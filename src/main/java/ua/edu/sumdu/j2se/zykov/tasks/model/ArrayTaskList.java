package ua.edu.sumdu.j2se.zykov.tasks.model;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * class is create list from array.
 */
public class ArrayTaskList extends AbstractTaskList {

    /**
     * k - coefficient for array expansion
     * tasks - array tasks
     */
    private transient final int k = 2;
    private Task[] tasks = new Task[10];

    /**
     * @param task is add task to array.
     */
    public void add(Task task) {
        if (count == tasks.length) {
            Task[] temp = tasks;
            tasks = new Task[tasks.length * k];
            tasks = Arrays.copyOf(temp, tasks.length);
        }
        tasks[count] = task;
        count++;
    }

    /**
     * @param task is delete task from array
     * @return is true if delete access
     */
    public boolean remove(Task task) {
        for (int i = 0; i < count; i++) {
            if (tasks[i].equals(task)) {
                Task[] temp = tasks;
                count--;
                System.arraycopy(temp, i+1, tasks, i, count - i);
                return true;
            }
        }
        return false;
    }

    /**
     * @return length array tasks.
     */
    public int size() {
        return count;
    }

    /**
     * @param index is number element from array
     * @return Task from array
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        return tasks[index];
    }

    /**
     * @param o is object equals
     * @return is true if this object = o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
     * @return new type object of ArrayTaskList based on current sheet
     */
    @Override
    public ArrayTaskList clone() {
        ArrayTaskList copy = (ArrayTaskList) TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        if (copy != null) {
            copy.tasks = tasks.clone();
            copy.count = count;
        }
        return copy;
    }

    /**
     * @return table tasks
     */
    @Override
    public String toString() {
        StringBuilder value = new StringBuilder("-------------------------------------------------------------------------------------------------------------------------------------------------\n" +
                "|       id       |       title       |        time          | active | repeated |      start time      |      last time       | repeat interval |");
        for (int i = 0; i < count; i++) {
            value.append("\n").append(tasks[i]);
        }
        value.append("\n-------------------------------------------------------------------------------------------------------------------------------------------------");
        return value.toString();
    }

    /**
     * @return tasks
     */
    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(tasks);
    }
}
