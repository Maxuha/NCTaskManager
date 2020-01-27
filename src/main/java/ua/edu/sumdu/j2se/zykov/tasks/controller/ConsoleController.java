package ua.edu.sumdu.j2se.zykov.tasks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

import java.io.File;
import java.io.IOException;

public class ConsoleController extends Controller {
    /**
     * log is link on logger this class
      */
    private static final Logger log = LoggerFactory.getLogger(ConsoleController.class.getName());

    /**
     * @param view - view port (type view). Example: ConsoleView, WebView etc
     * @param taskList - sheet of tasks required for processing
     */
    public ConsoleController(View view, AbstractTaskList taskList) {
        super(view, taskList);
    }

    /**
     * @param actions enum type of task
     * run action
     */
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
                    log.error("Failed to save file.");
                }
                System.exit(0);
                break;
        }
    }
}
