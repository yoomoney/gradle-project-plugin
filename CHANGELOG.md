### NEXT_VERSION_TYPE=MAJOR|MINOR|PATCH
### NEXT_VERSION_DESCRIPTION_BEGIN
### NEXT_VERSION_DESCRIPTION_END
## [8.2.0](https://github.com/yoomoney/gradle-project-plugin/pull/19) (06-04-2022)

* Обновлена версия `java-artifact-publish-plugin` 3.2.0 -> 4.0.0
* Обновлена версия `artifact-release-plugin` 3.11.1 -> 6.0.0
* Обновлена версия `java-plugin` 6.1.0 -> 7.0.0
* Обновлена версия `check-dependencies-plugin` 7.1.2 -> 9.0.0

## [8.1.0](https://github.com/yoomoney/gradle-project-plugin/pull/18) (05-04-2022)

* Обновлена версия java-plugin 6.0.1 -> 6.1.0

## [8.0.1](https://github.com/yoomoney/gradle-project-plugin/pull/17) (05-04-2022)

* Обновлена версия java-plugin 6.0.0 -> 6.0.1

## [8.0.0](https://github.com/yoomoney/gradle-project-plugin/pull/16) (04-04-2022)

* Поднята версия gradle: 6.4.1 -> 7.4.1.
* **breaking changes** Для сборки плагина необходимо, чтобы проект поддерживал сборку с 7 версией gradle.

## [7.7.0](https://github.com/yoomoney/gradle-project-plugin/pull/15) (20-10-2021)

added a mavenCoordinates block to the pluginBundle

## [7.6.0](https://github.com/yoomoney/gradle-project-plugin/pull/14) (18-10-2021)

* Обновлена версия com.gradle.publish:plugin-publish-plugin: 0.13.0 -> 0.16.0

## [7.5.0](https://github.com/yoomoney/gradle-project-plugin/pull/12) (26-08-2021)

* Переезд организации yoomoney-gradle-plugins -> yoomoney

## [7.4.0](https://github.com/yoomoney/gradle-project-plugin/pull/11) (29-06-2021)

* Конфигурация публикации артефакта в staging репозиторий перенесена в `java-artifact-publish-plugin`;
* Обновлена версия `java-artifact-publish-plugin` 3.0.2 -> 3.2.0.

## [7.3.4](https://github.com/yoomoney/gradle-project-plugin/pull/8) (19-05-2021)

* Добавлена информация о сборке, покрытии, лицензии в README.md.

## [7.3.3](https://github.com/yoomoney/gradle-project-plugin/pull/7) (23-04-2021)

* Добавлена таска checkCoverage для поддержки общего скрипта сборки

## [7.3.2](https://github.com/yoomoney/gradle-project-plugin/pull/6) (22-04-2021)

* Выключение таски publishMainArtifactPublicationToMavenRepository, т.к. публикация осуществляется с помощью таски
gradle-nexus-staging-plugin.

## [7.3.1](https://github.com/yoomoney/gradle-project-plugin/pull/5) (20-04-2021)

* Исправлена конфигурация nexus-staging-plugin - теперь проставляется PackageGroup.

## [7.3.0](https://github.com/yoomoney/gradle-project-plugin/pull/4) (20-04-2021)

* Плагин по работе со staging репозиториями gradle-nexus-staging-plugin заменен на io.github.gradle-nexus:publish-plugin.
Старый плагин работал нестабильно из-за того, что не обрабатывал случаи с несколькими staging репозиториями. Новый плагин лишен
этого недостатка.

## [7.2.0](https://github.com/yoomoney/gradle-project-plugin/pull/3) (08-04-2021)

* Добавлена зависимости на таску checkChangelog для feature веток. Необходимо для проверки заполненности changelog при разработке.

## [7.1.0](https://github.com/yoomoney/gradle-project-plugin/pull/2) (30-03-2021)

* Обновлена версия artifact-release-plugin с 3.7.0 на 3.11.1

## [7.0.0](https://github.com/yoomoney/gradle-project-plugin/pull/1) (18-03-2021)

