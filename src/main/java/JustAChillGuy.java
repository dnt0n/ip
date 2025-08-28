import java.util.Scanner;

public class JustAChillGuy {
    private static final String NAME = "Just A Chill Guy";
    private static final String GREETING = "Yo! I am ✨" + NAME + "✨ :)\nHow can I help ya?";
    private static final String HELLO = "Hey, how is it going?";
    private static final String GOODBYE = "Goodbye mate! See ya next time!";

    public static void main(String[] args) {
        boolean isRunning = true;
        TaskList taskList = new TaskList();
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

            case UNKNOWN:
                throw new JustAChillGuyException("Oops, I don't really understand that. Try something else maybe?");

        }

        return true;
    }
}
