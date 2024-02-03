import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Duke {

    static FileHandler fh = new FileHandler();

    public static void main(String[] args) throws IOException, DukeException {
        Scanner scanner = new Scanner(System.in);
        List<Task> todo = fh.readFile();

        String divider = "---------------------------------------------------------------";
        String chat_name = "Dwight Schrute";
        System.out.printf("%s\nHello! I'm %s\nWhat Can I do for you?\n%s\n"
                , divider, chat_name, divider);
        String input, task_name, start, end, first_string, second_string, third_string; // for easy processing
        Task task;
        int idx;
        // User input for task handling
        while (scanner.hasNext()) {
            try {
                input = scanner.nextLine();
                System.out.printf("\t%s\n", divider);
                switch (input.split(" ")[0]) {
                    case ("bye"):
                        System.out.println("\tBye. Hope to see you again soon!");
                        scanner.close();
                        return;
                    case ("list"):
                        for (int i = 0; i < todo.size(); i++) {
                            System.out.printf("\t%d. %s\n", i + 1, todo.get(i));
                        }
                        break;
                    case ("mark"):
                        idx = Integer.parseInt(input.split(" ")[1]);
                        todo.get(idx - 1).mark();
                        System.out.println("\tNice! I've marked this task as done:");
                        System.out.printf("\t\t%s\n", todo.get(idx - 1));

                        break;
                    case ("unmark"):
                        idx = Integer.parseInt(input.split(" ")[1]);
                        todo.get(idx - 1).unmark();
                        System.out.println("\tOK, I've marked this task as not done yet:");
                        System.out.printf("\t\t%s\n", todo.get(idx - 1));

                        break;
                    case ("todo"):
                        task_name = String.join(" ", Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length));
                        try {
                            // calling the method
                            task = new TodoTask(task_name, input);
                            todo.add(task);
                            System.out.println("\tGot it. I've added this task:");
                            System.out.printf("\t\t%s\n", task);
                            System.out.printf("\tNow you have %d tasks in the list.\n", todo.size());

                            break;
                        } catch (DukeException err) {
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
                        try {
                            task = new DeadlineTask(task_name, end_time, input);
                            todo.add(task);
                            System.out.println("\tGot it. I've added this task:");
                            System.out.printf("\t\t%s\n", task);
                            System.out.printf("\tNow you have %d tasks in the list.\n", todo.size());

                            break;
                        } catch (DukeException err) {
                            System.out.println(err.getMessage());
                            break;
                        }
                    case ("event"):
                        first_string = input.split(" /")[0];
                        second_string = input.split(" /")[1];
                        third_string = input.split(" /")[2];
                        task_name = String.join(" ", Arrays.copyOfRange(first_string.split(" "), 1, first_string.split(" ").length));
                        start = String.join(" ", Arrays.copyOfRange(second_string.split(" "), 1, second_string.split(" ").length));
                        end = String.join(" ", Arrays.copyOfRange(third_string.split(" "), 1, third_string.split(" ").length));
                        try {
                            task = new EventTask(task_name, start, end, input);
                            todo.add(task);
                            System.out.println("\tGot it. I've added this task:");
                            System.out.printf("\t\t%s\n", task);
                            System.out.printf("\tNow you have %d tasks in the list.\n", todo.size());

                            break;
                        } catch (DukeException err) {
                            System.out.println(err.getMessage());
                            break;
                        }

                    case("delete"):
                        idx = Integer.parseInt(input.split(" ")[1]);
                        System.out.println("\tNoted. I've removed this task:");
                        System.out.printf("\t\t%s\n", todo.remove(idx - 1));
                        System.out.printf("\tNow you have %d tasks in the list.\n", todo.size());

                        break;

                    default:
                        throw new DukeException("\tSorry, I did not understand the command!");

                }
                System.out.printf("\t%s\n", divider);
                fh.writeFile(todo);
            } catch (DukeException err) {
                System.out.println(err.getMessage());
                System.out.printf("\t%s\n", divider);
            }
        }

    }
}
