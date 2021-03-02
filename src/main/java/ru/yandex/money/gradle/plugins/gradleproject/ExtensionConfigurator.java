package ru.yandex.money.gradle.plugins.gradleproject;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.wrapper.Wrapper;
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension;
import ru.yandex.money.gradle.plugins.gradleproject.git.GitManager;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.EmailConnectionExtension;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.GitConnectionExtension;
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishExtension;
import ru.yoomoney.gradle.plugins.javapublishing.JavaArtifactPublishPlugin;
import ru.yoomoney.gradle.plugins.release.ReleaseExtension;

import java.util.Arrays;

/**
 * Конфигуратор настроек плагинов.
 *
 * @author Dmitry Komarov
 * @since 05.12.2018
 */
@SuppressFBWarnings("HARD_CODE_PASSWORD")
public class ExtensionConfigurator {
    private static final String GIT_EMAIL = "SvcReleaserBackend@yoomoney.ru";
    private static final String GIT_USER = "SvcReleaserBackend";
    /**
     * Url, по которому будет скачиваться gradle в wrapper таске.
     */
    private static final String GRADLE_DISTRIBUTION_URL = "https://nexus.yamoney.ru/content/repositories/" +
            "http-proxy-services.gradle.org/distributions/gradle-6.4.1-all.zip";

    /**
     * Конфигурирует плагины.
     *
     * @param project целевой проект
     */
    static void configure(Project project) {
        configureGitExpiredBranchesPlugin(project);
        configureReleasePlugin(project);
        configureWrapper(project);
    }

    private static void configureWrapper(Project project) {
        project.getTasks().maybeCreate("wrapper", Wrapper.class)
                .setDistributionUrl(GRADLE_DISTRIBUTION_URL);
    }

    private static String getStringExtProperty(Project project, String propertyName) {
        String value = (String)project.getExtensions().getExtraProperties().get(propertyName);
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("property " + propertyName + " is empty");
        }
        return value;
    }

    /**
     * Сконфигурировать публикацию
     */
    static void configurePublishPlugin(Project project) {
        //Создаем extension сами, для того, чтобы выставить очередность afterEvaluate
        project.getExtensions().create(JavaArtifactPublishPlugin.extensionName,
                JavaArtifactPublishExtension.class);
        project.getExtensions().getExtraProperties().set("pluginId", "");
        project.getExtensions().getByType(GradlePluginDevelopmentExtension.class).setAutomatedPublishing(false);
        JavaArtifactPublishExtension publishExtension = project.getExtensions().getByType(JavaArtifactPublishExtension.class);
        publishExtension.setNexusUser(System.getenv("NEXUS_USER"));
        publishExtension.setNexusPassword(System.getenv("NEXUS_PASSWORD"));
        project.afterEvaluate(p -> {
            publishExtension.setGroupId("ru.yandex.money.gradle.plugins");
            publishExtension.setArtifactId(getStringExtProperty(project, "pluginId"));

            publishExtension.setSnapshotRepository("https://nexus.yamoney.ru/repository/snapshots/");
            publishExtension.setReleaseRepository("https://nexus.yamoney.ru/repository/releases/");
        });
    }


    private static void configureReleasePlugin(Project project) {
        ReleaseExtension releaseExtension = project.getExtensions().getByType(ReleaseExtension.class);
        releaseExtension.getReleaseTasks().clear();
        releaseExtension.getReleaseTasks().addAll(Arrays.asList("build", "publish"));
        releaseExtension.setChangelogRequired(true);
        releaseExtension.setPathToGitPrivateSshKey(System.getenv("GIT_PRIVATE_SSH_KEY_PATH"));
        releaseExtension.setGitEmail(GIT_EMAIL);
        releaseExtension.setGitUsername(GIT_USER);

        releaseExtension.setAddPullRequestLinkToChangelog(true);
        releaseExtension.setBitbucketUser(System.getenv("BITBUCKET_USER"));
        releaseExtension.setBitbucketPassword(System.getenv("BITBUCKET_PASSWORD"));

        try (GitManager git = new GitManager(project)) {
            if (!git.isCurrentBranchForRelease()) {
                project.getTasks().getByName("build")
                        .dependsOn(project.getTasks().getByName("checkChangelog"));
            }
        }
    }

    private static void configureGitExpiredBranchesPlugin(Project project) {
        EmailConnectionExtension emailConnection = project.getExtensions().getByType(EmailConnectionExtension.class);
        emailConnection.emailHost = "mail.yoomoney.ru";
        emailConnection.emailPort = 25;
        emailConnection.emailAuthUser = System.getenv("MAIL_USER");
        emailConnection.emailAuthPassword = System.getenv("MAIL_PASSWORD");


        GitConnectionExtension gitConnectionExtension =
                project.getExtensions().findByType(GitConnectionExtension.class);

        gitConnectionExtension.setPathToGitPrivateSshKey(System.getenv("GIT_PRIVATE_SSH_KEY_PATH"));
        gitConnectionExtension.setEmail(GIT_EMAIL);
        gitConnectionExtension.setUsername(GIT_USER);
    }
}
