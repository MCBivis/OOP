package dslchecker.model;

import java.util.Objects;

public class Assignment {
    private final String taskId;
    private final String github;

    public Assignment(String taskId, String github) {
        this.taskId = taskId;
        this.github = github;
    }

    public String getTaskId() { return taskId; }
    public String getGithub() { return github; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(github, that.github);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "taskId='" + taskId + '\'' +
                ", github='" + github + '\'' +
                '}';
    }
}
