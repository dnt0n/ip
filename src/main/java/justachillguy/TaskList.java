package justachillguy;

import java.util.ArrayList;

/**
 * Manages a list of {@link Task} objects and handles operations
 * such as adding, marking, unmarking, deleting, and finding tasks.
 * Also ensures tasks are saved through {@link Storage}.
 */
public class TaskList {
    private ArrayList<Task> taskList;
    private Storage storage;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Creates a task list by loading tasks from storage.
     *
     * @param s the storage object to use
     * @throws JustAChillGuyException if loading tasks fails
     */
    public TaskList(Storage s) throws JustAChillGuyException {
        this.storage = s;
        this.taskList = s.loadTasks();
    }

    /**
     * Adds a new task to the list and saves it.
     *
     * @param task the task to add
     * @throws JustAChillGuyException if saving fails
     */
    public void addTask(Task task) throws JustAChillGuyException {
        taskList.add(task);
        this.storage.saveTasks(this.taskList);

        UI.display(
                "Sure man, I've added this task for ya:\n"
                        + "  " + task + "\n"
                        + "You've got " + taskList.size() + " tasks in the list yea"
        );
    }

    /**
     * Marks a task as done, given its index.
     *
     * @param i the index of the task (1-based)
     * @throws JustAChillGuyException if saving fails
     */
    public void markTask(int i) throws JustAChillGuyException {
        if (i < 1 || i > taskList.size()) {
            UI.display("Hey, enter a valid index!");
            return;
        }

        Task task = taskList.get(i - 1);
        task.mark();
        this.storage.saveTasks(this.taskList);

        UI.display(
                "Yo, nice job! I've marked this task as done for ya:\n" + task
        );
    }

    /**
     * Unmarks a task (sets it as not done), given its index.
     *
     * @param i the index of the task (1-based)
     * @throws JustAChillGuyException if saving fails
     */
    public void unmarkTask(int i) throws JustAChillGuyException {
        if (i < 1 || i > taskList.size()) {
            UI.display("Hey, enter a valid index!");
            return;
        }

        Task task = taskList.get(i - 1);
        task.unmark();
        this.storage.saveTasks(this.taskList);

        UI.display(
                "Alright! I've unmarked this task as not done for ya:\n" + task
        );
    }

    /**
     * Deletes a task from the list, given its index.
     *
     * @param i the index of the task (1-based)
     * @throws JustAChillGuyException if saving fails
     */
    public void deleteTask(int i) throws JustAChillGuyException {
        if (i < 1 || i > taskList.size()) {
            UI.display("Hey, enter a valid index!");
            return;
        }

        Task removedTask = taskList.remove(i - 1);
        this.storage.saveTasks(this.taskList);

        UI.display(
                "Got it, I've removed this task for ya:\n"
                        + "  " + removedTask + "\n"
                        + "Now you have " + taskList.size() + " tasks left in the list."
        );
    }

    /**
     * Finds tasks that contain the given keyword.
     *
     * @param keyword the keyword to search for
     * @return a formatted string of matching tasks
     */
    public String findTasksBasedOnKeyword(String keyword) {
        StringBuilder sb = new StringBuilder();
        int idx = 1;
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.containsKeyword(keyword)) {
                sb.append(idx).append('.').append(task).append("\n");
                idx++;
            }
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of all tasks in the list.
     *
     * @return formatted task list, or a message if empty
     */
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
