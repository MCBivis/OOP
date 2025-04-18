package dslchecker;

import dslchecker.report.HtmlReportGenerator;
import dslchecker.repos.*;
import dslchecker.config.CourseConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.util.LinkedHashMap;
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
        testRunner.runAllTests();

        Map<String, Map<String, List<String>>> data = new LinkedHashMap<>();

        Map<String, List<String>> labTasks = new LinkedHashMap<>();
        labTasks.put("2_1_1 (Простые числа)", List.of(
                "Студент No1\t+\t+\t+\t10/0/0\t0\t1",
                "Студент No2\t+\t-\t-\t0/0/0\t0\t0"
        ));
        labTasks.put("2_3_1 (Змейка)", List.of(
                "Студент No1\t-\t-\t-\t0/0/0\t0\t0",
                "Студент No2\t+\t+\t+\t4/0/0\t1\t2"
        ));

        data.put("12345", labTasks);

        HtmlReportGenerator.generateReport("report.html", data);
    }
}
