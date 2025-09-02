package justachillguy;

import java.util.Scanner;

/**
 * The {@code JustAChillGuy} class implements a simple interactive chatbot
 * that manages tasks such as ToDo, Deadlines, and Events.
 * <p>
 * It supports commands for greeting, showing help, adding, listing,
 * searching, updating, and deleting tasks. The chatbot also persists
 * tasks to a storage file so that they are saved between runs.
 * </p>
 */
public class JustAChillGuy {
    /** The display name of the chatbot. */
    private static final String NAME = "Just A Chill Guy";

    /** Greeting message displayed when the chatbot starts. */
    private static final String GREETING =
            "Yo! I am ✨" + NAME + "✨ :)\nHow can I help ya? \n(Enter the command help to see the full list of actions)";

    /** Standard reply for the {@code hello} command. */
    private static final String HELLO = "Hey, how is it going?";

    /** Goodbye message displayed when the program exits. */
    private static final String GOODBYE = "Goodbye mate! See ya next time!";

    /** Help menu that lists all supported commands. */
    private static final String HELP =
            "Here’s what I can do for ya:\n"
                    + "\n"
                    + "👉 Basic commands:\n"
                    + "   hello                - I’ll greet you back\n"
                    + "   bye                  - Exit the program\n"
                    + "   list                 - Show all your tasks\n"
                    + "   help                 - Show this help menu\n"
                    + "\n"
                    + "👉 Task management:\n"
                    + "   todo <task name>     - Add a ToDo task\n"
                    + "   deadline <name> /by <date/time>\n"
                    + "                        - Add a Deadline task with due date/time\n"
                    + "   event <name> /from <start> /to <end>\n"
                    + "                        - Add an Event task with start and end times\n"
                    + "\n"
                    + "👉 Task updates:\n"
                    + "   mark <index>         - Mark the task at position <index> as done\n"
                    + "   unmark <index>       - Mark the task at position <index> as not done\n"
                    + "   delete <index>       - Remove the task at position <index>\n"
                    + "\n"
                    + "👉 Search:\n"
                    + "   find <keyword>       - Find all tasks containing <keyword>\n"
                    + "\n"
                    + "👉 Notes:\n"
                    + "   - Index numbers start at 1 (as shown in list).\n"
                    + "   - Tasks are auto-saved after every change.\n"
                    + "   - Invalid input gives a friendly error instead of breaking the bot.\n"
                    + "\n"
                    + "✨ Chill and let me handle your tasks for ya! ✨";

    /** Path to the file where tasks are stored persistently. */
    private static final String FILE_PATH = "./data/justachillguy.txt";

    /**
     * Entry point for the chatbot program.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        run();
    }

    /**
     * Runs the chatbot. Initializes storage and task list,
     * displays greeting, and continuously accepts user input
     * until the {@code bye} command is entered.
     */
    public static void run() {
        boolean isRunning = true;
        Storage storage = new Storage(FILE_PATH);
        TaskList taskList = null;

        try {
            taskList = new TaskList(storage);
        } catch (JustAChillGuyException e) {
            UI.display(e.getMessage());
            taskList = new TaskList();
        }

        Scanner sc = new Scanner(System.in);

        UI.display(GREETING);

        while (isRunning) {
            String input = sc.nextLine().trim(); // to trim leading and trailing spaces
            try {
                Object[] parsed = Parser.parseInputIntoCommandAndArgs(input);
                Command command = (Command) parsed[0];
                String argsText = (String) parsed[1];
                // handleCommand will return false if the command is bye, true otherwise
                isRunning = handleCommand(command, argsText, taskList);
            } catch (JustAChillGuyException e) {
                UI.display(e.getMessage());
            }
        }
    }

