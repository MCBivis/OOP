package dslchecker;

import dslchecker.config.CourseConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        String repoRoot = "repos";
        CourseConfig courseConfig = new CourseConfig();
        Binding binding = new Binding();
        binding.setVariable("course", courseConfig);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File("CourseConfig.groovy"));

        PipelineRunner runner = new PipelineRunner(courseConfig, repoRoot, "build/classes", "build/docs", "report.html");
        runner.runFullPipeline();
    }
}
