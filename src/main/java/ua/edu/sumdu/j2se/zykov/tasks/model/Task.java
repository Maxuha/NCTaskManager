package ua.edu.sumdu.j2se.zykov.tasks.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * class Task - describe Task.
 */
public class Task implements Cloneable, Serializable {

    /**
     * id is unique identificator a task.
     */
    private long id;
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
     * @param id unique identificator a task.
     */
    public Task(long id, String title, LocalDateTime time)
            throws IllegalArgumentException {
        if (time == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.title = title;
        this.time = time;
        startTime = time;
        endTime = time;
        repeatInterval = 0;
        repeated = false;
        active = true;
    }

    /**
     * @param title is title current task.
     * @param start is start time current task.
     * @param end is end time current task.
     * @param interval is repeat interval time current task.
     * @param id unique identificator a task.
     */
    public Task(long id, String title, LocalDateTime start, LocalDateTime end,
                int interval) throws IllegalArgumentException {
        if (start == null && end == null && interval <= 0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.title = title;
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        time = startTime;
        repeated = true;
        active = true;
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
     * @return id a task.
     */
    public long getId() {
        return id;
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
                && time.equals(task.time)
                && startTime.equals(task.startTime)
                && endTime.equals(task.endTime)
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
        StringBuilder result = new StringBuilder("| " + id + "  | " + title);
        for (int i = 0; i < 18 - title.length(); i++) {
            result.append(" ");
        }
        result.append("| ").append(time.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"))).append(" | ");
        if (active) {
            result.append(true).append("   | ");
        } else {
            result.append(false).append("  | ");
        }
        if (repeated) {
            result.append(true).append("     | ");
        } else {
            result.append(false).append("    | ");
        }
        result.append(startTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"))).append(" | ").append(endTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"))).append(" | ").append(repeatInterval);
        for (int i = 0; i < 16 - String.valueOf(repeatInterval).length(); i++) {
            result.append(" ");
        }
        result.append("|");
        return result.toString();
    }

    /**
     * @param current is current time run task.
     * @return next time run task.
     * if task not active @return null.
     * if task not repeated @return current time.
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
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
