package ua.edu.sumdu.j2se.zykov.tasks.view;

public class NotificationConsole implements Notification {

    /**
     * @param sec - time to start task
     * @param title - name task
     * shows a notification in the console about a task with the name - title
     */
    @Override
    public void display(long sec, String title) {
        System.out.println("Task " + title + " must start in " + (sec / 60) + " minutes");
    }
}
