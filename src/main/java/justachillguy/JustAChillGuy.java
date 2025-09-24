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
            "Yo! I am " + NAME + " :)\nHow can I help ya? \n(Enter the command \"help\" to see the full list of actions)";

    /** Standard reply for the {@code hello} command. */
    private static final String HELLO = "Hey, how is it going?";

    /** Goodbye message displayed when the program exits. */
    private static final String GOODBYE = "Goodbye mate! See ya next time!";

    /** Help menu that lists all supported commands. */
    private static final String HELP =
            "Here is what I can do for ya:\n"
                    + "\n"
                    + "** Basic commands:\n"
                    + "   greet                - I will introduce myself\n"
                    + "   hello                - I will greet you back\n"
                    + "   bye                  - Exit the program\n"
                    + "   list                 - Show all your tasks\n"
                    + "   help                 - Show this help menu\n"
                    + "\n"
                    + "** Task management:\n"
                    + "   todo <task name>     - Add a ToDo task\n"
                    + "   deadline <name> /by <date/time>\n"
                    + "                        - Add a Deadline task (must include /by)\n"
                    + "   event <name> /from <start> /to <end>\n"
                    + "                        - Add an Event task (must include both /from and /to)\n"
                    + "\n"
                    + "** Date & Time formats:\n"
                    + "   - Use yyyy-MM-dd for dates (e.g., 2025-09-04)\n"
                    + "   - Use HHmm for 24-hour time (e.g., 1830)\n"
                    + "   - Combine them like yyyy-MM-dd HHmm (e.g., 2025-09-04 1400)\n"
                    + "\n"
                    + "** Task updates:\n"
                    + "   mark <index>         - Mark the task at position <index> as done\n"
                    + "   unmark <index>       - Mark the task at position <index> as not done\n"
                    + "   delete <index>       - Remove the task at position <index>\n"
                    + "   tag <index> <tag>    - Assign a tag to the task at position <index>\n"
                    + "   untag <index>        - Remove the tag from the task at position <index>\n"
                    + "\n"
                    + "** Search:\n"
                    + "   find <keyword>       - Find all tasks containing <keyword>\n"
                    + "\n"
                    + "** Notes:\n"
                    + "   - Index numbers start at 1 (as shown in list).\n"
                    + "   - Tasks are auto-saved after every change.\n"
                    + "\n"
                    + " Chill and let me handle your tasks for ya! ";

    /** Path to the file where tasks are stored persistently. */
    private static final String FILE_PATH = "./data/justachillguy.txt";

    private static final String ERR_INVALID_INDEX = "Yo, your index isn't valid!";
    private static final String ERR_UNKNOWN = "Oops, I don't really understand that. Try something else maybe? "
            + "\n(Enter \"help\" to see the full list of commands)";

    private TaskList taskList;

    /**
     * Default constructor for the chatbot.
     * <p>
     * Initializes persistent storage at {@link #FILE_PATH}, attempts to
     * load an existing {@link TaskList}, and falls back to an empty
     * list if loading fails.
     * </p>
     */
    public JustAChillGuy() {
        Storage storage = new Storage(FILE_PATH);

        try {
            taskList = new TaskList(storage);
        } catch (JustAChillGuyException e) {
            UI.display(e.getMessage());
            taskList = new TaskList();
        }
    }

    /**
     * Entry point for the chatbot program.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        run();
    }

    /**
     * Generates a response for the user's chat message
     */
    public String getResponse(String input) {
        return "Just A Chill Guy said: \n" + handleInput(input);
    }

    /**
     * Runs the chatbot. Initializes storage and task list,
     * displays greeting, and continuously accepts user input
     * until the {@code bye} command is entered.
     */
    public static void run() {
        boolean isRunning = true;
        TaskList taskList = initializeTaskList();

        Scanner sc = new Scanner(System.in);

        UI.display(GREETING);

        while (isRunning) {
            String input = sc.nextLine().trim(); // to trim leading and trailing spaces
            isRunning = processInput(input, taskList);
        }
    }

    /**
     * Processes a single line of user input in CLI mode.
     * Parses the input into a command and arguments, executes it,
     * and displays the corresponding output.
     *
     * @param input    the raw user input string
     * @param taskList the current list of tasks
     * @return {@code true} if the chatbot should continue running,
     *         {@code false} if the {@code bye} command was entered
     */
    public static boolean processInput(String input, TaskList taskList) {
        try {
            Object[] parsed = Parser.parseInputIntoCommandAndArgs(input);
            Command command = (Command) parsed[0];
            String argsText = (String) parsed[1];
            assert command != null : "Command must not be null";
            assert argsText != null : "Args text must not be null";
            UI.display(executeCommand(command, argsText, taskList));
            return command != Command.BYE;
        } catch (JustAChillGuyException e) {
            UI.display(e.getMessage());
            return true;
        }
    }

    /**
     * Initializes the task list from persistent storage.
     * If storage cannot be loaded, creates a new empty task list instead.
     *
     * @return the initialized {@link TaskList}
     */
    private static TaskList initializeTaskList() {
        Storage storage = new Storage(FILE_PATH);
        TaskList taskList = null;

        try {
            taskList = new TaskList(storage);
        } catch (JustAChillGuyException e) {
            UI.display(e.getMessage());
            taskList = new TaskList();
        }
        return taskList;
    }

    /**
     * Parses a raw user input string, executes the corresponding command
     * on the chatbot's task list, and returns the response message.
     *
     * @param input the raw user input text entered by the user
     * @return the chatbot's response message, or an error description
     *         if the command could not be processed
     */
    public String handleInput(String input) {
        String output = "";

        try {
            Object[] parsed = Parser.parseInputIntoCommandAndArgs(input);
            Command command = (Command) parsed[0];
            String argsText = (String) parsed[1];
            assert command != null : "Command must not be null";
            assert argsText != null : "Args text must not be null";
            output = executeCommand(command, argsText, this.taskList);
        } catch (JustAChillGuyException e) {
            output = e.getMessage();
        }

        return output;
    }

    /**
     * Handles a single user command in GUI mode and returns the chatbot's response.
     *
     * @param command  the {@link Command} parsed from user input
     * @param argsText the arguments provided with the command, may be empty
     * @param taskList the current list of tasks managed by the chatbot
     * @return the response message as a string, intended for display in the UI
     * @throws JustAChillGuyException if the command is not recognized or
     *                                if its arguments are invalid
     */
    public static String executeCommand(Command command, String argsText, TaskList taskList)
            throws JustAChillGuyException {
        switch (command) {
        case GREET:
            return GREETING;

        case BYE:
            return GOODBYE;

        case HELLO:
            return HELLO;

        case HELP:
            return HELP;

        case LIST:
            return taskList.toString();

        case FIND:
            return handleFindCommand(argsText, taskList);

        case MARK:
            return handleMarkCommand(argsText, taskList);

        case UNMARK:
            return handleUnmarkCommand(argsText, taskList);

        case TODO:
            return handleTodoCommand(argsText, taskList);

        case DEADLINE:
            return handleDeadlineCommand(argsText, taskList);

        case EVENT:
            return handleEventCommand(argsText, taskList);

        case DELETE:
            return handleDeleteCommand(argsText, taskList);

        case TAG:
            return handleTagCommand(argsText, taskList);

        case UNTAG:
            return handleUntagCommand(argsText, taskList);

        default:
            throw new JustAChillGuyException(ERR_UNKNOWN);
        }
    }

    /**
     * Handles the {@code delete} command by removing a task from the list.
     *
     * @param argsText the argument containing the task index to delete
     * @param taskList the current list of tasks
     * @return a message confirming the task deletion
     * @throws JustAChillGuyException if the index is invalid
     */
    private static String handleDeleteCommand(String argsText, TaskList taskList) throws JustAChillGuyException {
        try {
            int index = Integer.parseInt(argsText);
            return taskList.deleteTask(index);
        } catch (NumberFormatException e) {
            throw new JustAChillGuyException(ERR_INVALID_INDEX);
        }
    }

    /**
     * Handles the {@code event} command by creating a new event task.
     * <p>
     * The argument text must contain the event name, a start time specified
     * with {@code /from}, and an end time specified with {@code /to}.
     * </p>
     *
     * @param argsText the string containing the event details
     * @param taskList the task list to modify
     * @return the confirmation message after adding the event
     * @throws JustAChillGuyException if the event name or duration is missing or invalid
     */
    private static String handleEventCommand(String argsText, TaskList taskList) throws JustAChillGuyException {
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

        return taskList.addTask(new Event(eventName, from, to));
    }

    /**
     * Handles the {@code deadline} command by creating a new deadline task.
     * <p>
     * The argument text must contain the task name and a deadline specified
     * with {@code /by}.
     * </p>
     *
     * @param argsText the string containing the deadline details
     * @param taskList the task list to modify
     * @return the confirmation message after adding the deadline
     * @throws JustAChillGuyException if the task name or deadline is missing or invalid
     */
    private static String handleDeadlineCommand(String argsText, TaskList taskList) throws JustAChillGuyException {
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

        return taskList.addTask(new Deadline(deadlineName, by));
    }

    /**
     * Handles the {@code todo} command by creating a new to-do task.
     *
     * @param argsText the string containing the task description
     * @param taskList the task list to modify
     * @return the confirmation message after adding the to-do task
     * @throws JustAChillGuyException if the task description is missing
     */
    private static String handleTodoCommand(String argsText, TaskList taskList) throws JustAChillGuyException {
        if (argsText.isEmpty()) {
            throw new JustAChillGuyException("Yo, what todo task do you want me to add to the list?");
        }
        return taskList.addTask(new ToDo(argsText));
    }

    /**
     * Handles the {@code unmark} command by marking a task as not done.
     *
     * @param argsText the string containing the task index to unmark
     * @param taskList the task list to modify
     * @return the confirmation message after unmarking the task
     * @throws JustAChillGuyException if the index is not a valid integer
     */
    private static String handleUnmarkCommand(String argsText, TaskList taskList) throws JustAChillGuyException {
        try {
            int index = Integer.parseInt(argsText);
            return taskList.unmarkTask(index);
        } catch (NumberFormatException e) {
            throw new JustAChillGuyException(ERR_INVALID_INDEX);
        }
    }

    /**
     * Handles the {@code mark} command by marking a task as done.
     *
     * @param argsText the string containing the task index to mark
     * @param taskList the task list to modify
     * @return the confirmation message after marking the task
     * @throws JustAChillGuyException if the index is not a valid integer
     */
    private static String handleMarkCommand(String argsText, TaskList taskList) throws JustAChillGuyException {
        try {
            int index = Integer.parseInt(argsText);
            return taskList.markTask(index);
        } catch (NumberFormatException e) {
            throw new JustAChillGuyException(ERR_INVALID_INDEX);
        }
    }

    /**
     * Handles the {@code find} command by searching tasks that match
     * a given keyword.
     *
     * @param argsText the search keyword
     * @param taskList the task list to search
     * @return a message containing the matching tasks, or an appropriate
     *         message if no tasks match or the keyword is empty
     */
    private static String handleFindCommand(String argsText, TaskList taskList) {
        if (argsText.isEmpty()) {
            return "What do you want me to find for ya?";
        }
        if (taskList.isEmpty()) {
            return "Oops, there is nothing to find because the list is empty.";
        }
        String outputList = taskList.findTasksBasedOnKeyword(argsText);
        if (outputList.isEmpty()) {
            return "Oops, I can't find any matching tasks :(";
        } else {
            return "Sure! I've found these matching tasks for yea!\n" + outputList;
        }
    }

    /**
     * Handles the {@code untag} command by removing a tag from a task.
     *
     * @param argsText the string containing the task index to untag
     * @param taskList the current list of tasks
     * @return a confirmation message after removing the tag
     * @throws JustAChillGuyException if the index is invalid
     */
    private static String handleUntagCommand(String argsText, TaskList taskList) throws JustAChillGuyException {
        try {
            int index = Integer.parseInt(argsText);
            return taskList.untagTask(index);
        } catch (NumberFormatException e) {
            throw new JustAChillGuyException(ERR_INVALID_INDEX);
        }
    }

    /**
     * Handles the {@code tag} command by adding a tag to a task.
     *
     * @param argsText the string containing the task index and tag value
     * @param taskList the current list of tasks
     * @return a confirmation message after tagging the task
     * @throws JustAChillGuyException if the index or tag is missing or invalid
     */
    private static String handleTagCommand(String argsText, TaskList taskList) throws JustAChillGuyException {
        if (argsText.isEmpty()) {
            throw new JustAChillGuyException("Yo, you need to specify an index!");
        }

        String[] indexAndTag = argsText.split(" ", 2);

        if (indexAndTag.length < 2) {
            throw new JustAChillGuyException("Yo, you need to specify a tag!");
        }

        try {
            int index = Integer.parseInt(indexAndTag[0]);
            String tag = indexAndTag[1];
            return taskList.tagTask(index, tag);
        } catch (NumberFormatException e) {
            throw new JustAChillGuyException(ERR_INVALID_INDEX);
        }
    }
}
