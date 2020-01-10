package ua.edu.sumdu.j2se.zykov.tasks.view;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.Task;

import java.io.IOException;

public interface View {
    int update();
    Task addTask() throws IOException;
    String removeTask();
    String changeTask();
    void showTasks(AbstractTaskList taskList);
    void mainMenu();
    void calendar();
    void showMessage(String message);
}
