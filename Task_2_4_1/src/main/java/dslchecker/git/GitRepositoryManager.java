package dslchecker.git;

import java.io.File;
import java.io.IOException;

public class GitRepositoryManager {

    public static boolean cloneOrUpdateRepo(String repoUrl, String studentName, File targetDir) {
        File studentRepoDir = new File(targetDir, studentName);

        ProcessBuilder processBuilder;
        if (studentRepoDir.exists()) {
            // Выполняем git pull
            processBuilder = new ProcessBuilder("git", "-C", studentRepoDir.getAbsolutePath(), "pull");
        } else {
            // Выполняем git clone
            processBuilder = new ProcessBuilder("git", "clone", repoUrl, studentRepoDir.getAbsolutePath());
        }

        processBuilder.inheritIO(); // Чтобы видеть вывод в консоль
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка при работе с git: " + e.getMessage());
            return false;
        }
    }
}
