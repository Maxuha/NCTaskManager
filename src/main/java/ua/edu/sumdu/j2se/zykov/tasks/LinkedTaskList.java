package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Arrays;

public class LinkedTaskList extends AbstractTaskList {

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
        try {
            return tasks[index];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("This task does not exist");
        }
        return null;
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
        LinkedTaskList that = (LinkedTaskList) o;
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
    public AbstractTaskList incoming(final int from, final int to) {
        abstractTaskList = TaskListFactory.createTaskList(ListTypes.types.LINKED);
        super.incoming(from, to);
        return abstractTaskList;
    }
}
