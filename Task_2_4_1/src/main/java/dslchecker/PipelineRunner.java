package dslchecker;

import dslchecker.config.CourseConfig;
import dslchecker.report.HtmlReportDataBuilder;
import dslchecker.report.HtmlReportGenerator;
import dslchecker.repos.*;

import java.util.List;
import java.util.Map;

public class PipelineRunner {

    private final CourseConfig courseConfig;
    private final String repoRoot;
    private final String buildClasses;
    private final String buildDocs;
    private final String reportHtml;

    public PipelineRunner(CourseConfig courseConfig, String repoRoot, String buildClasses, String buildDocs, String reportHtml) {
        this.courseConfig = courseConfig;
        this.repoRoot = repoRoot;
        this.buildClasses = buildClasses;
        this.buildDocs = buildDocs;
        this.reportHtml = reportHtml;
    }

    public void runFullPipeline() {
        // 1. Clone repos
        RepoLoader.loadRepos(courseConfig);

        // 2. Compile
        CompilationChecker compilationChecker = new CompilationChecker(courseConfig, repoRoot, buildClasses);
        List<String> successfulCompiledTasks = compilationChecker.checkAll();

        // 3. Generate javadoc
        JavadocGenerator javadocGenerator = new JavadocGenerator(repoRoot, buildDocs, successfulCompiledTasks);
        List<String> successfulDocs = javadocGenerator.generateAll();

        // 4. Check style
        StyleChecker styleChecker = new StyleChecker(repoRoot, successfulCompiledTasks);
        List<String> successfulStyle = styleChecker.checkAll();

        // 5. Run tests
        TestRunner testRunner = new TestRunner(repoRoot, buildClasses, successfulCompiledTasks);
        Map<String, List<Integer>> testResults = testRunner.runAllTests();

        // 6. Build report data
        HtmlReportDataBuilder builder = new HtmlReportDataBuilder(courseConfig, successfulCompiledTasks, successfulDocs, successfulStyle, testResults);
        Map<String, Map<String, List<String>>> reportData = builder.buildDetailedReportData();
        Map<String, List<String>> summaryTable = builder.buildGroupSummaryData(reportData);

        // 7. Generate report
        HtmlReportGenerator.generateReport(reportHtml, reportData, summaryTable);
    }
}
