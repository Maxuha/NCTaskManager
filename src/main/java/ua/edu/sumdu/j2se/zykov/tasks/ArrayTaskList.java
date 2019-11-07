package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Arrays;

/**
 * class is create list from array.
 */
public class ArrayTaskList {

    /**
     * @variable tasks is array Task.
     */
    private Task[] tasks = new Task[0];

    /**
     * @param task is add task to array.
     */
    public void add(final Task task) {
        Task[] temp = tasks;
        tasks = new Task[temp.length + 1];
        for (int i = 0; i < temp.length; i++) {
            tasks[i] = temp[i];
        }
        tasks[tasks.length - 1] = task;
    }

    /**
     * @param task is delete task from array
     * @return is true if delete access
     */
    public boolean remove(final Task task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == task) {
                Task[] temp = tasks;
                tasks = new Task[temp.length - 1];
                for (int j = 0; j < i; j++) {
                    tasks[j] = temp[j];
                }
                for (int j = i + 1; j < temp.length; j++) {
                    tasks[j - 1] = temp[j];
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @return length array tasks.
     */
    public int size() {
        return tasks.length;
    }

    /**
     * @param index is number element from array
     * @return Task from array
     */
    public Task getTask(final int index) {
        return tasks[index];
    }

    /**
     * @param o is object equals
     * @return is true if this object = o
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayTaskList that = (ArrayTaskList) o;
        return Arrays.equals(tasks, that.tasks);
    }

    /**
     * @return hash code this object.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(tasks);
    }

    /**
     *
     * @param from is from date
     * @param to is to date
     * @return object is array task from date to date
     */
    public ArrayTaskList incoming(final int from, final int to) {
        ArrayTaskList arrayTaskList = new ArrayTaskList();
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].nextTimeAfter(from) != -1 && tasks[i].getEndTime() <= to) {
                if (tasks[i].isActive()) {
                    arrayTaskList.add(tasks[i]);
                }
            }
        }
        return arrayTaskList;
    }
}
