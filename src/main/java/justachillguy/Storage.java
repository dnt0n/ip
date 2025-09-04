package justachillguy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles saving and loading of tasks from a file.
 */
public class Storage {
    private String filePath;

    /**
     * Creates a new {@code Storage} object.
     *
     * @param filePath path to the save file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file into memory.
     * If the file does not exist, it will be created.
     *
     * @return a list of tasks read from the file
     * @throws JustAChillGuyException if there are errors reading or creating the file
     */
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

    /**
     * Saves the given list of tasks to the save file.
     * Overwrites the file if it already exists.
     *
     * @param tasks the list of tasks to save
     * @throws JustAChillGuyException if there are errors writing to the file
     */
    public void saveTasks(ArrayList<Task> tasks) throws JustAChillGuyException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)); // rewrites the file
            for (Task task : tasks) {
                bw.write(task.getSaveFormat());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new JustAChillGuyException("I am having trouble saving to the file :(");
        }
    }
}
