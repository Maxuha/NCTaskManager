package ua.edu.sumdu.j2se.zykov.tasks.controller;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.view.NotificationConsole;
import ua.edu.sumdu.j2se.zykov.tasks.view.NotificationTelegram;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

import java.io.IOException;

public abstract class Controller {
    protected View view;
    protected AbstractTaskList taskList;
    protected int action = -1;
    protected NotificationThread notificationThread;

    public Controller(View view, AbstractTaskList taskList) {
        this.view = view;
        this.taskList = taskList;
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        NotificationTelegram notificationTelegram = new NotificationTelegram();
        try {
            telegramBotsApi.registerBot(notificationTelegram);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        notificationThread = new NotificationThread(taskList);
        NotificationConsole notificationConsole = new NotificationConsole();
        notificationThread.register(notificationTelegram);
        notificationThread.register(notificationConsole);
        notificationThread.start();
    }

    public abstract void process(Actions actions);

    public void addTask() {
        try {
            taskList.add(view.addTask());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Canceled operation.");
        }
        backMenu();
        notificationThread.setTaskList(taskList);
    }

    public void removeTask() {
        try {
            view.removeTask(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        backMenu();
        notificationThread.setTaskList(taskList);
    }

    public void changeTask() {
        try {
            view.changeTask(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        backMenu();
        notificationThread.setTaskList(taskList);
    }

    public void showTasks() {
        view.showTasks(taskList);
        mainMenu();
    }

    public void calendar() {
        try {
            view.calendar(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    public void mainMenu() {
        view.mainMenu();
        action = view.update();
        Actions actions = Actions.EMPTY;
        switch (action) {
            case 1: actions = Actions.SHOW_TASKS;
                break;
            case 2: actions = Actions.ADD_TASK;
                break;
            case 3: actions = Actions.REMOVE_TASK;
                break;
            case 4: actions = Actions.CHANGE_TASK;
                break;
            case 5: actions = Actions.CALENDAR;
                break;
            case 0: actions = Actions.FINISH;
        }
        process(actions);
    }

    private void backMenu() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Error. Redirect to main menu.");
        } finally {
            mainMenu();
        }
    }
}
