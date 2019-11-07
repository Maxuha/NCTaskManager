package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Arrays;

public abstract class AbstractTaskList {
    /**
     * @variable tasks is array Task.
     */
    protected Task[] tasks = new Task[0];
    protected AbstractTaskList abstractTaskList;

    /**
     * @param task is add task to array.
     */
    public abstract void add(final Task task);

    /**
     * @param task is delete task from array
     * @return is true if delete access
     */
    public abstract boolean remove(final Task task);

    /**
     * @return length array tasks.
     */
    public abstract int size();

    /**
     * @param index is number element from array
     * @return Task from array
     */
    public abstract Task getTask(final int index) throws IndexOutOfBoundsException;

    /**
     *
     * @param from is from date
     * @param to is to date
     * @return object is array task from date to date
     */
    public AbstractTaskList incoming(final int from, final int to) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].nextTimeAfter(from) != -1
                    && tasks[i].getEndTime() <= to) {
                if (tasks[i].isActive()) {
                    abstractTaskList.add(tasks[i]);
                }
            }
        }
        return abstractTaskList;
    }
}
