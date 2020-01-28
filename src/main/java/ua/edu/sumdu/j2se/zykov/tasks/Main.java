package ua.edu.sumdu.j2se.zykov.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.sumdu.j2se.zykov.tasks.controller.Actions;
import ua.edu.sumdu.j2se.zykov.tasks.controller.ConsoleController;
import ua.edu.sumdu.j2se.zykov.tasks.controller.Controller;
import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.zykov.tasks.view.ConsoleView;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

import java.io.File;
import java.io.IOException;

/**
 * class main - class start point program.
 */
public class Main {
/**
* start point is program.
* @param args as array arguments run program.
*/
public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(Main.class);
    AbstractTaskList taskList = new ArrayTaskList();
    try {
        TaskIO.readText(taskList, new File("tasks.json"));
        logger.info("Loaded tasks.json file");
    } catch (IOException e) {
        logger.error("Failed to load tasks.json file");
    }
    View view = new ConsoleView();
    Controller controller = new ConsoleController(view, taskList);
    controller.process(Actions.MAIN_MENU);
}
}
