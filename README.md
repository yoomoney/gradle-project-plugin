# gradle-project-plugin

Плагин создан для упрощения сборки существующих gradle плагинов и разработки новых.
Данный плагин выступает в качестве агрегатора функционала - в нём применяются настройки и другие плагины, 
необходимые для работы и сборки gradle-плагинов.

## Подключение
Для подключения в проект этого плагина, нужно добавить файл ```project.gradle```:
```groovy
System.setProperty("platformGradleProjectVersion", "3.+")
repositories {
    maven { url 'https://nexus.yamoney.ru/content/repositories/thirdparty/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/central/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/releases/' }
    maven { url 'https://nexus.yamoney.ru/content/repositories/jcenter.bintray.com/' }
    maven { url 'https://nexus.yamoney.ru/repository/gradle-plugins/' }

    dependencies {
        classpath 'ru.yandex.money.gradle.plugins:yamoney-gradle-project-plugin:' + 
                System.getProperty("platformGradleProjectVersion")
    }
}
```
А в `build.gradle` добавить соответствующую секцию, чтобы конфигурационный файл выглядел подобным образом:
```groovy
buildscript {
    apply from: 'project.gradle', to: buildscript
}
apply plugin: 'yamoney-gradle-project-plugin'

pluginId = 'yamoney-hello-world-plugin'
gradlePlugin {
    plugins {
        helloWorldPlugin {
            id = "$pluginId"
            implementationClass = "ru.yandex.money.gradle.plugins.helloworld.HelloWorldPlugin"
        }
    }
}

dependencies {
    compile 'com.fasterxml.jackson.core:jackson-annotations:2.9.0'
    testCompile gradleTestKit()
}
```