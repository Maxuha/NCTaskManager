package ua.edu.sumdu.j2se.zykov.tasks.view;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.Task;

import java.io.IOException;

public interface View {
    /**
     * @return number selected task
     */
    int update();

    /**
     * @return new Task to add to taskList
     * @throws IOException to the controller
     */
    Task addTask() throws IOException;

    /**
     * @param taskList for remove task
     * @throws IOException to the controller
     */
    void removeTask(AbstractTaskList taskList) throws IOException;

    /**
     * @param taskList for change task
     * @throws IOException to the controller
     */
    void changeTask(AbstractTaskList taskList) throws IOException;

    /**
     * @param taskList for show all tasks
     */
    void showTasks(AbstractTaskList taskList);

    /**
     * show main menu
     */
    void mainMenu();

    /**
     * @param taskList for show calendar
     * @throws IOException to the controller
     */
    void calendar(AbstractTaskList taskList) throws IOException;

    /**
     * @param message to be show to user
     */
    void showMessage(String message);
}
