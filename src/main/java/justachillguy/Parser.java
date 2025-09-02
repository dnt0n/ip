package justachillguy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing user input into commands and date/time values.
 */
public class Parser {

    /**
     * Parses a raw input string into a {@link Command} and its arguments.
     *
     * @param input the raw input string entered by the user
     * @return an Object array containing the command of Command type and String that is the argument text
     * @throws JustAChillGuyException if the input is empty or invalid
     */
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

    /**
     * Parses a date-time string in the format {@code yyyy-M-d HHmm} into a {@link LocalDateTime}.
     * <p>
     * Example: {@code "2003-6-19 1800"} → {@code 2003-06-19T18:00}.
     *
     * @param input the date-time string to parse
     * @return the corresponding {@code LocalDateTime} object
     * @throws JustAChillGuyException if the input cannot be parsed
     */
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
