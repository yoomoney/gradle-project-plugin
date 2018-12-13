package ru.yandex.money.gradle.plugins.gradleproject

import ru.yandex.money.gradle.plugins.library.dependencies.CheckDependenciesPlugin

/**
 * @author Oleg Kandaurov
 * @since 18.11.2018
 */
class GradleProjectPluginSpec extends AbstractPluginSpec {

    def "should publish gradle plugin"() {
        when:
        def result = runTasksSuccessfully("clean", "build", "slowTest", "pTML")
        then:
        result.success

    }

    def "should contains tasks"() {
        def expectedTasks = [CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME]

        when:
        def result = runTasksSuccessfully("tasks")

        then:
        expectedTasks.forEach({
            assert result.standardOutput.contains(it)
        })
    }

}
