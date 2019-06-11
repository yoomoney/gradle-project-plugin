package ru.yandex.money.gradle.plugins.gradleproject;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.plugin.devel.plugins.JavaGradlePluginPlugin;
import org.gradle.util.VersionNumber;
import ru.yandex.money.gradle.plugin.architecturetest.ArchitectureTestPlugin;
import ru.yandex.money.gradle.plugins.backend.build.JavaModulePlugin;
import ru.yandex.money.gradle.plugins.gradleproject.publishing.PublishingConfigurer;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.GitExpiredBranchPlugin;
import ru.yandex.money.gradle.plugins.release.ReleasePlugin;

import java.util.Arrays;
import java.util.Collection;

/**
 * Входная точка gradle-project-plugin'а, подключает все необходимые плагины-зависимости.
 *
 * @author Oleg Kandaurov
 * @since 14.11.2018
 */
public class GradleProjectPlugin implements Plugin<Project> {

    private static final Collection<Class<?>> PLUGINS_TO_APPLY = Arrays.asList(
            JavaModulePlugin.class,
            MavenPublishPlugin.class,
            ReleasePlugin.class,
            GitExpiredBranchPlugin.class,
            JavaGradlePluginPlugin.class,
            ArchitectureTestPlugin.class
    );

    @Override
    public void apply(Project project) {
        if (VersionNumber.parse(project.getGradle().getGradleVersion()).compareTo(VersionNumber.parse("4.10.2'")) < 0) {
            throw new IllegalStateException("Gradle >= 4.10.2 is required");
        }
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
        configureRepos(project);
        ExtensionConfigurator.configure(project);
        new PublishingConfigurer().init(project);
        project.getTasks().create("checkComponentSnapshotDependencies").dependsOn("checkSnapshotsDependencies");
    }

    private void configureRepos(Project project) {
        project.getRepositories().maven(repo -> repo.setUrl("https://nexus.yamoney.ru/repository/gradle-plugins/"));
    }


}
