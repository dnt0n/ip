package justachillguy;

/**
 * Represents a simple to-do task without any time constraints.
 */
public class ToDo extends Task {

    /**
     * Creates a new {@code ToDo} task.
     *
     * @param name the name of the task
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Returns a string representation of the to-do task,
     * including its type and completion status.
     *
     * @return formatted string for display
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a formatted string suitable for saving to storage.
     *
     * @return string in save file format
     */
    @Override
    public String saveFormat() {
        return "T | " + (this.isDone() ? 1 : 0) + " | " + this.getName();
    }
}
