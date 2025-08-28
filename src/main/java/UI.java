public class UI {

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

    public static void display(String message) {
        System.out.println(wrapInLines(message));
    }
}