* Внесены изменения в связи с переходом на GitHub:
* Переименованы пакеты
* Сборка переведена на travis (ранее использовался jenkins)
* Изменен список подключаемых плагинов, теперь подключаются:
*  ru.yoomoney.gradle.plugins:check-dependencies-plugin;
*  io.spring.dependency-management;
*  ru.yoomoney.gradle.plugins:java-artifact-publish-plugin;
*  ru.yoomoney.gradle.plugins:artifact-release-plugin;
*  com.gradle.publish:plugin-publish-plugin;
*  ru.yoomoney.gradle.plugins:java-plugin.

## [6.10.0]() (05-03-2021)

* Переключение на плагин java-artifact-publish-plugin из github: ru.yandex.money.gradle.plugins -> ru.yoomoney.gradle.plugins

## [6.9.0]() (21-02-2021)

* Поднята версия artifact-release-plugin до 3.8.1

## [6.8.0]() (12-02-2021)

* Подключение java-module-plugin и artifact-dependencies-plugin заменено на подключение module-project-plugin

## [6.7.1]() (22-01-2021)

* Поднята версия artifact-release-plugin 3.5.1 -> 3.6.0

## [6.7.0]() (20-01-2021)

* Переключение на плагин artifact-release-plugin из github: ru.yandex.money.gradle.plugins -> ru.yoomoney.gradle.plugins

## [6.6.0]() (09-12-2020)

* Поднята версия yamoney-git-expired-branch-plugin: 5.1.1 -> 5.2.2

## [6.5.1]() (27-11-2020)

* Опущена версия kotlin 1.3.71 -> 1.3.50

## [6.5.0]() (23-11-2020)

* Обновлена версия yamoney-architecture-test-plugin: 2.2.1 -> 2.3.1

## [6.4.3]() (07-07-2020)

* Поднята версия kotlin 1.3.50 -> 1.3.71

## [6.4.2]() (03-07-2020)

* Опущена версия gradle: 6.5.1 -> 6.4.1.
* Поднята версия yamoney-git-expired-branch-plugin: 5.0.1 -> 5.1.1
* Поднята версия yamoney-java-artifact-publish-plugin: 2.0.1 -> 2.1.0
* Поднята версия yamoney-build-monitoring-plugin: 3.0.1 -> 3.2.0
* Поднята версия yamoney-architecture-test-plugin: 2.0.1 -> 2.2.1

## [6.4.1]() (02-07-2020)

* Поднята версия yamoney-java-module-plugin:2.0.1 -> 2.7.1

## [6.4.0]() (02-07-2020)

* Поднята версия gradle: 6.0.1 -> 6.5.1.

## [6.3.0]() (01-06-2020)

* Сборка плагина переведена с плагина gradle-project-plugin на файл configurator.gradle.

## [6.2.0]() (27-02-2020)

* Поднята версия yamoney-artifact-release-plugin: 3.0.1 -> 3.2.0.
Добавление ссылки на bitbucket pull request в changelog при релизе.

## [6.1.0]() (05-02-2020)

* Сборка на java 11

## [6.0.2]() (30-01-2020)

* Удаление репозитория snapshots.

## [6.0.1]() (30-01-2020)

* Убраны снапшотные версии плагинов.

## [6.0.0]() (29-01-2020)

* Поднята версия gradle: 4.10.2 -> 6.0.1.
**breaking changes** Для сборки плагина необходимо, чтобы проект поддерживал сборку с 6 версией gradle.

## [5.11.0]() (10-01-2020)

* Обновлён `yamoney-architecture-test-plugin`=`1.5.0`->`1.6.0`

## [5.10.0]() (29-11-2019)

* Обновлена версия `yamoney-git-expired-branch-plugin` 4.1.1 -> 4.2.1

## [5.9.0]() (22-11-2019)

* Обновлена версия `java-module-plugin` 1.16.0 -> 1.17.0

## [5.8.0]() (08-11-2019)

