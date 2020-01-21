package ua.edu.sumdu.j2se.zykov.tasks.controller;

import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ConsoleController extends Controller {
    private static final Logger log = Logger.getLogger(String.valueOf(ConsoleController.class));
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
            case FINISH:
                try {
                    TaskIO.writeText(taskList, new File("tasks.json"));
                    log.info("Saved tasks.json file.");
                } catch (IOException e) {
                    log.warning("Failed to save file.");
                }
                System.exit(0);
                break;
        }
    }
}
