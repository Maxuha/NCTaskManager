package ua.edu.sumdu.j2se.zykov.tasks.model;

public class TaskListFactory {
    /**
     * @param type - type list
     * @return - new type object ArrayTaskList or LinkedTaskList
     */
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        if (type == ListTypes.types.ARRAY) {
            return new ArrayTaskList();
        } else if (type == ListTypes.types.LINKED) {
            return new LinkedTaskList();
        }
        return null;
    }
}
