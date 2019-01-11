# gradle-project-plugin

## NEXT_VERSION

## [3.0.0]() (11-01-2018)

Добалвен `IdeaExtPlugin` устанавливающий галочку `Delegate IDE build/run actions to gradle` в 
`Build, Execution, Deployment -> Build Tools -> Gradle -> Runner`

Это нужно для корректного запуска тестов написанных на kotlin использующих GradleRunner из idea

`breaking changes` в `project.gradle` надо добавить `maven { url 'https://nexus.yamoney.ru/repository/gradle-plugins/' }` 

## [2.0.12]() (13-12-2018)

Добавила `CheckDependenciesPlugin`

## [2.0.11]() (06-12-2018)

Починила скрипт build-check-version.gradle

## [2.0.10]() (05-12-2018)

Добавил `GitExpiredBranchPlugin`

## [2.0.9]() (23-11-2018)

Добавил kotlin-stdlib-jdk8 в зависимости

## [2.0.8]() (17-11-2018)

* Поддержка плагинов на kotlin
* Статический анализ кода на kotlin
* Переход на gradle 4.10.2
* Стандартизирована публикация
* Убрал подключение ReadmePlugin

## [1.0.2]() (14-11-2018)

Убрал лишние плагины

## [1.0.1]() (14-11-2018)

Сборка при помощи gradle-project-plugin

## [1.0.0]() (14-11-2018)

Начальная версия