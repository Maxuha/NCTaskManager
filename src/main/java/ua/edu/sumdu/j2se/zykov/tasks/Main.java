package ua.edu.sumdu.j2se.zykov.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import ua.edu.sumdu.j2se.zykov.tasks.controller.Actions;
import ua.edu.sumdu.j2se.zykov.tasks.controller.ConsoleController;
import ua.edu.sumdu.j2se.zykov.tasks.controller.Controller;
import ua.edu.sumdu.j2se.zykov.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.zykov.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.zykov.tasks.view.ConsoleView;
import ua.edu.sumdu.j2se.zykov.tasks.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * class main - class start point program.
 */
public final class Main {
/**
* private default constructor is Main class.
*/
private Main() {
}
/**
* start point is program.
* @param args as array arguments run program.
*/
public static void main(String[] args) throws IOException {
    AbstractTaskList taskList = new ArrayTaskList();
    TaskIO.readText(taskList, new File("tasks.json"));
    View view = new ConsoleView();
    Controller controller = new ConsoleController(view, taskList);
    controller.process(Actions.MAIN_MENU);
}
}
