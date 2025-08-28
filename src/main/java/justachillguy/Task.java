package justachillguy;

public class Task {
    private String name;
    private boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getName() {
        return this.name;
    }

    public String saveFormat() {
        return "Task | " + (this.isDone() ? 1 : 0) + " | " + this.getName();
    }

    public boolean containsKeyword(String keyword) {
        return this.name.contains(keyword);
    }

    @Override
    public String toString() {
        String status = "[" + (this.isDone ? "X" : " ") + "] ";
        return status + this.name;
    }
}
