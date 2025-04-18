package dslchecker.repos;

import dslchecker.config.CourseConfig;
import dslchecker.model.Group;
import dslchecker.model.Student;

import java.io.IOException;

public class RepoLoader {
    public static void loadRepos(CourseConfig config) throws IOException, InterruptedException {
        for (Group group : config.getGroups()) {
            System.out.println("Group: " + group.getName());
            for (Student student : group.getStudents()) {
                System.out.println("  " + student.getFullName() + " (" + student.getGithub() + ")");
                System.out.println("  Репозиторий: " + student.getRepo());
                String cmd = "git clone " + student.getRepo() + " repos/" + student.getGithub();
                System.out.println("  Выполняю: " + cmd);
                Process p = Runtime.getRuntime().exec(cmd);
                p.waitFor();
            }
        }

        System.out.println("Все репозитории загружены.");
    }
}
