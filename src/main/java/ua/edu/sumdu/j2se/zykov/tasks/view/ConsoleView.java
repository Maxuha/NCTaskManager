package ua.edu.sumdu.j2se.zykov.tasks.view;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class ConsoleView implements View{
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
        System.out.println("Input name a task: ");
        String message = reader.readLine();
        System.out.println("Input time to run a task");
        message += " " + reader.readLine();
        String[] values = message.split(" ");
        LocalDateTime dateTime = LocalDateTime.parse(values[1]);
        Task task = new Task(values[0], dateTime);
        showMessage("Task " + task.getTitle() + " added");
        return task;
    }

    @Override
    public String removeTask() {
        return null;
    }

    @Override
    public String changeTask() {
        return null;
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
    public void calendar() {

    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
