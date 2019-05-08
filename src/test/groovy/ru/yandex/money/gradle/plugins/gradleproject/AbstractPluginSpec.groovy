package ru.yandex.money.gradle.plugins.gradleproject

import nebula.test.IntegrationSpec
import org.ajoberstar.grgit.Grgit

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
buildscript {

repositories {
    maven { url 'https://nexus.yamoney.ru/content/repositories/thirdparty/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/central/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/releases/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/jcenter.bintray.com/' }
    maven { url 'https://nexus.yamoney.ru/repository/gradle-plugins/' }


}
}
apply plugin: "yamoney-gradle-project-plugin"
pluginId = 'yamoney-hello-world-plugin'
gradlePlugin {
    plugins {
        helloWorldPlugin {
            id = "\$pluginId"
            implementationClass = "ru.yandex.money.gradle.plugins.helloworld.HelloWorldPlugin"
        }
    }
}

javaModule {
    checkstyleEnabled = false
}

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
        grgit.add(patterns: ['**/**'])
        grgit.commit(message: 'init', all: true)

    }

    def cleanup() {
        grgit.close()
    }
}
