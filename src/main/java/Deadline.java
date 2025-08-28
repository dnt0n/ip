public class Deadline extends Task {
    private String byTime;

    public Deadline(String name, String byTime) {
        super(name);
        this.byTime = byTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.byTime + ")";
    }

    @Override
    public String saveFormat() {
        return "D | " + (this.isDone() ? 1 : 0) + " | " + this.getName() + " | " + this.byTime;
    }
}
