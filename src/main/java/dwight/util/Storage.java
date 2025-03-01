package dwight.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import dwight.task.Task;
import dwight.task.TodoTask;
import dwight.task.DeadlineTask;
import dwight.task.EventTask;
import dwight.exceptions.DwightException;

public class Storage {
    private File FILE;

    public Storage(String filePath) {
        try {
            String dirPath = "./data/";
            File dir = new File(dirPath);

            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    System.out.println("Failed to create directory!");
                    return;
                }

            }

            this.FILE = new File("./data/duke.txt");
            this.FILE.createNewFile();
        } catch (IOException e){
            System.out.println("An error occurred.");
        }

    }


    public List<Task> readFile() throws IOException {

        String input, task_name, start, end, first_string, second_string, third_string; // for easy processing
        Task task;
        List<Task> todo = new ArrayList<>();
        try {
            System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
            Scanner scanner = new Scanner(this.FILE);

            while (scanner.hasNext()) {
                input = scanner.nextLine();
                switch (input.split(" ")[0]) {

                    case ("todo"):
                        task_name = String.join(" ", Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length));
                        try {
                            // calling the method
                            task = new TodoTask(task_name, input);
                            todo.add(task);
                            break;
                        } catch (DwightException err) {
                            System.out.println(err.getMessage());
                            break;
                        }

                    case ("deadline"):
                        first_string = input.split(" /")[0];
                        second_string = input.split(" /")[1];
                        task_name = String.join(" ", Arrays.copyOfRange(first_string.split(" "), 1, first_string.split(" ").length));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                        end = String.join(" ", Arrays.copyOfRange(second_string.split("by "), 1, second_string.split("by ").length));
                        LocalDateTime end_time = LocalDateTime.parse(end, formatter);
                        task = new DeadlineTask(task_name, end_time, input);
                        todo.add(task);
                        break;

                    case ("event"):
                        first_string = input.split(" /")[0];
                        second_string = input.split(" /")[1];
                        third_string = input.split(" /")[2];
                        task_name = String.join(" ", Arrays.copyOfRange(first_string.split(" "), 1, first_string.split(" ").length));
                        start = String.join(" ", Arrays.copyOfRange(second_string.split(" "), 1, second_string.split(" ").length));
                        end = String.join(" ", Arrays.copyOfRange(third_string.split(" "), 1, third_string.split(" ").length));
                        task = new EventTask(task_name, start, end, input);
                        todo.add(task);
                        break;
                }

            }
            return todo;
        } catch (IOException err) {
            throw new IOException(err.getMessage());
        }
    }
    public void writeFile(List<Task> todo) throws IOException {
        try {
            FileWriter fw = new FileWriter(FILE);
            for (Task task : todo) {
                fw.write(task.getFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException err) {
            System.out.println("File not found");
        }
    }
}
