package justachillguy;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles saving and loading of tasks from a file.
 */
public class Storage {
    private final String filePath;

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

        try (Scanner sc = new Scanner(new FileReader(file))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.isBlank()) {
                    Task task = TaskFactory.parseTask(line);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new JustAChillGuyException("Error reading file :(");
        }

        return tasks;
    }

    /**
     * Saves the given list of tasks to the save file.
     * Overwrites the file if it already exists.
     * <p>
     * Unlike buffered I/O, this method writes to disk immediately
     * after being called.
     * </p>
     *
     * @param tasks the list of tasks to save
     * @throws JustAChillGuyException if there are errors writing to the file
     */
    public void saveTasks(ArrayList<Task> tasks) throws JustAChillGuyException {
        try (FileWriter fw = new FileWriter(filePath, false)) { // overwrite mode
            for (Task task : tasks) {
                fw.write(task.getSaveFormat());
                fw.write(System.lineSeparator());
            }
            fw.flush(); // force immediate flush to disk
        } catch (IOException e) {
            throw new JustAChillGuyException("I am having trouble saving to the file :(");
        }
    }
}
