package dslchecker.repos;

import dslchecker.config.CourseConfig;
import dslchecker.model.*;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class CompilationChecker {
    private final CourseConfig config;
    private final String repoRoot, buildRoot;

    public CompilationChecker(CourseConfig config, String repoRoot, String buildRoot) {
        this.config = config;
        this.repoRoot = repoRoot;
        this.buildRoot = buildRoot;
    }

    public List<String> checkAll() {
        List<String> successfulTaskIds = new ArrayList<>();

        for (Assignment assignment : config.getAssignments()) {
            String taskId = assignment.getTaskId();
            String github = assignment.getGithub();

            System.out.println("\nПроверка: " + assignment.getGithub());
            File sourceRoot = new File(repoRoot, github + "/" + taskId + "/src/main");
            File outputDir = new File(buildRoot, github + "/" + taskId);
            outputDir.mkdirs();

            System.out.println("Задача " + taskId + ": компиляция main...");
            boolean successMain = JavaCompilerUtil.compileSources(sourceRoot, outputDir, null);
            System.out.println("Результат: " + (successMain ? "Успешно" : "Неудача"));

            sourceRoot = new File(repoRoot, github + "/" + taskId + "/src/test");
            String classpath = "tools/junit-platform-console-standalone.jar"
                    + File.pathSeparator +
                    Path.of(buildRoot, github, taskId).toAbsolutePath();

            System.out.println("Задача " + taskId + ": компиляция test...");
            boolean successTest = JavaCompilerUtil.compileSources(sourceRoot, outputDir, classpath);
            System.out.println("Результат: " + (successTest ? "Успешно" : "Неудача"));
            if (successMain && successTest) {
                successfulTaskIds.add(github + "/" + taskId);
            }
        }
        return successfulTaskIds;
    }

}
