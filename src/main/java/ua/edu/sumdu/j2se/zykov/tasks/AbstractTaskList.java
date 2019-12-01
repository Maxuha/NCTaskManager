package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Iterator;

public abstract class AbstractTaskList {

    //protected int nTasks;
    protected int count;
   // Iterator<Task> iterator;

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

        for (int i = 0; i < count; i++) {
            if (this.getTask(i) != null && this.getTask(i).nextTimeAfter(from) != -1
                    && this.getTask(i).getEndTime() <= to
                    && this.getTask(i).isActive()) {
                abstractTaskList.add(this.getTask(i));
            }
        }

        return abstractTaskList;
    }

    /*int nextTask = 0;
    @Override
    public Iterator<Task> iterator() {
        System.out.println("sjfnds");
        Iterator<Task> iterator = new Iterator<Task>() {
            @Override
            public boolean hasNext() {
                return nextTask < count;
            }

            @Override
            public Task next() {

                Task result = (Task) tasks[nextTask];
                nextTask++;
                return result;
            }

            @Override
            public void remove() {
                if (nextTask < count) {
                    Task[] temp = (Task[]) tasks;
                    // tasks = new ua.edu.sumdu.j2se.zykov.tasks.Task[temp.length-1];
                    System.arraycopy(temp, nextTask + 1,
                            tasks, nextTask, count - 1);
                }
                nextTask--;
            }
        };
        return iterator;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }*/
}
