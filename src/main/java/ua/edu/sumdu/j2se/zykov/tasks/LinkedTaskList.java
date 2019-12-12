package ua.edu.sumdu.j2se.zykov.tasks;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList {

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
        for (int i = 0; i < count; i++) {
            if (!getTask(i).equals(getTask(i))) {
                isEquals = false;
                break;
            }
        }
        return isEquals;
    }

    /**
     * @return hash code this object.
     */
    @Override
    public int hashCode() {
        return head.task.getTime().hashCode() + head.task.getEndTime().hashCode() + head.task.getRepeatInterval() + head.task.getStartTime().hashCode() + head.task.getTitle().hashCode();
    }

    @Override
    public LinkedTaskList clone() {
        LinkedTaskList copy = (LinkedTaskList) TaskListFactory.createTaskList(ListTypes.types.LINKED);
        if (copy != null) {
            copy.head = head;
            copy.count = count;
        }
        return copy;
    }

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                "head=" + head +
                ", count=" + count +
                '}';
    }

    @Override
    public Stream<Task> getStream() {
        LinkedList linkedList = new LinkedList();
        Iterator<Task> iterator = iterator();
        while (iterator.hasNext()) {
            linkedList.add(iterator.next());
        }

        return linkedList.stream();
    }
}
