package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Iterator;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task> {

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

    /**
     *
     * @param from is from date
     * @param to is to date
     * @return object is array task from date to date
     */
    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList abstractTaskList;
        if (this.getClass() == ArrayTaskList.class) {
            abstractTaskList = new ArrayTaskList();
        } else {
            abstractTaskList = new LinkedTaskList();
        }

        getStream().filter(task -> task != null && task.nextTimeAfter(from) != -1
                && task.getEndTime() <= to
                && task.isActive()).forEach(abstractTaskList::add);

        return abstractTaskList;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int currentIndex = -1;
            @Override
            public boolean hasNext() {
                return currentIndex < count-1;
            }

            @Override
            public Task next() throws IllegalStateException {
                if (!hasNext()) {
                    throw new IllegalStateException("Ent a list");
                } else {
                    return getTask(++currentIndex);
                }
            }

            @Override
            public void remove() throws IllegalStateException {
                if (!hasNext()) {
                    throw new IllegalStateException("End a list");
                } else {
                    try {
                        if (AbstractTaskList.this.remove(getTask(currentIndex))) {
                            currentIndex--;
                        } else {
                            throw new IllegalStateException();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        };
    }



    public abstract Stream<Task> getStream();
}
