package dslchecker.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HtmlReportGenerator {

    public static void generateReport(String outputPath, Map<String, Map<String, List<String>>> reportData) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>")
                .append("table { border-collapse: collapse; width: 100%; margin-bottom: 40px; }")
                .append("th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }")
                .append("th { background-color: #f2f2f2; }")
                .append("</style><title>Отчет OOP Checker</title></head><body>");
        html.append("<h1>Отчет по задачам OOP</h1>");

        for (String group : reportData.keySet()) {
            html.append("<h2>Группа ").append(group).append("</h2>");

            Map<String, List<String>> tasks = reportData.get(group);
            for (String task : tasks.keySet()) {
                html.append("<h3>Задача ").append(task).append("</h3>");
                html.append("<table><tr><th>Студент</th><th>Сборка</th><th>Документация</th><th>Style Guide</th><th>Тесты</th><th>Доп. балл</th><th>Общий балл</th></tr>");

                List<String> rows = tasks.get(task);
                for (String row : rows) {
                    html.append("<tr>");
                    for (String col : row.split("\t")) {
                        html.append("<td>").append(col).append("</td>");
                    }
                    html.append("</tr>");
                }

                html.append("</table>");
            }
        }

        html.append("</body></html>");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
            System.out.println("HTML-отчет успешно создан: " + outputPath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи HTML-отчета: " + e.getMessage());
        }
    }
}
