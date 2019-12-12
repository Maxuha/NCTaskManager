package ua.edu.sumdu.j2se.zykov.tasks;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

/**
 * class Task - describe Task.
 */
public class Task implements Cloneable {
    /**
     * title is name task.
     */
    private String title;
    /**
     * active is task do work.
     */
    private boolean active;
    /**
     * repeated is task has repeated.
     */
    private boolean repeated;
    /**
     * time is run task.
     */
    private LocalDateTime time;
    /**
     * startTime is start to time run task.
     */
    private LocalDateTime startTime;
    /**
     * endTime is end to time run task.
     */
    private LocalDateTime endTime;
    /**
     * repeatInterval is interval repeated to time run task.
     */
    private int repeatInterval;

    /**
     * @return get title task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title is title current task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return is active task.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active is active current task.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @param title is title current task.
     * @param time is time current task.
     */
    public Task(String title, LocalDateTime time)
            throws IllegalArgumentException {
        if (time == null) {
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.time = time;
        startTime = time;
        endTime = time;
        repeatInterval = 0;
        repeated = false;
    }

    /**
     * @param title is title current task.
     * @param start is start time current task.
     * @param end is end time current task.
     * @param interval is repeat interval time current task.
     */
    public Task(String title, LocalDateTime start, LocalDateTime end,
                int interval) throws IllegalArgumentException {
        if (start == null && end == null && interval <= 0) {
            throw new IllegalArgumentException();
        }
        this.title = title;
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        time = startTime;
        repeated = true;
    }

    /**
     * @return time current task.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * @param time is time current task.
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
        startTime = time;
        endTime = time;
        repeatInterval = 0;
        repeated = false;
    }

    /**
     * @return start time current task.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @return end time current task.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @return repeat interval time current task.
     */
    public int getRepeatInterval() {
        return repeatInterval;
    }

    /**
     * @param start is start time current task.
     * @param end is end time current task.
     * @param interval is repeat interval time current task.
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        time = startTime;
        repeated = true;
    }

    /**
     * @return is repeat current task.
     */
    public boolean isRepeated() {
        return repeated;
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    /**
     * @param o is object equals
     * @return is true if call object = o else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return active == task.active
                && repeated == task.repeated
                && time == task.time
                && startTime == task.startTime
                && endTime == task.endTime
                && repeatInterval == task.repeatInterval
                && title.equals(task.title);
    }

    /**
     * @return hashCode object
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, active, repeated, time,
                startTime, endTime, repeatInterval);
    }

    /**
     * @return object to string
     */
    @Override
    public String toString() {
        return "Task{" + "title='" + title + '\''
                + ", active=" + active
                + ", repeated=" + repeated
                + ", time=" + time
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", repeatInterval=" + repeatInterval
                + '}';
    }

    /**
     * @param current is current time run task.
     * @return next time run task.
     * if task not active @return null.
     * if task not repeated @return current time.
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        System.out.println(startTime);
        if (!endTime.isAfter(current) || !active) {
            return null;
        }

        if (!repeated) {
            return time;
        }

        LocalDateTime temp = startTime;

        while (!temp.isAfter(current)) {
            temp = temp.plusSeconds(repeatInterval);
        }
        if (temp.isAfter(endTime)) {
            return null;
        } else {
            return temp;
        }
    }
}
