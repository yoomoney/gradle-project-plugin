package ru.yoomoney.gradle.plugins.gradleproject;

import org.gradle.api.Project;
import ru.yoomoney.gradle.plugins.library.dependencies.CheckDependenciesPluginExtension;
import ru.yoomoney.gradle.plugins.library.dependencies.checkversion.MajorVersionCheckerExtension;

import java.util.HashSet;

import static java.util.Collections.singletonList;

/**
 * Конфигурация check-dependencies-plugin
 *
 * @author horyukova
 * @since 12.03.2021
 */
public final class CheckDependenciesConfigurer {
    private CheckDependenciesConfigurer(){}

    public static void configureCheckDependencies(Project project) {
        configureMajorVersionCheckerExtension(project);
    }

    private static void configureMajorVersionCheckerExtension(Project project) {
        HashSet<String> includeGroupId = new HashSet<>();
        includeGroupId.add("ru.yoomoney");

        MajorVersionCheckerExtension majorVersionCheckerExtension =
                project.getExtensions().findByType(MajorVersionCheckerExtension.class);
        majorVersionCheckerExtension.includeGroupIdPrefixes = includeGroupId;
    }
}
