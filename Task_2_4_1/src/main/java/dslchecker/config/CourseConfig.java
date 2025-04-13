package dslchecker.config;

import dslchecker.model.*;
import groovy.lang.Closure;

import java.util.*;

public class CourseConfig {
    private final List<Task> tasks = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();

    public void tasks(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void task(String id, Closure<?> inner) {
                Task task = new Task();
                task.setId(id);
                inner.setDelegate(task);
                inner.setResolveStrategy(Closure.DELEGATE_ONLY);
                inner.call();
                tasks.add(task);
            }
        });
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    public void groups(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void group(String name, Closure<?> inner) {
                Group group = new Group();
                group.setName(name);

                inner.setDelegate(new Object() {
                    public void student(Closure<?> studentClosure) {
                        Student student = new Student();
                        studentClosure.setDelegate(student);
                        studentClosure.setResolveStrategy(Closure.DELEGATE_ONLY);
                        studentClosure.call();
                        group.getStudents().add(student);
                    }
                });

                inner.setResolveStrategy(Closure.DELEGATE_ONLY);
                inner.call();
                groups.add(group);
            }
        });
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
