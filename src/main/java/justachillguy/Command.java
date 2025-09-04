package justachillguy;

/**
 * Represents the set of valid commands supported by the {@code JustAChillGuy} program.
 * <p>
 * Each enum constant corresponds to a specific user command that can be
 * entered through the console interface. This provides a type-safe way
 * to handle user input and map it to program actions.
 */
public enum Command {
    GREET, HELLO, BYE, HELP, LIST, FIND, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN;

    public static Command from(String commandWord) {
        try {
            return Command.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
