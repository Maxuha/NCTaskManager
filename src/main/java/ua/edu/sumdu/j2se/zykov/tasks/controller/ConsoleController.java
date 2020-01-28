package ua.edu.sumdu.j2se.zykov.tasks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

import java.io.File;
import java.io.IOException;

public class ConsoleController extends Controller {
    private static final Logger log = LoggerFactory.getLogger(ConsoleController.class.getName());
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
                    log.error("Failed to save tasks.json file.");
                }
                System.exit(0);
                break;
        }
    }
}
