package justachillguy;

/**
 * Handles display formatting and output for the program.
 */
public class UI {

    /**
     * Wraps a message string with horizontal divider lines
     * and indents each line for consistent display formatting.
     *
     * @param str the message to wrap
     * @return the formatted string
     */
    public static String wrapInLines(String str) {
        String[] lines = str.split("\n");
        StringBuilder sb = new StringBuilder();
        sb.append("______________________________________________________________________________\n");
        for (String line : lines) {
            sb.append("  ").append(line).append("\n");
        }
        sb.append("_______________________________________________________________________________\n");
        return sb.toString();
    }

    /**
     * Displays a formatted message to the console.
     *
     * @param message the message to display
     */
    public static void display(String message) {
        System.out.println(wrapInLines(message));
    }
}
