package dslchecker.compile;

import dslchecker.config.CourseConfig;
import dslchecker.model.*;

import java.io.File;

public class CompilationChecker {

    public static void checkAll(CourseConfig config, String repoRoot, String buildRoot) {
        for (Assignment assignment : config.getAssignments()) {
            String taskId = assignment.getTaskId();
            String github = assignment.getGithub();

            System.out.println("\nПроверка: " + assignment.getGithub());
            File sourceRoot = new File(repoRoot, github + "/" + taskId + "/src/main");
            File outputDir = new File(buildRoot, github + "/" + taskId);
            outputDir.mkdirs();

            System.out.println("Задача " + taskId + ": компиляция...");
            boolean success = JavaCompilerUtil.compileSources(sourceRoot, outputDir);
            System.out.println("Результат: " + (success ? "Успешно" : "Неудача"));
        }
    }
}
