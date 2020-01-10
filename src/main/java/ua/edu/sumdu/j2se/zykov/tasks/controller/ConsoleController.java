package ua.edu.sumdu.j2se.zykov.tasks.controller;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

public class ConsoleController extends Controller {
    public ConsoleController(View view, AbstractTaskList taskList) {
        super(view, taskList);
    }

    @Override
    public void process(Actions actions) {
        switch (actions) {
            case MAIN_MENU: mainMenu();
                break;
            case SHOW_TASKS: showTasks();
                break;
            case ADD_TASK: addTask();
                break;
            case REMOVE_TASK: removeTask();
                break;
            case CHANGE_TASK: changeTask();
                break;
            case CALENDAR: calendar();
                break;
            case FINISH: System.exit(0);
                break;
        }
    }
}
