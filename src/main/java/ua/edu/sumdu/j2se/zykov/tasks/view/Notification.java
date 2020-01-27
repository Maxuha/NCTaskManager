package ua.edu.sumdu.j2se.zykov.tasks.view;

public interface Notification {
    /**
     * @param sec - time to start task
     * @param title - name task
     * shows a notification of a task with a name - title
     */
    void display(long sec, String title);
}
