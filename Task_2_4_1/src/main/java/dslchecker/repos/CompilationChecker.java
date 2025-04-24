package dslchecker.repos;

import dslchecker.config.CourseConfig;

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
        List<String> successfulTaskIds = Collections.synchronizedList(new ArrayList<>());

        config.getAssignments().parallelStream().forEach(assignment -> {
            String taskId = assignment.getTaskId();
            String github = assignment.getGithub();

            File sourceRoot = new File(repoRoot, github + "/" + taskId + "/src/main");
            File outputDir = new File(buildRoot, github + "/" + taskId);
            outputDir.mkdirs();

            System.out.println("Задача " + github + " " + taskId + ": компиляция main...");
            boolean successMain = JavaCompilerUtil.compileSources(sourceRoot, outputDir, null);

            sourceRoot = new File(repoRoot, github + "/" + taskId + "/src/test");
            String classpath = "tools/junit-platform-console-standalone.jar"
                    + File.pathSeparator +
                    Path.of(buildRoot, github, taskId).toAbsolutePath();

            System.out.println("Задача " + github + " " + taskId + ": компиляция test...");
            boolean successTest = JavaCompilerUtil.compileSources(sourceRoot, outputDir, classpath);
            if (successMain && successTest) {
                successfulTaskIds.add(github + "/" + taskId);
            }
        });

        return successfulTaskIds;
    }

}
