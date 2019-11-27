package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Iterator;

public abstract class AbstractTaskList implements Iterable<Task> {

    /**
     * @variable tasks is array Task.
     */
    protected Node head;
    protected Task[] tasks = new Task[10];
    protected AbstractTaskList abstractTaskList;
    protected int nTasks;
    protected int count;

    /**
     * @param task is add task to array.
     */
    public abstract void add(Task task);

    /**
     * @param task is delete task from array
     * @return is true if delete access
     */
    public abstract boolean remove(Task task);

    /**
     * @return length array tasks.
     */
    public abstract int size();

    /**
     * @param index is number element from array
     * @return Task from array
     */
    public abstract Task getTask(int index)
            throws IndexOutOfBoundsException;

    public void setAbstractTaskList(AbstractTaskList abstractTaskList) {
        this.abstractTaskList = abstractTaskList;
    }

    /**
     *
     * @param from is from date
     * @param to is to date
     * @return object is array task from date to date
     */
    public AbstractTaskList incoming(final int from, final int to) {
        if (abstractTaskList instanceof ArrayTaskList) {
            for (int i = 0; i < count; i++) {
                if (tasks[i].nextTimeAfter(from) != -1
                        && tasks[i].getEndTime() <= to
                        && tasks[i].isActive()) {
                        abstractTaskList.add(tasks[i]);
                }
            }
        } else if (abstractTaskList instanceof LinkedTaskList) {
            Node current = head;

            while (current != null) {
                if (current.task.nextTimeAfter(from) != -1
                        && current.task.getEndTime() <= to
                        && current.task.isActive()) {
                    abstractTaskList.add(current.task);
                }
                current = current.next;
            }
        } else {
            //do nothing
        }

        return abstractTaskList;
    }

    private class KeysIterator<Task> implements Iterator<Task> {
        int nextTask = 0;

        public boolean hasNext() {
            return nextTask < count;
        }

        public Task next() {
            Task result = (Task) tasks[nextTask];
            nextTask++;
            return result;
        }

        public void remove() {
            if (nextTask < nTasks - 1) {
                System.arraycopy(tasks, nextTask + 1,
                        tasks, nextTask, nTasks - nextTask - 1);
            }
            nTasks--;
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return new KeysIterator<Task>();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
