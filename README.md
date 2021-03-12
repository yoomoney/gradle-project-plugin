# gradle-project-plugin

Плагин создан для упрощения сборки существующих gradle плагинов и разработки новых.
Данный плагин выступает в качестве агрегатора функционала - в нём применяются настройки и другие плагины, 
необходимые для работы и сборки gradle-плагинов.

Данный плагин является базовым, и для собственной сборки использует скрипт (configurator.gradle).

## Подключение
Для подключения добавьте в build.gradle:
```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'ru.yoomoney.gradle.plugins.gradle-project-plugin:7.+'
    }
}
apply plugin: 'ru.yoomoney.gradle.plugins.gradle-project-plugin'
```

Также нужно добавить описание вашего плагина:
```groovy
artifactId = 'hello-world-plugin' //обязательное свойство. применяется для публикации и для создания ссылок на проект в github
gradlePlugin {
    plugins {
        helloWorldPlugin {
            id = "ru.yoomoney.hello-world-plugin"    //идентификатор, с помощью которого плагин можно подключать к проекту
            implementationClass = "ru.yoomoney.gradle.plugins.helloworld.HelloWorldPlugin"
        }
    }
}
```

## Список подключаемых и конфигурируемых плагинов
*  [ru.yoomoney.gradle.plugins:check-dependencies-plugin](https://github.com/yoomoney-gradle-plugins/check-dependencies-plugin);  
*  [ru.yoomoney.gradle.plugins:java-artifact-publish-plugin](https://github.com/yoomoney-gradle-plugins/java-artifact-publish-plugin);  
*  [ru.yoomoney.gradle.plugins:artifact-release-plugin](https://github.com/yoomoney-gradle-plugins/artifact-release-plugin);  
*  [ru.yoomoney.gradle.plugins:java-plugin](https://github.com/yoomoney-gradle-plugins/java-plugin);  
*  [io.spring.dependency-management](https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/);
*  [com.gradle.publish:plugin-publish-plugin](https://docs.gradle.org/current/userguide/publishing_gradle_plugins.html).