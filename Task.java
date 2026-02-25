public class Task {
    private String title;
    private String priority;
    private boolean completed;

    public Task(String title, String priority) {
        this.title = title;
        this.priority = priority;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }
}
