package ua.edu.sumdu.j2se.zykov.tasks.controller;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.Task;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

import java.io.IOException;
import java.time.LocalDateTime;

public abstract class Controller {
    protected View view;
    protected AbstractTaskList taskList;
    protected int action = -1;

    public Controller(View view, AbstractTaskList taskList) {
        this.view = view;
        this.taskList = taskList;
    }

    public abstract void process(Actions actions);

    public void addTask() {
        String message = "";
        try {
            message = view.addTask();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] values = message.split(" ");
        LocalDateTime dateTime = LocalDateTime.parse(values[1]);
        Task task = new Task(values[0], dateTime);
        taskList.add(task);
        view.showMessage("Task " + task.getTitle() + " added");
        backMenu();
    }

    public void removeTask() {

    }

    public void changeTask() {

    }

    public void showTasks() {
        view.showTasks(taskList);
        mainMenu();
    }

    public void calendar() {

    }

    public void mainMenu() {
        view.mainMenu();
        action = view.update();
        Actions actions;
        switch (action) {
            case 0: actions = Actions.FINISH;
                break;
            case 1: actions = Actions.SHOW_TASKS;
                break;
            case 2: actions = Actions.ADD_TASK;
                break;
            case 3: actions = Actions.REMOVE_TASK;
                break;
            case 4: actions = Actions.CHANGE_TASK;
                break;
            default: actions = Actions.CALENDAR;
        }
        process(actions);
    }

    private void backMenu() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mainMenu();
    }
}
