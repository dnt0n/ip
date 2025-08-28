import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime byTime;

    public Deadline(String name, String byTime) throws JustAChillGuyException {
        super(name);
        this.byTime = Parser.parseStringIntoLocalDateTime(byTime);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
        return "[D]" + super.toString() + " (by: " + this.byTime.format(formatter) + ")";
    }

    @Override
    public String saveFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HHmm");
        return "D | " + (this.isDone() ? 1 : 0) + " | " + this.getName() + " | " + this.byTime.format(formatter);
    }
}
