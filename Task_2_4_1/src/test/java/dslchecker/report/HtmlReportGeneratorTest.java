package dslchecker.report;

import dslchecker.repos.*;
import dslchecker.config.CourseConfig;
import dslchecker.repos.RepoLoader;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HtmlReportGeneratorTest {

    private static final String REPO_ROOT = "repos";
    private static final String BUILD_CLASSES = "build/classes";
    private static final String BUILD_DOCS = "build/docs";
    private static final String REPORT_HTML = "report.html";

    private CourseConfig courseConfig;

    @BeforeEach
    public void setup() throws IOException {
        courseConfig = new CourseConfig();
        Binding binding = new Binding();
        binding.setVariable("course", courseConfig);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File("CourseConfig.groovy"));
        deleteFileOrDirectory(new File(REPORT_HTML));
    }

    @Test
    public void testFullPipelineAndHtmlReportCreation() throws Exception {
        // 1. Clone repos
        RepoLoader.loadRepos(courseConfig);

        // 2. Compile
        CompilationChecker compilationChecker = new CompilationChecker(courseConfig, REPO_ROOT, BUILD_CLASSES);
        List<String> successfulCompiledTasks = compilationChecker.checkAll();

        // 3. Generate javadoc
        JavadocGenerator javadocGenerator = new JavadocGenerator(REPO_ROOT, BUILD_DOCS, successfulCompiledTasks);
        List<String> successfulDocs = javadocGenerator.generateAll();

        // 4. Check style
        StyleChecker styleChecker = new StyleChecker(REPO_ROOT, successfulCompiledTasks);
        List<String> successfulStyle = styleChecker.checkAll();

        // 5. Run tests
        TestRunner testRunner = new TestRunner(courseConfig, REPO_ROOT, BUILD_CLASSES, successfulCompiledTasks);
        Map<String, List<Integer>> testResults = testRunner.runAllTests();

        // 6. Build report data
        HtmlReportDataBuilder builder = new HtmlReportDataBuilder(courseConfig, successfulCompiledTasks, successfulDocs, successfulStyle, testResults);
        Map<String, Map<String, List<String>>> reportData = builder.buildDetailedReportData();
        Map<String, List<String>> summaryTable = builder.buildGroupSummaryData(reportData);

        // 7. Generate report
        HtmlReportGenerator.generateReport(REPORT_HTML, reportData, summaryTable);

        // 8. Assert report file exists
        File reportFile = new File(REPORT_HTML);
        assertTrue(reportFile.exists(), "HTML report should be generated: " + REPORT_HTML);
    }

    private void deleteFileOrDirectory(File file) {
        if (!file.exists()) return;
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteFileOrDirectory(f);
                }
            }
        }
        file.delete();
    }
}
