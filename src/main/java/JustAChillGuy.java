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
            } else {
                taskList.addTask(new Task(input));
            }
        }

        sc.close();
    }
}
