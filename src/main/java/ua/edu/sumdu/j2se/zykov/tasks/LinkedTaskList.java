package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList implements Cloneable {

    private Node head;

    /**
     * @param task is add task to array.
     */
    public void add(Task task) {
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
            while (current.next != null) {
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
    public boolean remove(Task task) {
        if (task == null || head == null)
            return false;

        if (head.task.equals(task)) {
            head = head.next;
            count--;
            return true;
        } else {
            Node temp = head;
            Node tempPre = head;
            boolean matched;
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
    public Task getTask(int index) {
        if (index < 0) {
            return null;
        }
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
        boolean isEquals = count == that.count;
        Node current = head;
        Node thatCurrent = that.head;
        while (current != null && thatCurrent != null) {
            if (!current.task.equals(thatCurrent.task)) {
                isEquals = false;
            }
            current = current.next;
            thatCurrent = thatCurrent.next;
        }
        return isEquals;
    }

    /**
     * @return hash code this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(head);
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        return (LinkedTaskList) super.clone();
    }

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                "head=" + head +
                ", count=" + count +
                '}';
    }
}
