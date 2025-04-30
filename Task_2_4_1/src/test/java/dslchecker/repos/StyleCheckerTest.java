package dslchecker.repos;

import dslchecker.config.CourseConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StyleCheckerTest {

    private static final String REPO_ROOT = "repos";
    private static final String BUILD_CLASSES = "build/classes";

    private CourseConfig courseConfig;

    @BeforeEach
    public void setup() throws IOException {
        courseConfig = new CourseConfig();
        Binding binding = new Binding();
        binding.setVariable("course", courseConfig);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File("TestConfig.groovy"));
    }

    @Test
    public void testFullPipelineAndHtmlReportCreation() throws Exception {
        RepoLoader.loadRepos(courseConfig);

        CompilationChecker compilationChecker = new CompilationChecker(courseConfig, REPO_ROOT, BUILD_CLASSES);
        List<String> successfulCompiledTasks = compilationChecker.checkAll();

        StyleChecker styleChecker = new StyleChecker(REPO_ROOT, successfulCompiledTasks);
        List<String> successfulStyle = styleChecker.checkAll();

        List<String> waitingResult = new ArrayList<>();
        waitingResult.add("MCBivis/Task_Test");
        assertTrue(successfulStyle.equals(waitingResult));
    }
}
