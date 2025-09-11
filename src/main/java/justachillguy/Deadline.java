package justachillguy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * Stores the task name and the date/time by which it should be completed.
 */
public class Deadline extends Task {
    private LocalDateTime byTime;

    /**
     * Creates a new {@code Deadline} task.
     *
     * @param name   the name of the task
     * @param byTime the deadline in string format, parsed into {@link LocalDateTime}
     * @throws JustAChillGuyException if the deadline cannot be parsed
     */
    public Deadline(String name, String byTime) throws JustAChillGuyException {
        super(name);
        this.byTime = Parser.parseStringIntoLocalDateTime(byTime);
    }

    /**
     * Returns a string representation of the deadline task,
     * including its type, completion status, name, and deadline.
     *
     * @return formatted string for display
     */
    @Override
    public String toString() {
        // note to self: remember to remove magic strings like this and make them constants
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
        return "[D]" + super.toString() + " (by: " + this.byTime.format(formatter) + ")";
    }

    /**
     * Returns a formatted string suitable for saving to storage.
     *
     * @return string in save file format
     */
    @Override
    public String getSaveFormat() {
        // note to self: remember to remove magic strings like this and make them constants
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HHmm");
        return "D | " + (this.isDone() ? 1 : 0) + " | " + this.getName() + " | " + this.byTime.format(formatter);
    }
}
