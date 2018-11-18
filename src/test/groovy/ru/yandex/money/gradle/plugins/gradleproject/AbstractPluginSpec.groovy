package ru.yandex.money.gradle.plugins.gradleproject

import nebula.test.IntegrationSpec
import org.ajoberstar.grgit.Grgit
import org.apache.commons.io.FileUtils

import java.nio.file.Paths

/**
 * @author Oleg Kandaurov
 * @since 18.11.2018
 */
abstract class AbstractPluginSpec extends IntegrationSpec {

    protected Grgit grgit
    File gradleProperties
    File pluginClass

    def setup() {
        gradleProperties = Paths.get(projectDir.absolutePath, 'gradle.properties').toFile()
        gradleProperties.createNewFile()

        Paths.get(projectDir.absolutePath, 'src/main/java/ru/yandex/money/gradle/plugins/helloworld').toFile().mkdirs()
        pluginClass = Paths.get(projectDir.absolutePath, 'src/main/java/ru/yandex/money/gradle/plugins/helloworld/HelloWorldPlugin.java').toFile()
        pluginClass.createNewFile()

        buildFile << """
apply from: 'tmp/gradle-scripts/_root.gradle'

pluginId = 'yamoney-hello-world-plugin'
pluginImplementationClass = 'ru.yandex.money.gradle.plugins.helloworld.HelloWorldPlugin'

checkstyleEnabled = false

dependencies {
    compile localGroovy()
}
"""
        gradleProperties << """version=1.1.3"""

        pluginClass << """
package ru.yandex.money.gradle.plugins.helloworld;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class HelloWorldPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("hello world");
    }
}
"""

        grgit = Grgit.init(dir: projectDir)
        FileUtils.copyDirectory(Paths.get(System.getProperty("user.dir"), "gradle-scripts").toFile(),
                Paths.get(projectDir.absolutePath, "tmp", "gradle-scripts").toFile())



        grgit.add(patterns: ['**/**'])
        grgit.commit(message: 'init', all: true)

    }

    def cleanup() {
        grgit.close()
    }
}