* Обновлена версия yamoney-java-module-plugin 1.15.2 -> 1.16.0

## [5.7.8]() (28-10-2019)

* Поднятие версии yamoney-java-module-plugin 1.15.1 -> 1.15.2

## [5.7.7]() (23-10-2019)

* Поднятие версии yamoney-java-module-plugin 1.15.0 -> 1.15.1

## [5.7.6]() (22-10-2019)

* Подключен yamoney-java-artifact-publish плагин

## [5.7.5]() (21-10-2019)

* Поднятие версии yamoney-git-expired-branch-plugin 4.0.3 -> 4.1.1

## [5.7.4]() (30-09-2019)

* Исправлено добавление исходников котлин в sources.jar

## [5.7.3]() (27-09-2019)

* Поднятие версии yamoney-git-expired-branch-plugin 4.0.2 -> 4.0.3

## [5.7.2]() (01-08-2019)

* Поднятие версии yamoney-git-expired-branch-plugin 4.0.1 -> 4.0.2

## [5.7.1]() (30-07-2019)

* Обновлён `yamoney-build-monitoring-plugin`=`2.0.0`->`2.0.1`,
изменены ключи отправляемых метрик

## [5.7.0]() (29-07-2019)

* Обновлён `yamoney-build-monitoring-plugin`=`1.3.0`->`2.0.0`,
с отправкой метрик в продакшен графану
* Обновлён `yamoney-architecture-test-plugin`=`1.4.0`->`1.5.0`,
с обновлением наборов тестов

## [5.6.6]() (12-07-2019)

* Повышена версия yamoney-java-module-plugin до 1.14.1

## [5.6.5]() (05-07-2019)

Обновлена версия yamoney-artifact-release-plugin=2.0.0 -> 2.1.0,
для исправления автора коммита при локальной сборке

## [5.6.4]() (04-07-2019)

* Повышена версия java-module-plugin `1.13.1`
* Новая версия исправляет баг в процедуре подсчета warnings (FileNotFoundException ../compile_error_out.txt)

## [5.6.3]() (03-07-2019)

* Повышена версия java-module-plugin `1.13.0`
* В новой версии исправлена проблема с отсутствием информации об ошибках компиляции в консоле

## [5.6.2]() (02-07-2019)

* Обновлены версии
* git-expired-branch-plugin до `4.0.0`
* artifact-release-plugin до `2.0.0`
Этим плагинам добавлено проставление настроек для git - user и email.

## [5.6.1]() (28-06-2019)

* Обновлён java-module-plugin до версии 1.12.0

## [5.6.0]() (26-06-2019)

* Поднята версия build-monitoring-plugin 1.2.0 -> 1.3.0

## [5.5.0]() (14-06-2019)

* Корректно подключены плагины yamoney-build-monitoring-plugin, yamoney-architecture-test-plugin

## [5.4.0]() (13-06-2019)

* Удалены подключения плагинов yamoney-architecture-test-plugin, yamoney-build-monitoring-plugin

## [5.3.0]() (13-06-2019)

* Включен yamoney-build-monitoring-plugin

## [5.2.0]() (11-06-2019)

* Обновлена версия yamoney-architecture-test-plugin
* По умолчанию включены для выполнения следующие тесты:
'check_unique_enums_codes'
'check_unique_enums_secondary_codes'

## [5.1.1]() (22-05-2019)

* Удалено подключение kotlin в пользу аналогичного функционала в yamoney-kotlin-module-plugin
* Удалена конфигурация Intellij IDEA в пользу аналогичного функционала в yamoney-java-module-plugin
* Удалено подключение idea-ext-plugin
* Обновлена версия yamoney-java-module-plugin 1.7.0 -> 1.9.0
* Обновлена версия yamoney-artifact-release-plugin 1.2.2 -> 1.4.2

## [5.1.0]() (14-05-2019)

* Обновлён `java-module-plugin`, новя версия включает в себя плагин для kotlin

## [5.0.2]() (14-05-2019)

* Добавлен репозиторий с Gradle плагинами

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