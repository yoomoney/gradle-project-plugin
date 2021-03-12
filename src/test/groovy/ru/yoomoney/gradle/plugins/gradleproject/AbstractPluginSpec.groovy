package ru.yoomoney.gradle.plugins.gradleproject

import nebula.test.IntegrationSpec
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.URIish

import java.nio.file.Paths

/**
 * @author Oleg Kandaurov
 * @since 18.11.2018
 */
abstract class AbstractPluginSpec extends IntegrationSpec {

    protected Git git
    File gradleProperties
    File pluginClass
    File changelogFile

    def setup() {
        gradleProperties = Paths.get(projectDir.absolutePath, 'gradle.properties').toFile()
        gradleProperties.createNewFile()

        changelogFile = Paths.get(projectDir.absolutePath, 'CHANGELOG.md').toFile()
        changelogFile.createNewFile()

        Paths.get(projectDir.absolutePath, 'src/main/java/ru/yandex/money/gradle/plugins/helloworld').toFile().mkdirs()
        pluginClass = Paths.get(projectDir.absolutePath, 'src/main/java/ru/yandex/money/gradle/plugins/helloworld/HelloWorldPlugin.java').toFile()
        pluginClass.createNewFile()

        buildFile << """

apply plugin: "ru.yoomoney.gradle.plugins.gradle-project-plugin"
System.setProperty("ignoreDeprecations", "true")
artifactId = 'hello-world-plugin'
gradlePlugin {
    plugins {
        helloWorldPlugin {
            id = "\$artifactId"
            implementationClass = "ru.yandex.money.gradle.plugins.helloworld.HelloWorldPlugin"
        }
    }
}

javaModule {
    checkstyleEnabled = false
}

dependencies {
    implementation localGroovy()
}

"""
        def key = new File(getClass().getResource("test_gpg_key.txt").toURI()).text

        gradleProperties << "version=1.0.0-SNAPSHOT\n" +
                "signingPassword=123456\n" +
                "signingKey=$key"

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
        changelogFile << """
### NEXT_VERSION_TYPE=MAJOR
   ### NEXT_VERSION_DESCRIPTION_BEGIN
   * Init
   ### NEXT_VERSION_DESCRIPTION_END
"""
        git = Git.init().setDirectory(projectDir).setBare(false).call()
        git.add().addFilepattern('**/**').call()
        git.commit().setMessage('init').setAll(true).call()
        git.remoteSetUrl()
                .setRemoteUri(new URIish("file://${projectDir}/"))
                .setRemoteName("origin")
                .call()

    }

    def cleanup() {
        git.close()
    }
}
