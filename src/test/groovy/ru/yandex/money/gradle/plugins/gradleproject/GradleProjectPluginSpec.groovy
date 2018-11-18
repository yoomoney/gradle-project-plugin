package ru.yandex.money.gradle.plugins.gradleproject
/**
 * @author Oleg Kandaurov
 * @since 18.11.2018
 */
class GradleProjectPluginSpec extends AbstractPluginSpec {

    def "should publish gradle plugin"() {
        when:
        def result = runTasksSuccessfully("clean", "build", "pTML")
        System.err.println(result.standardOutput)
        then:
        result.success

    }

}
