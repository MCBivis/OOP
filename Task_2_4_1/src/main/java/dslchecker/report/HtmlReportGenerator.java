package dslchecker.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HtmlReportGenerator {

    public static void generateReport(
            String outputPath,
            Map<String, Map<String, List<String>>> reportData,
            Map<String, List<String>> summaryData
    ) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>")
                .append("table { border-collapse: collapse; width: 100%; margin-bottom: 40px; }")
                .append("th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }")
                .append("th { background-color: #f2f2f2; }")
                .append("</style><title>Отчет OOP Checker</title></head><body>");
        html.append("<h1>Отчет по задачам OOP</h1>");

        // Подробные отчеты по задачам
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

        // Сводная таблица
        html.append("<h1>Сводная таблица по студентам</h1>");

        for (String group : summaryData.keySet()) {
            html.append("<h2>Группа ").append(group).append("</h2>");
            List<String> rows = summaryData.get(group);

            if (rows != null && rows.size() > 1) {
                String[] headerParts = rows.get(0).split("\t");
                html.append("<table><tr>");
                for (String header : headerParts) {
                    html.append("<th>").append(header).append("</th>");
                }
                html.append("</tr>");

                for (int i = 1; i < rows.size(); i++) {
                    String row = rows.get(i);
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
