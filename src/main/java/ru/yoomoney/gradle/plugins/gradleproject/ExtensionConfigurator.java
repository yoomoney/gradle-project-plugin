package ru.yoomoney.gradle.plugins.gradleproject;

import com.gradle.publish.MavenCoordinates;
import com.gradle.publish.PluginBundleExtension;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.wrapper.Wrapper;
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension;
import ru.yoomoney.gradle.plugins.backend.build.JavaExtension;
import ru.yoomoney.gradle.plugins.backend.build.git.GitManager;
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishExtension;
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishPlugin;
import ru.yoomoney.gradle.plugins.javapublishing.PublicationAdditionalInfo;
import ru.yoomoney.gradle.plugins.javapublishing.StagingPublicationSettings;
import ru.yoomoney.gradle.plugins.release.ReleaseExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Конфигуратор настроек плагинов.
 *
 * @author Dmitry Komarov
 * @since 05.12.2018
 */
public class ExtensionConfigurator {
    private static final String GIT_EMAIL = "SvcReleaserBackend@yoomoney.ru";
    private static final String GIT_USER = "yoomoney-robot";
    private static final String PLUGIN_GROUP = "ru.yoomoney.gradle.plugins";

    /**
     * Url, по которому будет скачиваться gradle в wrapper таске.
     */
    private static final String GRADLE_DISTRIBUTION_URL = "https://services.gradle.org/distributions/gradle-6.4.1-all.zip";

    /**
     * Конфигурирует плагины.
     *
     * @param project целевой проект
     */
    static void configure(Project project) {
        configureReleasePlugin(project);
        configureWrapper(project);
        configureJavaPlugin(project);
        configurePublishPlugin(project);
        CheckDependenciesConfigurer.configureCheckDependencies(project);
    }

    private static void configurePublishPlugin(Project project) {
        project.afterEvaluate(p -> {
            PluginBundleExtension bundleExtension = project.getExtensions().getByType(PluginBundleExtension.class);
            String artifactId = getArtifactId(project);

            MavenCoordinates mavenCoordinates = new MavenCoordinates();
            mavenCoordinates.setGroupId(PLUGIN_GROUP);

            bundleExtension.setWebsite(format("https://github.com/yoomoney/%s", artifactId));
            bundleExtension.setVcsUrl(format("https://github.com/yoomoney/%s.git", artifactId));
            bundleExtension.setTags(Arrays.asList("plugin", "gradle", "yoomoney"));
            bundleExtension.setDescription(getDescription(artifactId));
            bundleExtension.setMavenCoordinates(mavenCoordinates);
        });
    }

    private static void configureWrapper(Project project) {
        project.getTasks().maybeCreate("wrapper", Wrapper.class)
                .setDistributionUrl(GRADLE_DISTRIBUTION_URL);
    }

