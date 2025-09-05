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
