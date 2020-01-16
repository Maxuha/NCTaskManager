package ua.edu.sumdu.j2se.zykov.tasks.view;

public class NotificationConsole implements Notification {

    @Override
    public void display(long sec, String title) {
        System.out.println("Task " + title + " must start in " + (sec / 60) + " minutes");
    }
}
