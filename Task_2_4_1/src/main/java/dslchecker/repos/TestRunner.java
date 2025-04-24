package dslchecker.repos;

import dslchecker.config.CourseConfig;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestRunner {
    private final CourseConfig config;
    private final String repoRoot;
    private final String buildRoot;
    private final List<String> successfulTasks;

    public TestRunner(CourseConfig config, String repoRoot, String buildRoot, List<String> successfulTasks) {
        this.config = config;
        this.repoRoot = repoRoot;
        this.buildRoot = buildRoot;
        this.successfulTasks = successfulTasks;
    }

    public Map<String, List<Integer>> runAllTests() {
        Map<String, List<Integer>> testResults = java.util.Collections.synchronizedMap(new LinkedHashMap<>());

        successfulTasks.parallelStream().forEach(taskPath -> {
            System.out.println("\nЗапуск тестов для: " + taskPath);

            Path classDir = Path.of(buildRoot, taskPath);
            Path testDir = Path.of(repoRoot, taskPath, "src", "test");

            try {
                List<Path> testFiles = getJavaFiles(testDir);
                if (testFiles.isEmpty()) {
                    System.out.println("Нет тестов");
                    testResults.put(taskPath, Arrays.asList(0, 0, 0));
                    return;
                }

                List<String> command = new ArrayList<>();
                command.add("java");
                command.add("-jar");
                command.add("tools/junit-platform-console-standalone.jar");
                command.add("--class-path");
                command.add(classDir.toString());
                command.add("--scan-class-path");
                command.add("--details=tree");
                command.add("--details-theme=ascii");

                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectErrorStream(true);
                Process process = pb.start();

                List<String> outputLines;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    outputLines = reader.lines().collect(Collectors.toList());
                }

                int passed = 0, failed = 0, aborted = 0;
                for (String line : outputLines) {
                    line = line.trim();

                    if (line.matches("\\[\\s*\\d+ tests successful\\s*\\]")) {
                        passed = extractNumber(line);
                    } else if (line.matches("\\[\\s*\\d+ tests failed\\s*\\]")) {
                        failed = extractNumber(line);
                    } else if (line.matches("\\[\\s*\\d+ tests aborted\\s*\\]")) {
                        aborted = extractNumber(line);
                    }
                }

                System.out.printf("Результат для " + taskPath + ": прошло %d, провалено %d, прервано %d\n", passed, failed, aborted);
                testResults.put(taskPath, Arrays.asList(passed, failed, aborted));

            } catch (Exception e) {
                System.err.println("Ошибка запуска тестов для " + taskPath + ": " + e.getMessage());
                testResults.put(taskPath, Arrays.asList(0, 0, 0));
            }
        });

        return testResults;
    }

    private int extractNumber(String line) {
        return Integer.parseInt(line.replaceAll("[^0-9]", ""));
    }

    private List<Path> getJavaFiles(Path root) throws IOException {
        if (!Files.exists(root)) return Collections.emptyList();
        try (Stream<Path> stream = Files.walk(root)) {
            return stream.filter(p -> p.toString().endsWith(".java")).collect(Collectors.toList());
        }
    }
}
