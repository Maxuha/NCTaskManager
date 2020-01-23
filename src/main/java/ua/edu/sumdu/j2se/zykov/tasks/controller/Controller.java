package ua.edu.sumdu.j2se.zykov.tasks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.view.NotificationConsole;
import ua.edu.sumdu.j2se.zykov.tasks.view.NotificationTelegram;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

import java.io.IOException;

public abstract class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    protected View view;
    protected AbstractTaskList taskList;
    protected int action = -1;
    protected NotificationThread notificationThread;

    public Controller(View view, AbstractTaskList taskList) {
        this.view = view;
        this.taskList = taskList;
        ApiContextInitializer.init();
        log.info("Initialized api telegram.");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        NotificationTelegram notificationTelegram = new NotificationTelegram();
        try {
            telegramBotsApi.registerBot(notificationTelegram);
            log.info("Telegram bot is running.");
        } catch (TelegramApiRequestException e) {
            log.error("Error run telegram bot.");
            e.printStackTrace();
        }
        notificationThread = new NotificationThread(taskList);
        NotificationConsole notificationConsole = new NotificationConsole();
        notificationThread.register(notificationTelegram);
        notificationThread.register(notificationConsole);
        notificationThread.start();
        log.info("Notification is running.");
    }

    public abstract void process(Actions actions);

    public void addTask() {
        try {
            taskList.add(view.addTask());
            log.info("Task added.");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to add task. Error IO.");
        } catch (NullPointerException e) {
            System.out.println("Canceled operation.");
            log.info("Task add canceled");
        }
        backMenu();
        notificationThread.setTaskList(taskList);
        log.info("Set new task list.");
    }

    public void removeTask() {
        try {
            log.info("Opening remove menu...");
            view.removeTask(taskList);
            log.info("Task removed.");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to remove task. Error IO.");
        }
        backMenu();
        notificationThread.setTaskList(taskList);
        log.info("Set new task list.");
    }

    public void changeTask() {
        try {
            log.info("Opening change menu...");
            view.changeTask(taskList);
            log.info("Task changed.");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to change task. Error IO.");
        }
        backMenu();
        notificationThread.setTaskList(taskList);
        log.info("Set new task list.");
    }

    public void showTasks() {
        view.showTasks(taskList);
        mainMenu();
    }

    public void calendar() {
        try {
            log.info("Opening calendar...");
            view.calendar(taskList);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to open calendar.");
        }
        mainMenu();
    }

    public void mainMenu() {
        log.info("Opening main menu...");
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
            log.info("Sleep successfully.");
        } catch (InterruptedException e) {
            System.out.println("Error. Redirect to main menu.");
            log.error("Sleeping not successfully.");
        } finally {
            mainMenu();
        }
    }
}