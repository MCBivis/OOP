package dslchecker.git;

import dslchecker.config.CourseConfig;
import dslchecker.model.Group;
import dslchecker.model.Student;

import java.io.File;
import java.io.IOException;

public class RepositoryLoader {

    public static void loadRepositories(CourseConfig config) {
        System.out.println("Начинаем загрузку репозиториев...");

        for (Group group : config.getGroups()) {
            for (Student student : group.getStudents()) {
                String repoUrl = student.getRepo();
                String github = student.getGithub();

                if (repoUrl == null || repoUrl.isBlank()) {
                    System.out.println("Пропуск: " + github + " — нет ссылки на репозиторий.");
                    continue;
                }

                // Клонировать в директорию repos/<github>
                File targetDir = new File("repos/" + github);
                if (targetDir.exists()) {
                    System.out.println("Пропуск клонирования — папка уже существует: " + targetDir.getPath());
                    continue;
                }

                try {
                    ProcessBuilder builder = new ProcessBuilder(
                            "git", "clone", repoUrl, targetDir.getAbsolutePath()
                    );
                    builder.inheritIO();
                    Process process = builder.start();
                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        System.out.println("Успешно клонирован репозиторий: " + github);
                    } else {
                        System.out.println("Ошибка при клонировании " + github);
                    }

                } catch (IOException | InterruptedException e) {
                    System.out.println("Ошибка при клонировании: " + github);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Загрузка завершена.");
    }
}
