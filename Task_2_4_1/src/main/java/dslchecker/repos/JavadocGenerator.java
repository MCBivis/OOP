package dslchecker.repos;

import dslchecker.config.CourseConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JavadocGenerator {
    private final String repoRoot, buildRoot;
    private final List<String> successfulTasks;

    public JavadocGenerator(String repoRoot, String buildRoot, List<String> successfulTasks) {
        this.repoRoot = repoRoot;
        this.buildRoot = buildRoot;
        this.successfulTasks = successfulTasks;
    }

    public List<String> generateAll() {
        List<String> success = new ArrayList<>();

        for (String taskPath : successfulTasks) {
            System.out.println("\nГенерация Javadoc для " + taskPath);

            Path sourceDir = Path.of(repoRoot, taskPath, "src", "main");
            Path docOutputDir = Path.of(buildRoot, taskPath);

            try {
                boolean ok = generateJavadoc(sourceDir, docOutputDir);
                System.out.println("Javadoc " + (ok ? "успешно сгенерирован." : "ошибка генерации."));
                if (ok) {
                    success.add(taskPath);
                }
            } catch (IOException e) {
                System.err.println("Ошибка генерации Javadoc для " + taskPath + ": " + e.getMessage());
            }
        }

        return success;
    }

    private boolean generateJavadoc(Path repoPath, Path outputPath) throws IOException {
        Files.createDirectories(outputPath);

        List<String> javadocCmd = new ArrayList<>();
        javadocCmd.add("javadoc");
        javadocCmd.add("-d");
        javadocCmd.add(outputPath.toString());

        try (Stream<Path> pathStream = Files.walk(repoPath)) {
            pathStream.filter(p -> p.toString().endsWith(".java"))
                    .forEach(p -> javadocCmd.add(p.toString()));
        }

        ProcessBuilder pb = new ProcessBuilder(javadocCmd);
        pb.inheritIO();
        Process process = pb.start();

        try {
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
