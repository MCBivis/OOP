package dslchecker.model;

public class Task {
    private String id;
    private String title;
    private int maxScore;
    private String softDeadline;
    private String hardDeadline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getSoftDeadline() {
        return softDeadline;
    }

    public void setSoftDeadline(String softDeadline) {
        this.softDeadline = softDeadline;
    }

    public String getHardDeadline() {
        return hardDeadline;
    }

    public void setHardDeadline(String hardDeadline) {
        this.hardDeadline = hardDeadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", maxScore=" + maxScore +
                ", softDeadline='" + softDeadline + '\'' +
                ", hardDeadline='" + hardDeadline + '\'' +
                '}';
    }
}