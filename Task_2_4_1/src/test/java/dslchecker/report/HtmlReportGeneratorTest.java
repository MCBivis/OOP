package dslchecker.report;

import dslchecker.PipelineRunner;
import dslchecker.config.CourseConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

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
    public void testFullPipelineAndHtmlReportCreation(){
        PipelineRunner runner = new PipelineRunner(courseConfig, REPO_ROOT, BUILD_CLASSES, BUILD_DOCS, REPORT_HTML);
        runner.runFullPipeline();

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