    /**
     * Сконфигурировать публикацию
     */
    static void configurePublish(Project project) {
        //Создаем extension сами, для того, чтобы выставить очередность afterEvaluate
        project.getExtensions().create(JavaArtifactPublishPlugin.extensionName,
                JavaArtifactPublishExtension.class);
        project.getExtensions().getExtraProperties().set("artifactId", "");
        project.getExtensions().getByType(GradlePluginDevelopmentExtension.class).setAutomatedPublishing(false);
        JavaArtifactPublishExtension publishExtension = project.getExtensions().getByType(JavaArtifactPublishExtension.class);

        project.afterEvaluate(p -> {
            publishExtension.setNexusUser(System.getenv("NEXUS_USER"));
            publishExtension.setNexusPassword(System.getenv("NEXUS_PASSWORD"));
            publishExtension.setSigning(true);

            String artifactId = getArtifactId(project);

            StagingPublicationSettings stagingPublicationSettings = new StagingPublicationSettings();
            stagingPublicationSettings.setEnabled(true);
            stagingPublicationSettings.setNexusUrl("https://oss.sonatype.org/service/local/");
            publishExtension.setStaging(stagingPublicationSettings);

            PublicationAdditionalInfo publicationAdditionalInfo = new PublicationAdditionalInfo();
            publicationAdditionalInfo.setAddInfo(true);
            publicationAdditionalInfo.setDescription(getDescription(artifactId));
            publicationAdditionalInfo.setOrganizationUrl("https://github.com/yoomoney");

            PublicationAdditionalInfo.License license = new PublicationAdditionalInfo.License();
            license.setName("MIT License");
            license.setUrl("http://www.opensource.org/licenses/mit-license.php");
            publicationAdditionalInfo.setLicense(license);

            PublicationAdditionalInfo.Developer developer = new PublicationAdditionalInfo.Developer();
            developer.setName("Oleg Kandaurov");
            developer.setEmail("kandaurov@yoomoney.ru");
            developer.setOrganizationUrl("https://yoomoney.ru");
            developer.setOrganization("YooMoney");
            ArrayList<PublicationAdditionalInfo.Developer> developers = new ArrayList<>();
            developers.add(developer);

            publicationAdditionalInfo.setDevelopers(developers);
            publishExtension.setPublicationAdditionalInfo(publicationAdditionalInfo);

            publishExtension.setGroupId(PLUGIN_GROUP);
            publishExtension.setArtifactId(artifactId);

            publishExtension.setSnapshotRepository("https://oss.sonatype.org/content/repositories/snapshots/");
        });
    }

    private static void configureReleasePlugin(Project project) {
        ReleaseExtension releaseExtension = project.getExtensions().getByType(ReleaseExtension.class);
        releaseExtension.getReleaseTasks().clear();

        //задачи, которые будут запускаться при релизе.
        //publish - опубликавать артефакт
        //publishPlugins - опубликовать плагин в Gradle Plugin Portal
        //closeAndReleaseMavenStagingRepository - закрыть staging репозиторий и выпустить артефакт в релизный репозиторий (MavenCentral)
        releaseExtension.getReleaseTasks().addAll(Arrays.asList("build", "publish", "closeAndReleaseMavenStagingRepository",
                "publishPlugins"));
        releaseExtension.setPathToGitPrivateSshKey(System.getenv("GIT_PRIVATE_SSH_KEY_PATH"));
        releaseExtension.setPassphraseToGitPrivateSshKey(System.getenv("GIT_KEY_PASSPHRASE"));

        releaseExtension.setGitUsername(GIT_USER);
        releaseExtension.setGitEmail(GIT_EMAIL);

        releaseExtension.setChangelogRequired(true);
        releaseExtension.setAddPullRequestLinkToChangelog(true);

        releaseExtension.setPullRequestInfoProvider("GitHub");
        releaseExtension.setGithubAccessToken(System.getenv("GITHUB_TOKEN"));

        try (GitManager git = new GitManager(project)) {
            if (git.isDevelopmentBranch()) {
                project.getTasks().getByName("build")
                        .dependsOn(project.getTasks().getByName("checkChangelog"));
            }
        }
    }

    private static void configureJavaPlugin(Project project) {
        List<String> repositories = Arrays.asList(project.getRepositories().mavenCentral().getUrl().toString(),
                project.getRepositories().jcenter().getUrl().toString(),
                "https://plugins.gradle.org/m2/");

        List<String> snapshotsRepositories = Arrays.asList(
                project.getRepositories().mavenLocal().getUrl().toString(),
                "https://oss.sonatype.org/content/repositories/snapshots/");

        JavaExtension javaExtension = project.getExtensions().getByType(JavaExtension.class);
        javaExtension.setRepositories(repositories);
        javaExtension.setSnapshotsRepositories(snapshotsRepositories);
    }

    private static String getArtifactId(Project project) {
        String value = (String) project.getExtensions().getExtraProperties().get("artifactId");
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("property artifactId is empty");
        }
        return value;
    }

    private static String getDescription(String artifactId) {
        return format("Gradle plugin by YooMoney. See README: " +
                "https://github.com/yoomoney/%s", artifactId);
    }
}
