package dslchecker;

import dslchecker.report.HtmlReportDataBuilder;
import dslchecker.report.HtmlReportGenerator;
import dslchecker.repos.*;
import dslchecker.config.CourseConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String repoRoot = "repos";
        CourseConfig courseConfig = new CourseConfig();
        Binding binding = new Binding();
        binding.setVariable("course", courseConfig);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File("CourseConfig.groovy"));

        RepoLoader.loadRepos(courseConfig);

        CompilationChecker compilationChecker = new CompilationChecker(courseConfig, repoRoot, "build/classes");
        List<String> successfulCompiledTasks = compilationChecker.checkAll();

        JavadocGenerator javaDocGenerator = new JavadocGenerator(repoRoot, "build/docs", successfulCompiledTasks);
        List<String> successfulDocGeneratedTasks = javaDocGenerator.generateAll();

        StyleChecker styleChecker = new StyleChecker(repoRoot, successfulCompiledTasks);
        List<String> successfulStyleGeneratedTasks = styleChecker.checkAll();

        TestRunner testRunner = new TestRunner(courseConfig, repoRoot,"build/classes", successfulCompiledTasks);
        Map<String, List<Integer>> testResults = testRunner.runAllTests();

        HtmlReportDataBuilder htmlReportDataBuilder = new HtmlReportDataBuilder(courseConfig, successfulCompiledTasks, successfulDocGeneratedTasks, successfulStyleGeneratedTasks, testResults);
        Map<String, Map<String, List<String>>> reportData = htmlReportDataBuilder.buildDetailedReportData();
        Map<String, List<String>> summaryTable = htmlReportDataBuilder.buildGroupSummaryData(reportData);

        HtmlReportGenerator.generateReport("report.html", reportData, summaryTable);
    }
}
