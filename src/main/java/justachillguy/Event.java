package justachillguy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs during a specific time period.
 * Stores the task name along with a start and end time.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates a new {@code Event} task.
     *
     * @param name the name of the task
     * @param from the starting time in string format, parsed into {@link LocalDateTime}
     * @param to   the ending time in string format, parsed into {@link LocalDateTime}
     * @throws JustAChillGuyException if the time strings cannot be parsed
     */
    public Event(String name, String from, String to) throws JustAChillGuyException {
        super(name);
        this.from = Parser.parseStringIntoLocalDateTime(from);
        this.to = Parser.parseStringIntoLocalDateTime(to);
    }

    /**
     * Returns a string representation of the event task,
     * including its type, completion status, name, and time range.
     *
     * @return formatted string for display
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
        return "[E]" + super.toString()
                + " (from: " + this.from.format(formatter)
                + " to: " + this.to.format(formatter) + ")";
    }

    /**
     * Returns a formatted string suitable for saving to storage.
     *
     * @return string in save file format
     */
    @Override
    public String getSaveFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HHmm");
        return "E | " + (this.isDone() ? 1 : 0) + " | "
                + this.getName() + " | "
                + this.from.format(formatter) + " | "
                + this.to.format(formatter)
                + (this.isTagged() ? " | " + this.getTag() : "");
    }
}
