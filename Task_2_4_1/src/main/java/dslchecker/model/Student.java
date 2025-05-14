package dslchecker.model;

public class Student {
    private String github;
    private String fullName;
    private String repo;

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "github='" + github + '\'' +
                ", fullName='" + fullName + '\'' +
                ", repo='" + repo + '\'' +
                '}';
    }
}
