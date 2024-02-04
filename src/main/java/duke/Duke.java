package duke;

import duke.exceptions.DukeException;
import duke.task.Task;
import duke.util.Parser;
import duke.util.Storage;
import duke.task.TaskList;
import duke.util.Ui;

import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.io.IOException;

public class Duke {

    private Storage storage;
    private TaskList todo;
    private Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            todo = new TaskList(storage.readFile());
        } catch (IOException e) {
            ui.showLoadingError();
            todo = new TaskList();
        }
    }


    public void run() {
        String divider = "---------------------------------------------------------------";
        Scanner scanner = new Scanner(System.in);
        String input;
        String command;
        Task task;
        int idx;
        ui.welcome();
        ui.showCommands();
        ui.divider();
        while (scanner.hasNext()) {
            try {
                input = scanner.nextLine();
                System.out.printf("\t%s\n", divider);
                command = Parser.parse(input);
                switch (command) {
                    case ("bye"):
                        System.out.println("\tBye. Hope to see you again soon!");
                        scanner.close();
                        return;
                    case ("list"):
                        this.todo.list();
                        break;
                    case("mark"):
                        try {
                            idx = Parser.parse_mark(input);
                            task = todo.mark(idx);
                            System.out.println("\tNice! I've marked this task as done:");
                            System.out.printf("\t\t%s\n", task);
                            break;
                        } catch (DukeException err) {
                            System.out.println(err.getMessage());
                            break;
                        }
                    case("unmark"):
                        try {
                            idx = Parser.parse_unmark(input);
                            task = todo.unmark(idx);
                            System.out.println("\tI've unmarked this task as done:");
                            System.out.printf("\t\t%s\n", task);
                            break;
                        } catch (DukeException err) {
                            System.out.println(err.getMessage());
                            break;
                        }
                    case ("todo"):

                        try {
                            // calling the method
                            task = Parser.parse_todo(input);
                            todo.addTask(task);
                            System.out.println("\tGot it. I've added this task:");
                            System.out.printf("\t\t%s\n", task);
                            System.out.printf("\tNow you have %d tasks in the list.\n", todo.size());

                            break;
                        } catch (DukeException err) {
                            System.out.println(err.getMessage());
                            break;
                        }

                    case ("deadline"):

                        try {
                            task = Parser.parse_deadline(input);
                            todo.addTask(task);
                            System.out.println("\tGot it. I've added this task:");
                            System.out.printf("\t\t%s\n", task);
                            System.out.printf("\tNow you have %d tasks in the list.\n", todo.size());

                            break;
                        } catch (DukeException err) {
                            System.out.println(err.getMessage());
                            break;
                        } catch (DateTimeParseException err) {
                            System.out.println("\tPlease write your data in d/m/yyyy T format");
                            break;
                        }
                    case ("event"):

                        try {
                            task = Parser.parse_event(input);
                            todo.addTask(task);
                            System.out.println("\tGot it. I've added this task:");
                            System.out.printf("\t\t%s\n", task);
                            System.out.printf("\tNow you have %d tasks in the list.\n", todo.size());

                            break;
                        } catch (DukeException err) {
                            System.out.println(err.getMessage());
                            break;
                        }

                    case("delete"):
                        try {
                            idx = Parser.parse_delete(input);
                            System.out.println("\tNoted. I've removed this task:");
                            System.out.printf("\t\t%s\n", todo.deleteTask(idx));
                            System.out.printf("\tNow you have %d tasks in the list.\n", todo.size());
                            break;
                        } catch (DukeException err) {
                            System.out.println(err.getMessage());
                            break;
                        }

                    default:
                        throw new DukeException("\tSorry, I did not understand the command!");

                }
                System.out.printf("\t%s\n", divider);
                storage.writeFile(todo.getList());
            } catch (DukeException err) {
                System.out.println(err.getMessage());
                System.out.printf("\t%s\n", divider);
            } catch (IOException err) {
                System.out.println(err.getMessage());
                System.out.printf("\t%s\n", divider);
            }
        }
    }

    public static void main(String[] args) throws IOException, DukeException {
        new Duke("./data/duke.txt").run();
    }
}
