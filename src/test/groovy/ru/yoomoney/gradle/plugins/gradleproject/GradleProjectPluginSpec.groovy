package ru.yoomoney.gradle.plugins.gradleproject

import ru.yoomoney.gradle.plugins.library.dependencies.CheckDependenciesPlugin

/**
 * @author Oleg Kandaurov
 * @since 18.11.2018
 */
class GradleProjectPluginSpec extends AbstractPluginSpec {

    def "should publish gradle plugin"() {
        when:
        def result = runTasksSuccessfully("clean", "build", "componentTest", "pTML")
        then:
        result.success

    }

    def "should contains tasks"() {
        def expectedTasks = [CheckDependenciesPlugin.CHECK_DEPENDENCIES_TASK_NAME,
                             "preRelease", "release", "checkChangelog"]

        when:
        def result = runTasksSuccessfully("tasks")

        then:
        expectedTasks.forEach({
            assert result.standardOutput.contains(it)
        })
    }

    def "release task should depend on predefined release tasks"() {
        given:
        buildFile << """
            def printTaskDependencies(task, level) {
                task.taskDependencies.getDependencies(task).forEach {
                    println(":\${it.name}".padLeft(level * 4))
                    printTaskDependencies(it, level + 1)
                }
            }
            
            task printReleaseTaskDependencies {
                doLast { printTaskDependencies(project.tasks.getByName("release"), 0) }
            }
        """

        when:
        def result = runTasksSuccessfully("printReleaseTaskDependencies")

        then:
        assert result.standardOutput.contains("closeAndReleaseMavenStagingRepository")
        assert result.standardOutput.contains("publishPlugins")
        assert result.standardOutput.contains("publish")
        assert result.standardOutput.contains("build")
    }
}
