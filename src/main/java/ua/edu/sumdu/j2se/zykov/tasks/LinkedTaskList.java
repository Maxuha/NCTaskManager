package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList {

    private Node head;
    private int count;

    /**
     * @param task is add task to array.
     */
    public void add(final Task task) {
        if (task == null)
            return;

        if (head == null)
        {
            head = new Node();
            head.task = task;
            head.next = null;
        }
        else
        {
            Node toAdd = new Node();
            toAdd.task = task;
            Node current = head;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = toAdd;
        }
        count++;
    }

    /**
     * @param task is delete task from array
     * @return is true if delete access
     */
    public boolean remove(final Task task) {
        if (task == null || head == null)
            return false;

        if (head.task.equals(task)) {
            head = head.next;
            count--;
            return true;
        } else {
            Node temp = head;
            Node tempPre = head;
            boolean matched = false;
            while (!(matched = temp.task.equals(task)) && temp.next != null) {
                tempPre = temp;
                temp = temp.next;
            }
            if (matched) {
                tempPre.next = temp.next;
                count--;
                return true;
            } else {
                return false;
            }
        }
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
    public Task getTask(final int index) {
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.task;
    }

    /**
     * @param o is object equals
     * @return is true if this object = o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) o;
        return count == that.count &&
                head.equals(that.head);
    }

    /**
     * @return hash code this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(head, count);
    }

    /**
     *
     * @param from is from date
     * @param to is to date
     * @return object is array task from date to date
     */
    public AbstractTaskList incoming(final int from, final int to) {
        abstractTaskList = TaskListFactory.
                createTaskList(ListTypes.types.LINKED);
        super.incoming(from, to);
        return abstractTaskList;
    }
}
