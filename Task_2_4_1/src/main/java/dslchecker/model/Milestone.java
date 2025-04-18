package dslchecker.model;

public class Milestone {
    private final String name;
    private final String date;

    public Milestone(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() { return name; }
    public String getDate() { return date; }

    @Override
    public String toString() {
        return "Milestone{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
