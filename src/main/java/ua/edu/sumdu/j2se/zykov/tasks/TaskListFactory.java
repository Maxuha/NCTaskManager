package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.List;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        if (type == ListTypes.types.ARRAY) {
            return new ArrayTaskList();
        } else if (type == ListTypes.types.LINKED) {
            return new LinkedTaskList();
        }
        return null;
    }
}