    /**
     * Handles a single user command by delegating to the appropriate
     * action on the task list or UI.
     *
     * @param command  the command to execute
     * @param argsText the arguments provided with the command
     * @param taskList the current list of tasks
     * @return {@code false} if the command is {@code bye} (to stop the program),
     *         {@code true} otherwise
     * @throws JustAChillGuyException if the command arguments are invalid
     *                                or if the command is not recognized
     */
    public static boolean handleCommand(Command command, String argsText, TaskList taskList)
            throws JustAChillGuyException {
        switch (command) {
            case BYE:
                UI.display(GOODBYE);
                return false; // this will set isRunning to false;

            case HELLO:
                UI.display(HELLO);
                break;

            case HELP:
                UI.display(HELP);
                break;

            case LIST:
                UI.display(taskList.toString());
                break;

            case FIND:
                String keyword = argsText;
                String outputList = taskList.findTasksBasedOnKeyword(keyword);
                if (outputList.isEmpty()) {
                    UI.display("Oops, I can't find any matching tasks :(");
                } else {
                    UI.display("Sure! I've found these matching tasks for yea!\n" + outputList);
                }
                break;

            case MARK:
                try {
                    int index = Integer.parseInt(argsText);
                    taskList.markTask(index);
                } catch (NumberFormatException e) {
                    throw new JustAChillGuyException("Yo, your index isn't valid!");
                }
                break;

            case UNMARK:
                try {
                    int index = Integer.parseInt(argsText);
                    taskList.unmarkTask(index);
                } catch (NumberFormatException e) {
                    throw new JustAChillGuyException("Yo, your index isn't valid!");
                }
                break;

            case TODO:
                if (argsText.isEmpty()) {
                    throw new JustAChillGuyException("Yo, what todo task do you want me to add to the list?");
                }
                taskList.addTask(new ToDo(argsText));
                break;

            case DEADLINE:
                if (argsText.isEmpty()) { // if there is no argument body
                    throw new JustAChillGuyException("Yo, what deadline do you want me to add to the list?");
                }
                if (!argsText.contains("/by")) { // if no /by used
                    throw new JustAChillGuyException("Yo, specify the deadline using /by!");
                }

                String[] nameAndBy = argsText.split("/by", 2);
                String deadlineName = nameAndBy[0].trim();
                String by = nameAndBy[1].trim();

                if (deadlineName.isEmpty()) {
                    throw new JustAChillGuyException("Yo, your task has no name!");
                }
                if (by.isEmpty()) {
                    throw new JustAChillGuyException("Yo, what is the deadline of your task?");
                }

                taskList.addTask(new Deadline(deadlineName, by));
                break;

            case EVENT:
                if (argsText.isEmpty()) { // if no argument body
                    throw new JustAChillGuyException("Yo, what event do you want me to add to the list?");
                }
                if (!argsText.contains("/from") || !argsText.contains("/to")) { // if missing required keywords
                    throw new JustAChillGuyException("Yo, specify the event duration using /from and /to!");
                }

                String[] nameAndFrom = argsText.split("/from", 2);
                String eventName = nameAndFrom[0].trim();

                if (eventName.isEmpty()) {
                    throw new JustAChillGuyException("Yo, your event has no name!");
                }

                String[] fromAndTo = nameAndFrom[1].split("/to", 2);
                String from = fromAndTo[0].trim();
                String to = fromAndTo[1].trim();

                if (from.isEmpty()) {
                    throw new JustAChillGuyException("Yo, when does your event start? (/from ...)");
                }
                if (to.isEmpty()) {
                    throw new JustAChillGuyException("Yo, when does your event end? (/to ...)");
                }

                taskList.addTask(new Event(eventName, from, to));
                break;

            case DELETE:
                try {
                    int index = Integer.parseInt(argsText);
                    taskList.deleteTask(index);
                } catch (NumberFormatException e) {
                    throw new JustAChillGuyException("Yo, your index isn't valid!");
                }
                break;

            case UNKNOWN:
                throw new JustAChillGuyException("Oops, I don't really understand that. Try something else maybe?");
        }

        return true;
    }
}
