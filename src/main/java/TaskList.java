import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskList.add(task);
        UI.display(
                "Sure man, I've added this task for ya:\n"
                        + "  " + task + "\n"
                        + "You've got " + taskList.size() + " tasks in the list yea"
        );
    }

    public void markTask(int i) {
        if (i < 1 || i > taskList.size()) {
            UI.display("Hey, enter a valid index!");
            return;
        }

        Task task = taskList.get(i - 1);
        task.mark();
        UI.display(
                "Yo, nice job! I've marked this task as done for ya:\n" + task
        );
    }

    public void unmarkTask(int i) {
        if (i < 1 || i > taskList.size()) {
            UI.display("Hey, enter a valid index!");
            return;
        }

        Task task = taskList.get(i - 1);
        task.unmark();
        UI.display(
                "Alright! I've unmarked this task as not done for ya:\n" + task
        );
    }

    public void deleteTask(int i) {
        if (i < 1 || i > taskList.size()) {
            UI.display("Hey, enter a valid index!");
            return;
        }

        Task removedTask = taskList.remove(i - 1);
        UI.display(
                "Got it, I've removed this task for ya:\n"
                        + "  " + removedTask + "\n"
                        + "Now you have " + taskList.size() + " tasks left in the list."
        );
    }

    @Override
    public String toString() {
        if (taskList.isEmpty()) {
            return "Your list is empty! Chill day yea?";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            sb.append(i + 1).append('.').append(task).append("\n");
        }
        return sb.toString();
    }
}
