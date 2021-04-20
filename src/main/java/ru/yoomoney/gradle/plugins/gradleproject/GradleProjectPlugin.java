package ru.yoomoney.gradle.plugins.gradleproject;

import com.gradle.publish.PublishPlugin;
import io.github.gradlenexus.publishplugin.NexusPublishPlugin;
import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.plugin.devel.plugins.JavaGradlePluginPlugin;
import org.gradle.util.VersionNumber;
import ru.yoomoney.gradle.plugins.backend.build.JavaPlugin;
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishPlugin;
import ru.yoomoney.gradle.plugins.library.dependencies.CheckDependenciesPlugin;
import ru.yoomoney.gradle.plugins.release.ReleasePlugin;

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
            JavaPlugin.class,
            DependencyManagementPlugin.class,
            CheckDependenciesPlugin.class,
            JavaArtifactPublishPlugin.class,
            ReleasePlugin.class,
            PublishPlugin.class,
            NexusPublishPlugin.class
    );

    @Override
    public void apply(Project project) {
        if (VersionNumber.parse(project.getGradle().getGradleVersion()).compareTo(VersionNumber.parse("6.4.1'")) < 0) {
            throw new IllegalStateException("Gradle >= 6.4.1 is required");
        }

        project.getPluginManager().apply(JavaGradlePluginPlugin.class);
        ExtensionConfigurator.configurePublish(project);
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
        ExtensionConfigurator.configure(project);
    }
}
