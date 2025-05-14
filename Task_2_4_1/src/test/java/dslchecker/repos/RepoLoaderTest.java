package dslchecker.repos;

import dslchecker.config.CourseConfig;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepoLoaderTest {

    private CourseConfig courseConfig;
    private static final String REPO_ROOT = "repos";

    @BeforeEach
    public void loadConfig() throws IOException {
        courseConfig = new CourseConfig();
        Binding binding = new Binding();
        binding.setVariable("course", courseConfig);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File("CourseConfig.groovy"));

        courseConfig.getGroups().stream()
                .flatMap(group -> group.getStudents().stream())
                .map(student -> new File(REPO_ROOT + "/" + student.getGithub()))
                .forEach(this::deleteDirectory);
    }

    @Test
    public void testLoadAndUpdateRepos() {
        RepoLoader.loadRepos(courseConfig);

        courseConfig.getGroups().forEach(group ->
                group.getStudents().forEach(student -> {
                    File repoDir = new File(REPO_ROOT + "/" + student.getGithub());
                    assertTrue(repoDir.exists() && repoDir.isDirectory(),
                            "Репозиторий должен быть клонирован: " + repoDir.getPath());
                })
        );

        RepoLoader.loadRepos(courseConfig);

        courseConfig.getGroups().forEach(group ->
                group.getStudents().forEach(student -> {
                    File repoDir = new File(REPO_ROOT + "/" + student.getGithub());
                    assertTrue(repoDir.exists() && repoDir.isDirectory(),
                            "Репозиторий должен быть обновлён: " + repoDir.getPath());
                })
        );
    }

    @AfterEach
    public void cleanUp() {
        courseConfig.getGroups().stream()
                .flatMap(group -> group.getStudents().stream())
                .map(student -> new File(REPO_ROOT + "/" + student.getGithub()))
                .forEach(this::deleteDirectory);
    }

    private void deleteDirectory(File dir) {
        if (dir.exists()) {
            File[] entries = dir.listFiles();
            if (entries != null) {
                for (File file : entries) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            dir.delete();
        }
    }
}
