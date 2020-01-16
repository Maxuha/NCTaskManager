package ua.edu.sumdu.j2se.zykov.tasks.view;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.Task;
import ua.edu.sumdu.j2se.zykov.tasks.model.Tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;

public class ConsoleView implements View {
    private BufferedReader reader;

    @Override
    public int update() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        int value = -1;
        try {
            value = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public Task addTask() throws IOException {
        Task task;
        System.out.println("Enter name task (up to 20 characters): ");
        String title = reader.readLine();
        System.out.println("Enter repeat (true/false) ");
        boolean repeat = Boolean.parseBoolean(reader.readLine());
        if (!repeat) {
            System.out.println("Enter day and time task (format: dd-MM-yyyy HH:mm:ss): ");
            LocalDateTime time = LocalDateTime.parse(reader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            task = new Task(System.currentTimeMillis(), title, time);
        } else {
            System.out.println("Enter start time(format: dd-MM-yyyy HH:mm:ss): ");
            LocalDateTime startTime = LocalDateTime.parse(reader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            System.out.println("Enter end time(format: dd-MM-yyyy HH:mm:ss): ");
            LocalDateTime endTime = LocalDateTime.parse(reader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            System.out.println("Enter repeat interval(in seconds): ");
            int interval = Integer.parseInt(reader.readLine());
            task = new Task(System.currentTimeMillis(), title, startTime, endTime, interval);
        }
        System.out.println("Task " + task.getTitle() + " added");
        return task;
    }

    @Override
    public void removeTask(AbstractTaskList taskList) throws IOException {
        System.out.println("Enter id task to delete (0 - cancel): ");
        long id = Long.parseLong(reader.readLine());
        if (id == 0) {
            return;
        }
        for (Task task: taskList) {
            if (task.getId() == id) {
                taskList.remove(task);
                System.out.println("Task " + task.getTitle() + " with id " + id + " remove");
                return;
            }
        }
        System.out.println("Task with id " + id + " not found.");
    }

    @Override
    public void changeTask(AbstractTaskList taskList) throws IOException {
        System.out.println("Enter id task to change (0 - cancel): ");
        long id = Long.parseLong(reader.readLine());
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
                int value = Integer.parseInt(reader.readLine());
                String result = "";
                ChangeType changeType = ChangeType.EMPTY;
                switch (value) {
                    case 0:
                        return;
                    case 1:
                        System.out.println("Enter new name... ");
                        result = reader.readLine();
                        task.setTitle(result);
                        changeType = ChangeType.RENAME;
                        break;
                    case 2:
                        System.out.println("Enter active: true and false... ");
                        result = reader.readLine();
                        task.setActive(Boolean.parseBoolean(result));
                        changeType = ChangeType.ACTIVE;
                        break;
                    case 3:
                        System.out.println("Enter repeat: true and false... ");
                        result = reader.readLine();
                        task.setActive(Boolean.parseBoolean(result));
                        changeType = ChangeType.REPEAT;
                        break;
                    case 4:
                        System.out.println("Enter date and time (format: dd-MM-yyyy:HH-mm-ss)... ");
                        result = reader.readLine();
                        task.setTime(LocalDateTime.parse(result, DateTimeFormatter.ofPattern("dd-MM-yyyy:HH-mm-ss")));
                        changeType = ChangeType.TIME;
                        break;
                    case 5:
                        System.out.println("Enter start date and time (format: dd-MM-yyyy:HH-mm-ss)... ");
                        result = reader.readLine();
                        task.setTime(LocalDateTime.parse(result, DateTimeFormatter.ofPattern("dd-MM-yyyy:HH-mm-ss")), task.getEndTime(), task.getRepeatInterval());
                        changeType = ChangeType.START_TIME;
                        break;
                    case 6:
                        System.out.println("Enter end date and time (format: dd-MM-yyyy:HH-mm-ss)... ");
                        result = reader.readLine();
                        task.setTime(task.getStartTime(), LocalDateTime.parse(result, DateTimeFormatter.ofPattern("dd-MM-yyyy:HH-mm-ss")), task.getRepeatInterval());
                        changeType = ChangeType.END_TIME;
                        break;
                    case 7:
                        System.out.println("Enter repeat interval... ");
                        result = reader.readLine();
                        task.setTime(task.getStartTime(), task.getEndTime(), Integer.parseInt(result));
                        changeType = ChangeType.REPEAT_INTERVAL;
                        break;
                }
                System.out.println("Task with id " + id + " changed " + changeType + " on " + result);
                return;
            }
        }
        System.out.println("Task with id " + id + " not found.");
    }

    @Override
    public void showTasks(AbstractTaskList taskList) {
        System.out.println(taskList);
        System.out.println("Input any button to main menu...");
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mainMenu() {
        System.out.println("1. Show tasks");
        System.out.println("2. Add task");
        System.out.println("3. Remove task");
        System.out.println("4. Change task");
        System.out.println("5. Calendar");
        System.out.println("0. Exit");
    }

    @Override
    public void calendar(AbstractTaskList taskList) throws IOException {
        System.out.println("Enter start date (format: dd-MM-yyyy HH:mm:ss) (0 - cancel): ");
        String in = reader.readLine();
        if ("0".equals(in)) {
            return;
        }
        LocalDateTime start = LocalDateTime.parse(in, DateTimeFormatter.ofPattern("dd-MM-yyyy:HH-mm-ss"));
        System.out.println("Enter end date (format: dd-MM-yyyy HH:mm:ss) (0 - cancel): ");
        in = reader.readLine();
        if ("0".equals(in)) {
            return;
        }
        LocalDateTime end = LocalDateTime.parse(in, DateTimeFormatter.ofPattern("dd-MM-yyyy:HH-mm-ss"));
        SortedMap<LocalDateTime, Set<Task>> sortedMap = Tasks.calendar(taskList, start, end);
        System.out.println("Date                 | Task         ");
        Object[] tasks;
        for (SortedMap.Entry<LocalDateTime, Set<Task>> entry : sortedMap.entrySet()) {
            tasks = entry.getValue().toArray();
            System.out.print(entry.getKey().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss")) + "| ");
            for (int i = 0; i < tasks.length; i++) {
                if (i+1 == tasks.length) {
                    System.out.print(((Task)tasks[i]).getTitle() + ".");
                } else {
                    System.out.print(((Task)tasks[i]).getTitle() + ", ");
                }
            }
            System.out.println();
        }
        System.out.println("Input any button to main menu...");
        reader.readLine();
    }
}
