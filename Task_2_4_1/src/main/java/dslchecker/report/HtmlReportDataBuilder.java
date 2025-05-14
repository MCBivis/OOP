package dslchecker.report;

import dslchecker.config.CourseConfig;
import dslchecker.model.Assignment;
import dslchecker.model.Group;
import dslchecker.model.Student;
import dslchecker.model.Task;

import java.util.*;

public class HtmlReportDataBuilder {

    private final CourseConfig courseConfig;
    private final List<String> successfulCompiledTasks;
    private final List<String> successfulDocGeneratedTasks;
    private final List<String> successfulStyleGeneratedTasks;
    private final Map<String, List<Integer>> testResults;

    public HtmlReportDataBuilder(
            CourseConfig courseConfig,
            List<String> successfulCompiledTasks,
            List<String> successfulDocGeneratedTasks,
            List<String> successfulStyleGeneratedTasks,
            Map<String, List<Integer>> testResults
    ) {
        this.courseConfig = courseConfig;
        this.successfulCompiledTasks = successfulCompiledTasks;
        this.successfulDocGeneratedTasks = successfulDocGeneratedTasks;
        this.successfulStyleGeneratedTasks = successfulStyleGeneratedTasks;
        this.testResults = testResults;
    }

    public Map<String, Map<String, List<String>>> buildDetailedReportData() {
        Map<String, Map<String, List<String>>> report = Collections.synchronizedMap(new LinkedHashMap<>());

        courseConfig.getGroups().parallelStream().forEach(group -> {
            Map<String, List<String>> groupReport = Collections.synchronizedMap(new LinkedHashMap<>());

            courseConfig.getTasks().parallelStream().forEach(task -> {
                List<String> rows = new ArrayList<>();

                for (Student student : group.getStudents()) {
                    String github = student.getGithub();
                    String id = task.getId();
                    Assignment assignment = new Assignment(id, github);
                    if (!courseConfig.getAssignments().contains(assignment)) continue;

                    String key = github + "/" + id;

                    boolean compiled = successfulCompiledTasks.contains(key);
                    boolean documented = successfulDocGeneratedTasks.contains(key);
                    boolean styled = successfulStyleGeneratedTasks.contains(key);

                    List<Integer> testResult = testResults.getOrDefault(key, List.of(0, 0, 0));
                    int passed = testResult.get(0);
                    int failed = testResult.get(1);
                    int skipped = testResult.get(2);

                    double bonus = courseConfig.getBonusPoints(github, id);
                    double score = (compiled ? 1 : 0) & (passed > 0 ? 1 : 0);
                    score += bonus;

                    String row = String.join("\t", List.of(
                            student.getFullName(),
                            compiled ? "+" : "-",
                            documented ? "+" : "-",
                            styled ? "+" : "-",
                            String.format("%d/%d/%d", passed, failed, skipped),
                            formatScore(bonus),
                            formatScore(score)
                    ));

                    rows.add(row);
                }

                if (!rows.isEmpty()) {
                    groupReport.put(task.getId() + " (" + task.getTitle() + ")", rows);
                }
            });

            report.put(group.getName(), groupReport);
        });

        return report;
    }

    public Map<String, List<String>> buildGroupSummaryData(Map<String, Map<String, List<String>>> detailedReportData) {
        Map<String, List<String>> summary = new LinkedHashMap<>();

        List<String> allTaskIds = courseConfig.getTasks().stream()
                .map(Task::getId)
                .toList();

        for (Group group : courseConfig.getGroups()) {
            List<String> rows = new ArrayList<>();

            List<String> header = new ArrayList<>();
            header.add("Студент");
            header.addAll(allTaskIds);
            header.add("Сумма");
            header.add("Оценка");
            rows.add(String.join("\t", header));

            Map<String, List<String>> groupData = detailedReportData.get(group.getName());
            if (groupData == null) continue;

            for (Student student : group.getStudents()) {
                Map<String, Double> taskScores = new LinkedHashMap<>();

                for (Task task : courseConfig.getTasks()) {
                    String taskId = task.getId();
                    String taskLabel = taskId + " (" + task.getTitle() + ")";
                    List<String> taskRows = groupData.get(taskLabel);
                    if (taskRows == null) continue;

                    for (String row : taskRows) {
                        String[] cols = row.split("\t");
                        if (cols.length < 7) continue;

                        String name = cols[0];
                        if (!name.equals(student.getFullName())) continue;

                        double score = Double.parseDouble(cols[6].replace(",", "."));
                        taskScores.put(taskId, score);
                        break;
                    }
                }

                double total = taskScores.values().stream().mapToDouble(Double::doubleValue).sum();

                List<String> row = new ArrayList<>();
                row.add(student.getFullName());

                for (String taskId : allTaskIds) {
                    double score = taskScores.getOrDefault(taskId, 0.0);
                    row.add(formatScore(score));
                }

                row.add(formatScore(total));
                row.add(determineGrade(total));

                rows.add(String.join("\t", row));
            }

            summary.put(group.getName(), rows);
        }

        return summary;
    }



    private String determineGrade(double total) {
        if (total >= 5) return "отлично";
        if (total >= 4) return "хорошо";
        if (total >= 3) return "удовл.";
        return "неуд.";
    }

    private String formatScore(double score) {
        if (score == Math.floor(score)) {
            return String.valueOf((int) score);
        } else {
            return String.format("%.1f", score);
        }
    }

}
