import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> loadTasks() throws JustAChillGuyException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new JustAChillGuyException("I can't create the file :(");
            }
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                Task task = TaskFactory.parseTask(line);
                tasks.add(task);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            throw new JustAChillGuyException("Error reading file :(");
        }

        return tasks;
    }

    public void saveTasks(ArrayList<Task> tasks) throws JustAChillGuyException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)); // rewrites the file
            for (Task task : tasks) {
                bw.write(task.saveFormat());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new JustAChillGuyException("I am having trouble saving to the file :(");
        }
    }
}
