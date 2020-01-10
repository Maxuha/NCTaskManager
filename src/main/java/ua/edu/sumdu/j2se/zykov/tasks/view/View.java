package ua.edu.sumdu.j2se.zykov.tasks.view;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;

import java.io.IOException;

public interface View {
    int update();
    String addTask() throws IOException;
    String removeTask();
    String changeTask();
    void showTasks(AbstractTaskList taskList);
    void mainMenu();
    void calendar();
    void showMessage(String message);
}
