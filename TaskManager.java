import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String title, String priority) {
        tasks.add(new Task(title, priority));
        System.out.println("Task added successfully!");
    }

    public void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println((i + 1) + ". " + t.getTitle() +
                    " | Priority: " + t.getPriority() +
                    " | Completed: " + t.isCompleted());
        }
    }
}