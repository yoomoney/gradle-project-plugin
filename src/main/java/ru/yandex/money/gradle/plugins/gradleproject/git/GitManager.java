package ru.yandex.money.gradle.plugins.gradleproject.git;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

/**
 * Работа с git
 */
public class GitManager implements Closeable {

    private final Logger log = Logging.getLogger(GitManager.class);
    private final Git git;

    public GitManager(Project project) {
        try {
            this.git = new Git(new FileRepositoryBuilder()
                    .readEnvironment()
                    .findGitDir(project.getProjectDir())
                    .build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает текущую ветку
     *
     * @return текущую ветку
     */
    public Optional<String> getCurrentBranch() {
        try {
            return Optional.ofNullable(git.getRepository().getBranch());
        } catch (IOException e) {
            log.error("Can't get current branch ", e);
            return Optional.empty();
        }
    }

    /**
     * Проверяем, является ли текущая ветка подходящей для релиза
     *
     * @return true если из текущей ветки может быть срезан релиз
     */
    public boolean isCurrentBranchForRelease() {
        return getCurrentBranch()
                .filter(branchName -> "master".equals(branchName) ||
                        branchName.startsWith("release/")).isPresent();
    }

    @Override
    public void close() {
        git.close();
    }
}
