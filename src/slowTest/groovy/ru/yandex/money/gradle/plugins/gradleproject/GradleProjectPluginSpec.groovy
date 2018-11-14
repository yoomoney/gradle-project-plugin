package ru.yandex.money.gradle.plugins.gradleproject

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.testfixtures.ProjectBuilder
import ru.yandex.money.gradle.plugins.library.changelog.CheckChangelogPlugin
import ru.yandex.money.gradle.plugins.library.readme.PublishReadmeTask

/**
 *
 * @author Kirill Bulatov (mail4score@gmail.com)
 * @since 22.12.2016
 */
class GradleProjectPluginSpec extends AbstractPluginSpec {

    def "проверяем, что таски плагина определены и запускаются"() {
        def expectedTasks = [PublishReadmeTask.TASK_NAME, CheckChangelogPlugin.CHECK_CHANGELOG_TASK_NAME]

        when:
        def result = runTasksSuccessfully("tasks")

        then:
        expectedTasks.forEach({
            assert result.standardOutput.contains(it)
        })
    }

    def 'если имя текущей ветки равно master, тогда SNAPSHOT-репозитории НЕ должны быть включены в проект'() {
        given:
        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'если имя текущей ветки равно dev, тогда SNAPSHOT-репозитории НЕ должны быть включены в проект'() {
        given:
        checkoutNewBranch('dev')
        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'если имя текущей ветки начинается с release/, тогда SNAPSHOT-репозитории НЕ должны быть включены в проект'() {
        given:
        checkoutNewBranch('release/foo-1.1.1')
        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'если имя текущей ветки имеет формат tag/N.N.N, тогда SNAPSHOT-репозитории НЕ должны быть включены в проект'() {
        given:
        checkoutNewBranch('tags/1.1.1')
        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'если имя текущей ветки имеет формат tag/foo-bar-N.N.N, тогда SNAPSHOT-репозитории НЕ должны быть включены в проект'() {
        given:
        checkoutNewBranch('tags/calypso-latin-1.1.1')
        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'если в текущей ветке для последнего коммита определен релизный tag, тогда SNAPSHOT-репозитории НЕ должны быть включены в проект'() {
        given:
        createTag('1.1.1')
        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'если HEAD-указатель ссылается на релизную ветку, тогда SNAPSHOT-репозитории НЕ должны быть включены в проект'() {
        given:
        checkoutNewBranch('release/some')
        checkoutNewBranch('other')
        setRefToBranch('release/some')

        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).every({ !it.contains("snapshot") })
    }

    def 'если текущая ветка является рабочей, тогда SNAPSHOT-репозитории должны быть включены в проект'() {
        given:
        checkoutNewBranch('feature/SOME-0000')
        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).any({ it.contains("snapshot") })
    }

    def 'если HEAD-указатель ссылается на рабочую ветку, тогда SNAPSHOT-репозитории должны быть включены в проект'() {
        given:
        checkoutNewBranch('feature/SOME-0000')
        checkoutNewBranch('other')
        setRefToBranch('feature/SOME-0000')

        def project = createProject()

        when:
        project.apply plugin: 'yamoney-gradle-project-plugin'

        then:
        getMavenRepositoryUrls(project).any({ it.contains("snapshot") })
    }

    def checkoutNewBranch(String branchName) {
        grgit.checkout(branch: branchName, createBranch: true)
    }

    def setRefToBranch(String branchName) {
        ru.yandex.money.gradle.plugins.gradleproject.utils.Shell.execute(projectDir, "git symbolic-ref HEAD refs/heads/${branchName}".split(" "))
    }

    def createTag(String tagName) {
        grgit.tag.add(name: tagName)
    }

    def createProject() {
        def project = ProjectBuilder.builder().withProjectDir(projectDir).build()

        project.buildscript {
            repositories {
                maven { url 'http://nexus.yamoney.ru/content/repositories/releases/' }
                maven { url 'http://nexus.yamoney.ru/content/repositories/jcenter.bintray.com/' }
                maven { url 'http://nexus.yamoney.ru/content/repositories/central/' }
            }
            dependencies {
                classpath 'org.ajoberstar:gradle-git:1.5.0'
                classpath 'ru.yandex.money.common:yamoney-doc-publishing:1.0.1'
            }
        }

        project.apply plugin: 'java'

        project
    }

    def getMavenRepositoryUrls(Project project) {
        project.repositories.findAll { it instanceof MavenArtifactRepository}
                            .collect { ((MavenArtifactRepository)it).url.toString() }
    }
}
