package ua.edu.sumdu.j2se.zykov.tasks.view;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.Task;

import java.io.IOException;

public interface View {
    int update();
    Task addTask() throws IOException;
    void removeTask(AbstractTaskList taskList) throws IOException;
    void changeTask(AbstractTaskList taskList) throws IOException;
    void showTasks(AbstractTaskList taskList);
    void mainMenu();
    void calendar();
    void showMessage(String message);
}
