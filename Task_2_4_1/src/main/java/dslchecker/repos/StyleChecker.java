package dslchecker.repos;

import dslchecker.config.CourseConfig;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StyleChecker {
    private final String repoRoot;
    private final List<String> successfulTasks;

    public StyleChecker(String repoRoot, List<String> successfulTasks) {
        this.repoRoot = repoRoot;
        this.successfulTasks = successfulTasks;
    }

    public List<String> checkAll() {
        List<String> success = new ArrayList<>();

        for (String taskPath : successfulTasks) {
            System.out.println("\nПроверка стиля: " + taskPath);
            Path sourceDir = Path.of(repoRoot, taskPath, "src", "main");

            try {
                boolean result = checkStyleWithGoogleFormat(sourceDir);
                System.out.println("Стиль: " + (result ? "соответствует" : "ошибка в файлах выше"));
                if (result) {
                    success.add(taskPath);
                }
            } catch (Exception e) {
                System.err.println("Ошибка проверки стиля для " + taskPath + ": " + e.getMessage());
            }
        }

        return success;
    }

    private boolean checkStyleWithGoogleFormat(Path sourceDir) throws IOException, InterruptedException {
        if (!Files.exists(sourceDir)) return false;

        List<Path> javaFiles;
        try (Stream<Path> stream = Files.walk(sourceDir)) {
            javaFiles = stream.filter(p -> p.toString().endsWith(".java")).collect(Collectors.toList());
        }

        if (javaFiles.isEmpty()) return false;

        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add("tools/google-java-format.jar");
        command.add("--dry-run");
        command.add("--set-exit-if-changed");
        for (Path javaFile : javaFiles) {
            command.add(javaFile.toString());
        }

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.inheritIO();
        Process process = pb.start();

        int exitCode = process.waitFor();
        return exitCode == 0;
    }

}
