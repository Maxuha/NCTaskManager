package ua.edu.sumdu.j2se.zykov.tasks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.Task;
import ua.edu.sumdu.j2se.zykov.tasks.view.Notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class NotificationThread extends Thread {
    /**
     * log - link on logger this class
     * taskList - current task list
     * notifications - list class Notification for different type notifications.
     * Example: NotificationConsole, TelegramConsole etc
     * lastTask - last notified task
     */
    private static final Logger log = LoggerFactory.getLogger(NotificationThread.class);
    private AbstractTaskList taskList;
    private List<Notification> notifications;
    private Task lastTask;

    /**
     * @param taskList - current taskList to check for alert
     */
    public NotificationThread(AbstractTaskList taskList) {
        super("NotificationThread");
        this.taskList = taskList;
        notifications = new ArrayList<>();
        log.info("Created notification thread.");
    }

    /**
     * @param taskList - set new task list to check for alert
     */
    public void setTaskList(AbstractTaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * @param notification - new notification for register
     */
    public void register(Notification notification) {
        notifications.add(notification);
    }

    /**
     * @param notification - new notification for unregister
     */
    public void unregister(Notification notification) {
        notifications.remove(notification);
    }


    /**
     * run notification thread
     */
    @Override
    public void run() {
        long sec;
        LocalDateTime time;
        while (true) {
            for (Task task: taskList) {
                time = task.nextTimeAfter(LocalDateTime.now());
                if (time != null) {
                    sec = (time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000)
                            - (LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000);
                    if (sec <= 180) {
                        if (lastTask == null || time.minusSeconds(sec).isAfter(lastTask.getTime())) {
                            for (Notification notification : notifications) {
                                notification.display(sec, task.getTitle());
                            }
                            try {
                                lastTask = task.clone();
                            } catch (CloneNotSupportedException e) {
                                log.error("Failed clone task.");
                            }
                            lastTask.setTime(time);
                            log.info("Alert about " + task.getTitle());
                        }
                    }
                }
            }
        }
    }
}
