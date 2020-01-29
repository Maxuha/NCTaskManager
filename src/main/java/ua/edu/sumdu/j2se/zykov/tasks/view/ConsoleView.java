package ua.edu.sumdu.j2se.zykov.tasks.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.zykov.tasks.exceptions.NCTaskManagerNumberFormatException;
import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.Task;
import ua.edu.sumdu.j2se.zykov.tasks.model.Tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.SortedMap;

public class ConsoleView implements View {
    /**
     * reader - reading source
     */
    private BufferedReader reader;
    /**
     * log is link on logger this class
     */
    private static final Logger log = LoggerFactory.getLogger(ConsoleView.class);

    /**
     * @return number selected task
     */
    @Override
    public int update() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        int value = -1;
        boolean isTry = true;
        while (isTry) {
            try {
                value = Integer.parseInt(reader.readLine());
                if (value < 0 || value > 5) {
                    throw new NCTaskManagerNumberFormatException("Incorrectly selected number. Enter correctly number: ", value);
                }
                isTry = false;
            } catch (IOException | NCTaskManagerNumberFormatException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return value;
    }

    /**
     * @return new Task to add to taskList
     * @throws IOException to the controller
     */
    @Override
    public Task addTask() throws IOException {
        Task task;
        System.out.println("Enter name task (up to 20 characters) (0 - cancel): ");
        String title = reader.readLine();
        if ("0".equals(title)) {
            return null;
        }
        System.out.println("Enter repeat (true/false) ");
        boolean repeat = Boolean.parseBoolean(reader.readLine());
        if (!repeat) {
            System.out.println("Enter day and time task (format: dd-MM-yyyy HH:mm:ss) (0 - cancel): ");
            LocalDateTime time = inputDate();
            if (time == null) {
                return null;
            }
            task = new Task(System.currentTimeMillis(), title, time);
        } else {
            System.out.println("Enter start date and time(format: dd-MM-yyyy HH:mm:ss) (0 - cancel): ");
            LocalDateTime startTime = inputDate();
            if (startTime == null) {
                return null;
            }
            System.out.println("Enter end date and time(format: dd-MM-yyyy HH:mm:ss) (0 - cancel): ");
            LocalDateTime endTime = inputDate();
            if (endTime == null) {
                return null;
            }
            System.out.println("Enter repeat interval(in seconds): ");
            int interval = Integer.parseInt(reader.readLine());
            task = new Task(System.currentTimeMillis(), title, startTime, endTime, interval);
        }
        System.out.println("Task " + task.getTitle() + " added");
        return task;
    }

    /**
     * @param taskList for remove task
     * @throws IOException to the controller
     */
    @Override
    public void removeTask(AbstractTaskList taskList) throws IOException {
        System.out.println("Enter id task to delete (0 - cancel): ");
        long id = getIDTask();
        if (id == 0) {
            return;
        }
        for (Task task : taskList) {
            if (task.getId() == id) {
                taskList.remove(task);
                System.out.println("Task " + task.getTitle() + " with id " + id + " remove");
                log.info("Task " + task.getTitle() + " with id " + id + " remove");
                return;
            }
        }
        System.out.println("Task with id " + id + " not found.");
        log.info("Task with id " + id + " not found.");
    }

    /**
     * @param taskList for change task
     * @throws IOException to the controller
     */
    @Override
    public void changeTask(AbstractTaskList taskList) throws IOException {
        System.out.println("Enter id task to change (0 - cancel): ");
        long id = getIDTask();

        if (id == 0) {
            return;
        }
        for (Task task : taskList) {
            if (task.getId() == id) {
                System.out.println(task);
                System.out.println("Choose what you want to do with task: ");
                System.out.println("1. Rename");
                System.out.println("2. Active/Inactive");
                System.out.println("3. Repeat/No repeat");
                System.out.println("4. Change time");
                System.out.println("5. Change start time");
                System.out.println("6. Change end time");
                System.out.println("7. Change repeat interval");
                System.out.println("0. Cancel");
                LocalDateTime dateTime;
                String result = "";
                ChangeType changeType = ChangeType.EMPTY;
                while (changeType.equals(ChangeType.EMPTY)) {
                    int value = Integer.parseInt(reader.readLine());
                    switch (value) {
                        case 0:
                            return;
                        case 1:
                            System.out.println("Enter new name (0 - cancel): ");
                            result = reader.readLine();
                            if ("0".equals(result)) {
                                return;
                            }
                            task.setTitle(result);
                            changeType = ChangeType.RENAME;
                            break;
                        case 2:
                            System.out.println("Enter active: true and false (0 - cancel): ");
                            result = reader.readLine();
                            if ("0".equals(result)) {
                                return;
                            }
                            task.setActive(Boolean.parseBoolean(result));
                            changeType = ChangeType.ACTIVE;
                            break;
                        case 3:
                            System.out.println("Enter repeat: true and false (0 - cancel): ");
                            result = reader.readLine();
                            if ("0".equals(result)) {
                                return;
                            }
                            task.setActive(Boolean.parseBoolean(result));
                            changeType = ChangeType.REPEAT;
                            break;
                        case 4:
                            System.out.println("Enter date and time (format: dd-MM-yyyy:HH-mm-ss) (0 - cancel): ");
                            result = reader.readLine();
                            dateTime = inputDate();
                            if (dateTime == null) {
                                return;
                            }
                            task.setTime(dateTime);
                            changeType = ChangeType.TIME;
                            break;
                        case 5:
                            System.out.println("Enter start date and time (format: dd-MM-yyyy:HH-mm-ss) (0 - cancel): ");
                            result = reader.readLine();
                            dateTime = inputDate();
                            if (dateTime == null) {
                                return;
                            }
                            task.setTime(dateTime, task.getEndTime(), task.getRepeatInterval());
                            changeType = ChangeType.START_TIME;
                            break;
                        case 6:
                            System.out.println("Enter end date and time (format: dd-MM-yyyy:HH-mm-ss) (0 - cancel): ");
                            dateTime = inputDate();
                            if (dateTime == null) {
                                return;
                            }
                            task.setTime(task.getStartTime(), dateTime, task.getRepeatInterval());
                            changeType = ChangeType.END_TIME;
                            break;
                        case 7:
                            System.out.println("Enter repeat interval: ");
                            result = reader.readLine();
                            task.setTime(task.getStartTime(), task.getEndTime(), Integer.parseInt(result));
                            changeType = ChangeType.REPEAT_INTERVAL;
                            break;
                        default:
                            System.out.println("Error. Enter correctly number: ");
                    }
                }
                System.out.println("Task with id " + id + " changed " + changeType + " on " + result);
                log.info("Task with id " + id + " changed " + changeType + " on " + result);
                return;
            }
        }
        System.out.println("Task with id " + id + " not found.");
        log.info("Task with id " + id + " not found.");
    }

    /**
     * @param taskList for show all tasks
     */
    @Override
    public void showTasks(AbstractTaskList taskList) {
        System.out.println(taskList);
        log.info("Show all tasks.");
        System.out.println("Input any button to main menu...");
        try {
            reader.readLine();
        } catch (IOException e) {
            System.out.println("Error IO");
            log.error("Error IO: " + e.getMessage());
        }
    }

    /**
     * show main menu
     */
    @Override
    public void mainMenu() {
        System.out.println("1. Show tasks");
        System.out.println("2. Add task");
        System.out.println("3. Remove task");
        System.out.println("4. Change task");
        System.out.println("5. Calendar");
        System.out.println("0. Exit");
    }

    /**
     * @param taskList for show calendar
     * @throws IOException to the controller
     */
    @Override
    public void calendar(AbstractTaskList taskList) throws IOException {
        System.out.println("Enter start date (format: dd-MM-yyyy HH:mm:ss) (0 - cancel): ");
        LocalDateTime start = inputDate();
        if (start == null) {
            return;
        }
        System.out.println("Enter end date (format: dd-MM-yyyy HH:mm:ss) (0 - cancel): ");
        LocalDateTime end = inputDate();
        if (end == null) {
            return;
        }
        SortedMap<LocalDateTime, Set<Task>> sortedMap = Tasks.calendar(taskList, start, end);
        if (sortedMap.entrySet().size() == 0) {
            System.out.println("No one found task.");
            return;
        }
        System.out.println("Date                 | Task         ");
        Object[] tasks;
        for (SortedMap.Entry<LocalDateTime, Set<Task>> entry : sortedMap.entrySet()) {
            tasks = entry.getValue().toArray();
            System.out.print(entry.getKey().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss")) + " | ");
            for (int i = 0; i < tasks.length; i++) {
                if (i + 1 == tasks.length) {
                    System.out.print(((Task) tasks[i]).getTitle() + ".");
                } else {
                    System.out.print(((Task) tasks[i]).getTitle() + ", ");
                }
            }
            System.out.println();
        }
        System.out.println("Input any button to main menu...");
        reader.readLine();
    }

    private long getIDTask() {
        long id = -1;
        while (id == -1) {
            try {
                id = Long.parseLong(reader.readLine());
            } catch (NumberFormatException e) {
                log.error("Incorrectly enter format.");
                System.out.println("Incorrectly enter format. Enter the whole number (0 - cancel): ");
            } catch (IOException e) {
                System.out.println("Error IO");
                log.error("Error IO: " + e.getMessage());
            }
        }
        return id;
    }

    private LocalDateTime inputDate() {
        while (true) {
            try {
                String date = reader.readLine();
                if ("0".equals(date)) {
                    return null;
                }
                return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            } catch (DateTimeParseException e) {
                System.out.println("Incorrectly format date. Enter correctly date (format: dd-MM-yyyy HH:mm:ss): ");
                log.info("Incorrectly format date.");
            } catch (IOException e) {
                System.out.println("Error IO");
                log.error("Error IO: " + e.getMessage());
            }
        }
    }

    /**
     * @param message to be show to user
     */
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
