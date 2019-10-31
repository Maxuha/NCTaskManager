package ua.edu.sumdu.j2se.studentLastName.tasks;

import java.util.Objects;

/**
 * class Task - describe Task.
 */
public class Task {
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
    private int time;
    /**
     * startTime is start to time run task.
     */
    private int startTime;
    /**
     * endTime is end to time run task.
     */
    private int endTime;
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
     * @param pTitle is title current task.
     */
    public void setTitle(final String pTitle) {
        this.title = pTitle;
    }

    /**
     * @return is active task.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param pActive is active current task.
     */
    public void setActive(final boolean pActive) {
        this.active = pActive;
    }

    /**
     * @param pTitle is title current task.
     * @param pTime is time current task.
     */
    public Task(final String pTitle, final int pTime) {
        this.title = pTitle;
        this.time = pTime;
        endTime = pTime;
        repeated = false;
    }

    /**
     * @param pTitle is title current task.
     * @param start is start time current task.
     * @param end is end time current task.
     * @param interval is repeat interval time current task.
     */
    public Task(final String pTitle, final int start, final int end,
                final int interval) {
        this.title = pTitle;
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        repeated = true;
    }

    /**
     * @return time current task.
     */
    public int getTime() {
        return time;
    }

    /**
     * @param pTime is time current task.
     */
    public void setTime(final int pTime) {
        this.time = pTime;
        endTime = pTime;
    }

    /**
     * @return start time current task.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * @return end time current task.
     */
    public int getEndTime() {
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
    public void setTime(final int start, final int end, final int interval) {
        startTime = start;
        endTime = end;
        repeatInterval = interval;
    }

    /**
     * @return is repeat current task.
     */
    public boolean isRepeated() {
        return repeated;
    }

    /**
     * @param o is object equals
     * @return is true if call object = o else false
     */
    @Override
    public boolean equals(final Object o) {
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
     * if task not active @return -1.
     * if task not repeated @return current time.
     */
    public int nextTimeAfter(final int current) {
        if (current > endTime) {
            active = false;
            repeated = false;
        }

        if (!active) {
            return -1;
        }

        if (!repeated) {
            return time;
        }

        time = startTime;
        for (int i = startTime; i < current; i += repeatInterval) {
            time += repeatInterval;
        }

        return time;
    }
}
