package ru.yandex.money.gradle.plugins.gradleproject;

import org.gradle.api.Project;
import ru.yandex.money.gradle.plugin.architecturetest.ArchitectureTestExtension;
import ru.yandex.money.gradle.plugins.gradleproject.git.GitManager;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.EmailConnectionExtension;
import ru.yandex.money.gradle.plugins.library.git.expired.branch.settings.GitConnectionExtension;
import ru.yandex.money.gradle.plugins.release.ReleaseExtension;

import java.util.Arrays;

/**
 * Конфигуратор настроек плагинов.
 *
 * @author Dmitry Komarov
 * @since 05.12.2018
 */
public class ExtensionConfigurator {

    /**
     * Конфигурирует плагины.
     *
     * @param project целевой проект
     */
    static void configure(Project project) {
        configureGitExpiredBranchesPlugin(project);
        configureReleasePlugin(project);
        configureArchitectureTestPlugin(project);
    }

    private static void configureReleasePlugin(Project project) {
        ReleaseExtension releaseExtension = project.getExtensions().getByType(ReleaseExtension.class);
        releaseExtension.getReleaseTasks().clear();
        releaseExtension.getReleaseTasks().addAll(Arrays.asList("build", "publish"));
        releaseExtension.setChangelogRequired(true);
        releaseExtension.setPathToGitPrivateSshKey(System.getenv("GIT_PRIVATE_SSH_KEY_PATH"));

        try (GitManager git = new GitManager(project)) {
            if (!git.isCurrentBranchForRelease()) {
                project.getTasks().getByName("build")
                        .dependsOn(project.getTasks().getByName("checkChangelog"));
            }
        }
    }

    private static void configureGitExpiredBranchesPlugin(Project project) {
        EmailConnectionExtension emailConnection = project.getExtensions().getByType(EmailConnectionExtension.class);
        emailConnection.emailHost = "mail.yamoney.ru";
        emailConnection.emailPort = 25;
        emailConnection.emailAuthUser = System.getenv("MAIL_USER");
        emailConnection.emailAuthPassword = System.getenv("MAIL_PASSWORD");


        GitConnectionExtension gitConnectionExtension =
                project.getExtensions().findByType(GitConnectionExtension.class);

        gitConnectionExtension.setPathToGitPrivateSshKey(System.getenv("GIT_PRIVATE_SSH_KEY_PATH"));
    }


    private static void configureArchitectureTestPlugin(Project project) {
        ArchitectureTestExtension architectureTestExtension = project.getExtensions().getByType(ArchitectureTestExtension.class);
        architectureTestExtension.getInclude().addAll(Arrays.asList(
                "check_unique_enums_codes",
                "check_unique_enums_secondary_codes"));
    }
}
