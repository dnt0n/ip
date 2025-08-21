public class TaskList {
    private Task[] taskList;
    private int currSize;

    public TaskList() {
        this.taskList = new Task[100];
        this.currSize = 0;
    }

    public void addTask(Task task) {
        taskList[currSize] = task;
        currSize++;
        System.out.println(JustAChillGuy.wrapInLines(
                "Sure man, I've added this task for ya: \n"
                + "  " + task + "\n"
                + "You've got " + this.currSize + " tasks in the list yea"
        ));
    }

    public void markTask(int i) {
        Task task = taskList[i-1];
        task.mark();
        System.out.println(JustAChillGuy.wrapInLines(
                "Yo, nice job! I've marked this task as done for ya: \n" + task
        ));
    }

    public void unmarkTask(int i) {
        Task task = taskList[i-1];
        task.unmark();
        System.out.println(JustAChillGuy.wrapInLines(
                "Alright! I've unmarked this task as not done for ya: \n" + task
        ));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currSize; i++) {
            Task task = taskList[i];
            sb.append(i+1).append('.').append(task).append("\n");
        }
        return sb.toString();
    }
}
