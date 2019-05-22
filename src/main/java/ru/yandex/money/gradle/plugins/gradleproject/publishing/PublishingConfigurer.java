package ru.yandex.money.gradle.plugins.gradleproject.publishing;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Конфигурирует публикацию артефактов
 *
 * @author Valerii Zhirnov (vazhirnov@yamoney.ru)
 * @since 22.04.2019
 */
@SuppressFBWarnings("HARD_CODE_PASSWORD")
public class PublishingConfigurer {

    private static final String ARTIFACT_GROUP = "ru.yandex.money.gradle.plugins";
    private final Logger logger = Logging.getLogger(PublishingConfigurer.class);

    /**
     * Иницализация
     */
    public void init(Project project) {
        project.getExtensions().getExtraProperties().set("pluginId", "");
        project.getExtensions().getByType(GradlePluginDevelopmentExtension.class).setAutomatedPublishing(false);
        project.afterEvaluate(target -> {
            configureJavadoc(target);
            configurePublishing(target);
            configureStoreVersion(target);
        });
    }

    private void configureJavadoc(Project project) {
        Javadoc javadoc = (Javadoc) project.getTasks().findByName("javadoc");
        javadoc.setClasspath(javadoc.getClasspath().plus(project.getConfigurations().getByName("optional")));
        javadoc.getOptions().setEncoding("UTF-8");
        javadoc.setFailOnError(false);

        project.getTasks().create("sourcesJar", Jar.class, sourcesJar -> {
            sourcesJar.from(
                    project.getConvention()
                            .getPlugin(JavaPluginConvention.class)
                            .getSourceSets()
                            .getByName("main")
                            .getAllJava()
            );
            sourcesJar.setClassifier("sources");
        });

        project.getTasks().create("javadocJar", Jar.class, javadocJar -> {
            javadocJar.dependsOn("javadoc");
            javadocJar.setClassifier("javadoc");
            javadocJar.from(javadoc.getDestinationDir());
        });
    }

    private void configurePublishing(Project project) {
        PublishingExtension publishingExtension = project.getExtensions().getByType(PublishingExtension.class);
        publishingExtension.publications(publicationContainer -> {
            MavenPublication mavenJava = publicationContainer.maybeCreate("mavenJava", MavenPublication.class);
            mavenJava.setGroupId(ARTIFACT_GROUP);
            mavenJava.setArtifactId((String) project.property("pluginId"));
            mavenJava.from(project.getComponents().getByName("java"));
            mavenJava.artifact(project.getTasks().getByName("sourcesJar"));
            mavenJava.artifact(project.getTasks().getByName("javadocJar"));
        });
        publishingExtension.repositories(artifactRepositories -> {
            artifactRepositories.maven(repository -> {
                if (project.getVersion().toString().endsWith("-SNAPSHOT")) {
                    repository.setUrl(URI.create("https://nexus.yamoney.ru/content/repositories/snapshots/"));
                } else {
                    repository.setUrl(URI.create("https://nexus.yamoney.ru/content/repositories/releases/"));
                }
                repository.credentials(passwordCredentials -> {
                    passwordCredentials.setUsername(System.getenv("NEXUS_USER"));
                    passwordCredentials.setPassword(System.getenv("NEXUS_PASSWORD"));
                });
            });
        });
        project.getTasks().withType(PublishToMavenRepository.class).forEach(task -> task.dependsOn("jar", "test"));
    }

    private void configureStoreVersion(Project project) {
        storeVersion(project);
        project.getTasks().getByName("publish").finalizedBy("storeVersion");
    }

    private void storeVersion(Project project) {
        Task storeVersion = project.getTasks().create("storeVersion");
        storeVersion.setDescription("Generates file, which contains information about build version");
        storeVersion.doLast(task -> {
            String version = String.format("%s:%s:%s",
                    ARTIFACT_GROUP, project.property("pluginId"), project.getVersion());
            storeVersionToFile(project.getBuildDir().getAbsolutePath(), "version.txt", version);
        });
    }

    private void storeVersionToFile(String versionDir, String filename, String content) {
        try {
            Path versionFile = Paths.get(versionDir, filename);
            ResourceGroovyMethods.write(versionFile.toFile(), content);
            logger.lifecycle("File with version generated successfully into " + versionFile);
        } catch (Exception e) {
            logger.lifecycle("Error occurred during storing version", e);
        }
    }

}
