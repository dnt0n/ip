public enum Command {
    HELLO, BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN;

    public static Command from(String commandWord) {
        try {
            return Command.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
