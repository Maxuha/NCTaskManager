package ua.edu.sumdu.j2se.zykov.tasks;

/**
 * class main - class start point program.
 */
public final class Main {
/**
* private default constructor is Main class.
*/
private Main() {
}
/**
* start point is program.
* @param args as array arguments run program.
*/
public static void main(String[] args) {
    AbstractTaskList listTest = new ArrayTaskList();
    for (int i = 0; i < 100; i++) {
        Task taskTest = new Task("some" + i, i);
        listTest.add(taskTest);
    }

    for (int i = 99; i > 5; i--) {
        listTest.remove(listTest.getTask(i));
    }

    for(Task t: listTest) {
        System.out.println(t);
    }

    AbstractTaskList listTest1 = new ArrayTaskList();
    for (int i = 0; i < 6; i++) {
        Task taskTest1 = new Task("some" + i, i);
        listTest1.add(taskTest1);
    }

    for(Task t: listTest1) {
        System.out.println(t);
    }

    System.out.println(listTest.equals(listTest1));
    /*Task task1 = new Task("task1", 1);
    Task task2 = new Task("task2", 2);
    AbstractTaskList abstractTaskList = new LinkedTaskList();
    abstractTaskList.add(task1);
    abstractTaskList.add(task2);
    AbstractTaskList abstractTaskList2 = new LinkedTaskList();
    abstractTaskList2.add(task2);
    abstractTaskList2.add(task1);
    try {
        Task copy = task1.clone();
        System.out.println(copy != null && task1.getTitle().equals(copy.getTitle()) && task1.getTime() == copy.getTime());
    }
    catch (CloneNotSupportedException e) {

    }*/
    /*while (abstractTaskList.iterator().hasNext()) {
        System.out.println(abstractTaskList.iterator().next());
    }
    System.out.println(abstractTaskList2.iterator().next());
    System.out.println(abstractTaskList.equals(abstractTaskList2));
    abstractTaskList2.iterator().remove();
    System.out.println(abstractTaskList2.iterator().next());
    System.out.println(abstractTaskList.equals(abstractTaskList2));*/

    /*for (int i = 0; i < 2; i++) {
        System.out.println(abstractTaskList.getTask(i));
    }

    for (int i = 0; i < 2; i++) {
        System.out.println(abstractTaskList2.getTask(i));
    }

    System.out.println(abstractTaskList.equals(abstractTaskList2));

    abstractTaskList2.remove(task2);
    abstractTaskList2.add(task2);

    System.out.println(abstractTaskList.equals(abstractTaskList2));

    for (int i = 0; i < 2; i++) {
        System.out.println(abstractTaskList.getTask(i));
    }

    for (int i = 0; i < 2; i++) {
        System.out.println(abstractTaskList2.getTask(i));
    }*/

    //AbstractTaskList abstractTaskList3 = new LinkedTaskList();
   // AbstractTaskList abstractTaskList4 = new LinkedTaskList();
   // System.out.println(abstractTaskList3.equals(abstractTaskList4));
}
}
