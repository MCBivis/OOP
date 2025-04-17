package dslchecker.config;

import dslchecker.model.*;
import groovy.lang.Closure;

import java.util.*;

public class CourseConfig {
    private final List<Task> tasks = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();
    private final List<Assignment> assignments = new ArrayList<>();
    private final List<Milestone> milestones = new ArrayList<>();
    private final Map<String, Object> settings = new HashMap<>();

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

    public void assignments(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void assign(String taskId, String github) {
                assignments.add(new Assignment(taskId, github));
            }
        });
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    public void milestones(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void milestone(String name, String date) {
                milestones.add(new Milestone(name, date));
            }
        });
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    public void settings(Closure<?> closure) {
        closure.setDelegate(settings);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    public List<Task> getTasks() { return tasks; }
    public List<Group> getGroups() { return groups; }
    public List<Assignment> getAssignments() { return assignments; }
    public List<Milestone> getMilestones() { return milestones; }
    public Map<String, Object> getSettings() { return settings; }
}
