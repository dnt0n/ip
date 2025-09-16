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

        Task task;
        String tag;

        switch (type) {
        case "T":
            tag = components.length > 3 ? components[3] : "";
            task = getTodo(taskName, isDone, tag);
            break;

        case "D":
            String by = components[3];
            tag = components.length > 4 ? components[4] : "";
            task = getDeadline(taskName, by, isDone, tag);
            break;

        case "E":
            String from = components[3];
            String to = components[4];
            tag = components.length > 5 ? components[5] : "";
            task = getEvent(taskName, from, to, isDone, tag);
            break;

        default:
            return null;
        }

        return task;
    }

    private static Task getEvent(String taskName, String from, String to, boolean isDone, String tag) throws JustAChillGuyException {
        Task event = new Event(taskName, from, to);
        if (isDone) {
            event.mark();
        }
        if (!tag.isEmpty()) {
            event.addTag(tag);
        }
        return event;
    }

    private static Task getDeadline(String taskName, String by, boolean isDone, String tag) throws JustAChillGuyException {
        Task deadline = new Deadline(taskName, by);
        if (isDone) {
            deadline.mark();
        }
        deadline.addTag(tag);
        return deadline;
    }

    private static Task getTodo(String taskName, boolean isDone, String tag) {
        Task todo = new ToDo(taskName);
        if (isDone) {
            todo.mark();
        }
        todo.addTag(tag);
        return todo;
    }
}
