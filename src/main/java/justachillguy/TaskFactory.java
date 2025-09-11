package justachillguy;

/**
 * Factory class for creating {@link Task} objects from their saved string format.
 */
public class TaskFactory {

    /**
     * Parses a line of text into the corresponding {@link Task}.
     * <p>
     * Expected formats:
     * <ul>
     *     <li>{@code T | 0 | task name}</li>
     *     <li>{@code D | 1 | task name | yyyy-M-d HHmm}</li>
     *     <li>{@code E | 0 | task name | yyyy-M-d HHmm | yyyy-M-d HHmm}</li>
     * </ul>
     *
     * @param line the saved string representation of a task
     * @return the corresponding {@code Task}, or {@code null} if type is unknown
     * @throws JustAChillGuyException if parsing a date/time fails
     */
    public static Task parseTask(String line) throws JustAChillGuyException {
        String[] components = line.split(" \\| ");
        String type = components[0];
        boolean isDone = components[1].equals("1");
        String taskName = components[2];

        switch (type) {
        case "T":
            return getTodo(taskName, isDone);

        case "D":
            String by = components[3];
            return getDeadline(taskName, by, isDone);

        case "E":
            String from = components[3];
            String to = components[4];
            return getEvent(taskName, from, to, isDone);

        default:
            return null;
        }
    }

    private static Task getEvent(String taskName, String from, String to, boolean isDone) throws JustAChillGuyException {
        Task event = new Event(taskName, from, to);
        if (isDone) {
            event.mark();
        }
        return event;
    }

    private static Task getDeadline(String taskName, String by, boolean isDone) throws JustAChillGuyException {
        Task deadline = new Deadline(taskName, by);
        if (isDone) {
            deadline.mark();
        }
        return deadline;
    }

    private static Task getTodo(String taskName, boolean isDone) {
        Task todo = new ToDo(taskName);
        if (isDone) {
            todo.mark();
        }
        return todo;
    }
}
