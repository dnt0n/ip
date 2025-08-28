public enum Command {
    BYE, HELLO, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, UNKNOWN;

    public static Command from(String commandWord) {
        try {
            return Command.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
