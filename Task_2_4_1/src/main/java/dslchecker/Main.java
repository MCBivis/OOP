package dslchecker;

import dslchecker.config.CourseConfig;
import dslchecker.model.Student;
import dslchecker.model.Group;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Чтение DSL
        CourseConfig courseConfig = new CourseConfig();
        Binding binding = new Binding();
        binding.setVariable("course", courseConfig);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File("CourseConfig.groovy"));

        // Вывод — студенты и репозитории
        for (Group group : courseConfig.getGroups()) {
            System.out.println("Group: " + group.getName());
            for (Student student : group.getStudents()) {
                System.out.println("  " + student.getFullName() + " (" + student.getGithub() + ")");
                System.out.println("  Репозиторий: " + student.getRepo());
                // Загрузка репозитория
                String cmd = "git clone " + student.getRepo() + " repos/" + student.getGithub();
                System.out.println("  ➜ Выполняю: " + cmd);
                Process p = Runtime.getRuntime().exec(cmd);
                p.waitFor(); // Подождать завершения
            }
        }

        System.out.println("Все репозитории загружены.");
    }
}
