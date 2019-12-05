package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Arrays;

/**
 * class is create list from array.
 */
public class ArrayTaskList extends AbstractTaskList implements Cloneable {

    private final int k = 2;
    private Task[] tasks = new Task[10];

    /**
     * @param task is add task to array.
     */
    public void add(Task task) {
        if (count == tasks.length) {
            Task[] temp = tasks;
            tasks = new Task[tasks.length * k];
            tasks = Arrays.copyOf(temp, temp.length);
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayTaskList that = (ArrayTaskList) o;
        /*boolean isArray = Arrays.equals(tasks, that.tasks);
        if (isArray) {
            if (count != that.count) {
                isArray = false;
            }
        }
        if (isArray) {
            for (int i = 0; i < count; i++) {
                if (!tasks[i].equals(that.tasks[i])) {
                    isArray = false;
                }
            }
        }*/
        return count == that.count && Arrays.equals(tasks, that.tasks);
    }

    /**
     * @return hash code this object.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(tasks);
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        return (ArrayTaskList) super.clone();
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "tasks=" + Arrays.toString(tasks) +
                ", count=" + count +
                '}';
    }
}
