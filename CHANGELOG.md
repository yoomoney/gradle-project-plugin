### NEXT_VERSION_TYPE=PATCH
### NEXT_VERSION_DESCRIPTION_BEGIN
* Добавлен репозиторий с Gradle плагинами
### NEXT_VERSION_DESCRIPTION_END
## [5.0.1]() (08-05-2019)

* Сборка переведена на yamoney-gradle-project-plugin=5.0.0

## [5.0.0]() (08-05-2019)

* Все gradle скрипты заменены на плагины и удалены
* Изменён способ подключения плагина, подробности в README
## [4.3.0]() (26-04-2019)

* Подняла версию `yamoney-git-expired-branch-plugin` c '2.0.4' до `3.0.0`.

## [4.2.3]() (04-04-2019)

* Убрана галочка `Delegate IDE build/run actions to gradle` в
`Build, Execution, Deployment -> Build Tools -> Gradle -> Runner`
т.к. она устанавливается один раз в idea для всех проектов

## [4.2.2]() (20-03-2019)

* Исправлена авторизация в гите по ssh

## [4.2.1]() (20-03-2019)

* Исправлена авторизация в гите по ssh

## [4.2.0]() (07-03-2019)

* Подняла версию `yamoney-check-dependencies-plugin` до `4.4.2`

## [4.1.1]() (04-03-2019)

* Поменялся формат маркеров в changelog, сборка в фича ветках может упасть
поправьте %% на ### в changelog.md

## [4.1.0]() (26-02-2019)

* Переход на platformGradleProjectVersion 4 версии

## [4.0.1]() (18-02-2019)
Обновлен yamoney-artifact-release-plugin

## [4.0.0]() (18-02-2019)

Переход на yamoney-artifact-release-plugin
Поменялся релизный цикл, теперь вместо release, в мастере надо вызвать preRelease и release
Поменялся формат CHANGELOG.md подробности в https://bitbucket.yamoney.ru/projects/BACKEND-GRADLE-PLUGINS/repos/artifact-release-plugin/browse/README.md  
 
## [3.0.1]() (17-01-2019)

Изменена версия check-dependencies-plugin на 4.0.3

## [3.0.0]() (11-01-2019)

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