package dslchecker.model;

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
    public String toString() {
        return "Assignment{" +
                "taskId='" + taskId + '\'' +
                ", github='" + github + '\'' +
                '}';
    }
}
