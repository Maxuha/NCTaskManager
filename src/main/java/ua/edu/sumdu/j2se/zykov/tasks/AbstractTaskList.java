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

    @Override
    public Iterator<Task> iterator() {
        final int[] currentIndex = {-1};
        return new Iterator<Task>() {
            @Override
            public boolean hasNext() {
                return currentIndex[0] < count-1;
            }

            @Override
            public Task next() throws IllegalStateException {
                if (!hasNext()) {
                    throw new IllegalStateException("Ent a list");
                } else {
                    return getTask(++currentIndex[0]);
                }
            }

            @Override
            public void remove() throws IllegalStateException {
                try {
                    if (AbstractTaskList.this.remove(getTask(currentIndex[0]))) {
                        currentIndex[0]--;
                    } else {
                        throw new IllegalStateException();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

    public abstract Stream<Task> getStream();
}
