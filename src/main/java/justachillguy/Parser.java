package justachillguy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public static Object[] parseInputIntoCommandAndArgs(String input) throws JustAChillGuyException {
        if (input == null || input.trim().isEmpty()) {
            throw new JustAChillGuyException("Input cannot be empty");
        }

        String[] parts = input.trim().split(" ", 2);
        String commandWord = parts[0];
        String argsText = (parts.length > 1) ? parts[1].trim() : "";

        Command command = Command.from(commandWord);
        return new Object[]{command, argsText};
    }

    // parse input in the format 2003-6-19 1800 into LocalDateTime object
    public static LocalDateTime parseStringIntoLocalDateTime(String input) throws JustAChillGuyException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HHmm");
        try {
            return LocalDateTime.parse(input.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new JustAChillGuyException(
                    "Yo, I couldn’t understand that date/time format. \n"
                            + "Try using yyyy-M-d HHmm (e.g., 2003-6-19 1800). "
            );
        }
    }
}
