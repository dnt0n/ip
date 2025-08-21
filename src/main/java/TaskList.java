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
        System.out.println(JustAChillGuy.wrapInLines("added: " + task));
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
