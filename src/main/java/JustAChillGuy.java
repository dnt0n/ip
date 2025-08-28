import java.util.Scanner;

public class JustAChillGuy {
    private static final String NAME = "Just A Chill Guy";
    private static final String GREETING = "Yo! I am ✨" + NAME + "✨ :)\nHow can I help ya? \n(Enter the command help to see the full list of actions)";
    private static final String HELLO = "Hey, how is it going?";
    private static final String GOODBYE = "Goodbye mate! See ya next time!";
    private static final String HELP =
            "Here’s what I can do for ya:\n"
                    + "\n"
                    + "👉 Basic commands:\n"
                    + "   hello                - I’ll greet you back\n"
                    + "   bye                  - Exit the program\n"
                    + "   list                 - Show all your tasks\n"
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
                    + "👉 Notes:\n"
                    + "   - Index numbers start at 1 (as shown in list).\n"
                    + "   - Tasks are auto-saved after every change.\n"
                    + "   - Invalid input gives a friendly error instead of breaking the bot.\n"
                    + "\n"
                    + "✨ Chill and let me handle your tasks for ya! ✨";


    private static final String FILE_PATH = "./data/justachillguy.txt";

    public static void main(String[] args) {
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
                if (input.isEmpty()) {
                    throw new JustAChillGuyException("Input cannot be empty");
                }

                String[] parts = input.split(" ", 2); // limit the output array length to maximum 2
                String commandWord = parts[0];
                String argsText = (parts.length > 1) ? parts[1].trim() : ""; // handle the case where input is just one word

                Command command = Command.from(commandWord);
                isRunning = handleCommand(command, argsText, taskList); // handleCommand will return false if the command is bye, true otherwise

            } catch (JustAChillGuyException e) {
                UI.display(e.getMessage());
            }
        }
    }

    public static boolean handleCommand(Command command, String argsText, TaskList taskList) throws JustAChillGuyException {
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
