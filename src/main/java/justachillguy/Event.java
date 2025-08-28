package justachillguy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String name, String from, String to) throws JustAChillGuyException {
        super(name);
        this.from = Parser.parseStringIntoLocalDateTime(from);
        this.to = Parser.parseStringIntoLocalDateTime(to);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
        return "[E]" + super.toString() + " (from: " + this.from.format(formatter) + " to: " + this.to.format(formatter) + ")";
    }

    @Override
    public String saveFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HHmm");
        return "E | " + (this.isDone() ? 1 : 0) + " | " + this.getName() + " | " + this.from.format(formatter) + " | " + this.to.format(formatter);
    }
}
