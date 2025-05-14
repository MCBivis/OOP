package dslchecker.repos;

import dslchecker.config.CourseConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RepoLoader {
    public static void loadRepos(CourseConfig config) {
        config.getGroups().parallelStream().forEach(group -> {
            System.out.println("Группа: " + group.getName());

            group.getStudents().parallelStream().forEach(student -> {
                System.out.println("  " + student.getFullName() + " (" + student.getGithub() + ")");

                String repoPath = "repos/" + student.getGithub();

                File repoDir = new File(repoPath);
                if (repoDir.exists() && repoDir.isDirectory()) {
                    System.out.println("  Репозиторий " + student.getRepo() + " существует, выполняю git pull.");
                    try {
                        String cmd = "git -C " + repoPath + " pull";
                        executeCommand(cmd);
                    } catch (IOException | InterruptedException e) {
                        System.err.println("Ошибка при обновлении репозитория для студента " + student.getGithub());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("  Репозиторий " + student.getRepo() + " не найден, выполняю git clone.");
                    String cmd = "git clone " + student.getRepo() + " " + repoPath;
                    try {
                        executeCommand(cmd);
                    } catch (IOException | InterruptedException e) {
                        System.err.println("Ошибка при загрузке репозитория для студента " + student.getGithub());
                        e.printStackTrace();
                    }
                }
            });
        });

        System.out.println("Все репозитории загружены.");
    }

    private static void executeCommand(String cmd) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(cmd);
        if (!p.waitFor(5, TimeUnit.MINUTES)) {
            System.out.println("Команда не завершилась вовремя: " + cmd);
            p.destroy();
        }
    }
}


