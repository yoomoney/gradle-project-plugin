package ru.yandex.money.gradle.plugins.gradleproject;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import ru.yandex.money.gradle.plugins.library.changelog.CheckChangelogPlugin;

import java.util.Arrays;
import java.util.Collection;

/**
 * Входная точка gradle-project-plugin'а, подключает все необходимые плагины-зависимости.
 *
 * @author Oleg Kandaurov
 * @since 14.11.2018
 */
public class GradleProjectPlugin implements Plugin<Project> {
    /**
     * Для подключения новой функциональности, достаточно добавить плагин в этот список.
     * Все остальные настройки должны делаться в самом добавляемом плагине.
     */
    private static final Collection<Class<?>> PLUGINS_TO_APPLY = Arrays.asList(
            CheckChangelogPlugin.class
    );

    @Override
    public void apply(Project project) {
        PLUGINS_TO_APPLY.forEach(pluginClass -> project.getPluginManager().apply(pluginClass));
    }
}
