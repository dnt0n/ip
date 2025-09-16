package justachillguy;

/**
 * Represents a general task with a name and completion status.
 * Serves as the base class for more specific task types.
 */
public class Task {
    private String name;
    private boolean isDone;
    private String tag;

    /**
     * Creates a new {@code Task}.
     *
     * @param name the name of the task
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
        this.tag = "";
    }

    /**
     * Creates a new {@code Task} with the given name and tag.
     *
     * @param name the task name
     * @param tag  the initial tag for the task
     */
    public Task(String name, String tag) {
        this(name);
        addTag(tag);
    }

    /**
     * Adds or updates the task's tag.
     *
     * @param tag the tag to set
     * @throws IllegalArgumentException if the tag is null or empty
     */
    public void addTag(String tag) {
        this.tag = tag.trim();
    }

    /**
     * Removes the task's tag, leaving it untagged.
     */
    public void removeTag() {
        this.tag = "";
    }

    /**
     * Checks if the task has a tag assigned.
     *
     * @return {@code true} if the task has a tag, {@code false} otherwise
     */
    public boolean isTagged() {
        return !tag.isEmpty();
    }

    /**
     * Gets the current tag of the task.
     *
     * @return the tag string, or an empty string if untagged
     */
    public String getTag() {
        return tag;
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns whether this task is completed.
     *
     * @return {@code true} if done, {@code false} otherwise
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the name of this task.
     *
     * @return the task name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a formatted string suitable for saving to storage.
     *
     * @return string in save file format
     */
    public String getSaveFormat() {
        return "Task | " + (this.isDone() ? 1 : 0) + " | "
                + this.getName()
                + (this.isTagged() ? " | " + this.tag : "");
    }

    /**
     * Checks if the task name contains the given keyword.
     *
     * @param keyword the keyword to check
     * @return {@code true} if the name contains the keyword, {@code false} otherwise
     */
    public boolean containsKeyword(String keyword) {
        return this.name.toLowerCase().contains(keyword);
    }

    /**
     * Returns a string representation of the task,
     * including its completion status and name.
     *
     * @return formatted string for display
     */
    @Override
    public String toString() {
        String status = "[" + (this.isDone ? "X" : " ") + "] ";
        return status + this.name + (this.isTagged() ? " [#" + this.tag + "]" : "");
    }
}
