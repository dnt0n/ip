package justachillguy;

import justachillguy.Deadline;
import justachillguy.Event;

public class TaskFactory {
    public static Task parseTask(String line) throws JustAChillGuyException {
        String[] components = line.split(" \\| ");
        String type = components[0];
        boolean isDone = components[1].equals("1");
        String taskName = components[2];

        switch (type) {
            case "T":
                Task todo = new ToDo(taskName);
                if (isDone) {
                    todo.mark();
                }
                return todo;

            case "D":
                String by = components[3];
                Task deadline = new Deadline(taskName, by);
                if (isDone) {
                    deadline.mark();
                }
                return deadline;

            case "E":
                String from = components[3];
                String to = components[4];
                Task event = new Event(taskName, from, to);
                if (isDone) {
                    event.mark();
                }
                return event;

            default:
                return null;
        }
    }
}
