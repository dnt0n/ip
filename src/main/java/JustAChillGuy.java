import java.util.Arrays;
import java.util.Scanner;

public class JustAChillGuy {
    public static String wrapInLines(String str) {
        String[] lines = str.split("\n");
        StringBuilder sb = new StringBuilder();
        sb.append("____________________________________________________________\n");
        for (String line : lines) {
            sb.append("  ").append(line).append("\n");
        }
        sb.append("____________________________________________________________\n");
        return sb.toString();
    }

    // added this comment to push a new tag

    public static void main(String[] args) {
        String name = "Just A Chill Guy";
        String greetingMessage = "Yo! I am ✨" + name + "✨ :) \n" + "How can I help ya?";
        String goodbyeMessage = "Goodbye mate! See ya next time!";

        boolean isRunning = true;
        TaskList taskList = new TaskList();
        Scanner sc = new Scanner(System.in);

        System.out.println(wrapInLines(greetingMessage));

        while (isRunning) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                isRunning = false;
                System.out.println(wrapInLines(goodbyeMessage));

            } else if (input.equals("list")) {
                System.out.println(taskList);

            } else if (input.startsWith("mark")) {
                int index = Integer.parseInt(input.split(" ")[1]);
                taskList.markTask(index);
            } else if (input.startsWith("unmark")) {
                int index = Integer.parseInt(input.split(" ")[1]);
                taskList.unmarkTask(index);

            } else if (input.startsWith("todo ")) {
                String taskName = input.substring(5);
                taskList.addTask(new ToDo(taskName));
            } else if (input.startsWith("deadline ")) {
                String toParse = input.substring(9);
                String[] nameAndBy = toParse.split("/by ");
                String taskName = nameAndBy[0];
                String by = nameAndBy[1];
                taskList.addTask(new Deadline(taskName, by));
            } else if (input.startsWith("event ")) {
                String toParse = input.substring(6);
                String toParse2 = toParse.replace(" /from ", "%%%").replace(" /to ", "%%%");
                String[] nameAndFromAndTo = toParse2.split("%%%");
                String taskName = nameAndFromAndTo[0];
                String from = nameAndFromAndTo[1];
                String to = nameAndFromAndTo[2];
                taskList.addTask(new Event(taskName, from, to));
            }
        }

        sc.close();
    }
}
