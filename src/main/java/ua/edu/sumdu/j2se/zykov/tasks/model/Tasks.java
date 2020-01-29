package ua.edu.sumdu.j2se.zykov.tasks.model;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {
    /**
     * @param start is from date
     * @param end is to date
     * @return object is array task from date to date
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        List<Task> taskList = new ArrayList<>();
        Iterator<Task> iterator = tasks.iterator();
        Task task;

        while (iterator.hasNext()) {
            task = iterator.next();
            if (task.nextTimeAfter(start) != null
                    && !task.nextTimeAfter(start).isAfter(end)
                    && task.isActive()) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    /**
     * @param tasks - list tasks for analysis
     * @param start - start time
     * @param end - finish time
     * @return time and related tasks to complete
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        Iterable<Task> iterable = incoming(tasks, start, end);
        Iterator<Task> iterator = iterable.iterator();
        SortedMap<LocalDateTime, Set<Task>> setSortedMap = new TreeMap<>();
        Task task;
        LocalDateTime timeTask;
        while (iterator.hasNext()) {
            task = iterator.next();
            timeTask = task.getTime();
            addToMapIsNoHisRepeatTask(setSortedMap, timeTask, task, start, end);

            while (task.isRepeated() && task.nextTimeAfter(timeTask) != null && !task.nextTimeAfter(timeTask).isAfter(end)) {
                timeTask = task.nextTimeAfter(timeTask);
                addToMapIsNoHisRepeatTask(setSortedMap, timeTask, task, start, end);
            }
        }

        return setSortedMap;
    }

    private static void addToMapIsNoHisRepeatTask(SortedMap<LocalDateTime, Set<Task>> setSortedMap, LocalDateTime timeTask, Task task, LocalDateTime start, LocalDateTime end) {
        if (!timeTask.isBefore(start) && !timeTask.isAfter(end)) {
            boolean isHis = false;
            Set<Task> set = new HashSet<>();
            set.add(task);
            for (Map.Entry<LocalDateTime, Set<Task>> entry : setSortedMap.entrySet()) {
                if (entry.getKey().equals(timeTask)) {
                    entry.getValue().add(task);
                    isHis = true;
                    break;
                }
            }
            if (!isHis) {
                setSortedMap.put(timeTask, set);
            }
        }
    }
}
