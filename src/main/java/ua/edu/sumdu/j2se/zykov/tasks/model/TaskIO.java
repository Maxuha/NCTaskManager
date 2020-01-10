package ua.edu.sumdu.j2se.zykov.tasks.model;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {
    private static int size;
    private static int nameLength;
    private static String name;
    private static String isActive;
    private static int repeatInterval;
    private static long start;
    private static long end;

    public static void write(AbstractTaskList tasks, OutputStream out) {
        DataOutput data = new DataOutputStream(out);
        size = tasks.size();
        try {
            data.writeInt(size);
            for (Task task : tasks) {
                name = task.getTitle();
                nameLength = name.length();
                if (task.isActive()) {
                    isActive = "Активність: 1";
                } else {
                    isActive = "Активність: 0";
                }
                repeatInterval = task.getRepeatInterval();
                start = task.getStartTime().toEpochSecond(ZoneOffset.UTC);
                data.writeInt(nameLength);
                data.writeChars(name);
                data.writeChars(isActive);
                data.writeInt(repeatInterval);
                data.writeLong(start);
                if (repeatInterval != 0) {
                    end = task.getEndTime().toEpochSecond(ZoneOffset.UTC);
                    data.writeLong(end);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
        DataInput data = new DataInputStream(in);
        boolean isActiveBoolean;
        LocalDateTime startTime;
        LocalDateTime endTime;
        Task task;
        try {
            size = data.readInt();
            for (int i = 0; i < size; i++) {
                name = "";
                isActive = "";
                nameLength = data.readInt();
                for (int j = 0; j < nameLength; j++) {
                    name += data.readChar();
                }
                for (int j = 0; j < 13; j++) {
                    isActive += data.readChar();
                }
                repeatInterval = data.readInt();
                start = data.readLong();
                if (isActive.equals("Активність: 1")) {
                    isActiveBoolean = true;
                } else {
                    isActiveBoolean = false;
                }
                startTime = LocalDateTime.ofEpochSecond(start, 0, ZoneOffset.UTC);
                task = new Task(name, startTime);
                task.setActive(isActiveBoolean);
                if (repeatInterval != 0) {
                    end = data.readLong();
                    endTime = LocalDateTime.ofEpochSecond(end, 0, ZoneOffset.UTC);
                    task.setTime(startTime, endTime, repeatInterval);
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException {
        DataOutput data = new DataOutputStream(new FileOutputStream(file));
        size = tasks.size();
        try {
            data.writeInt(size);
            for (Task task : tasks) {
                name = task.getTitle();
                nameLength = name.length();;
                if (task.isActive()) {
                    isActive = "Активність: 1";
                } else {
                    isActive = "Активність: 0";
                }
                repeatInterval = task.getRepeatInterval();
                start = task.getStartTime().toEpochSecond(ZoneOffset.UTC);
                data.writeInt(nameLength);
                data.writeChars(name);
                data.writeChars(isActive);
                data.writeInt(repeatInterval);
                data.writeLong(start);
                if (repeatInterval != 0) {
                    end = task.getEndTime().toEpochSecond(ZoneOffset.UTC);
                    data.writeLong(end);
                }
            }
        } catch (IOException e) {

        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) throws IOException {
        DataInput data = new DataInputStream(new FileInputStream(file));
        boolean isActiveBoolean;
        LocalDateTime startTime;
        LocalDateTime endTime;
        Task task;
        try {
            size = data.readInt();
            for (int i = 0; i < size; i++) {
                nameLength = data.readInt();
                for (int j = 0; j < nameLength; j++) {
                    name += data.readChar();
                }
                for (int j = 0; j < 13; j++) {
                    isActive += data.readChar();
                }
                repeatInterval = data.readInt();
                start = data.readLong();
                if (isActive.equals("Активність: 1")) {
                    isActiveBoolean = true;
                } else {
                    isActiveBoolean = false;
                }
                startTime = LocalDateTime.ofEpochSecond(start, 0, ZoneOffset.UTC);
                task = new Task(name, startTime);
                task.setActive(isActiveBoolean);
                if (repeatInterval != 0) {
                    end = data.readLong();
                    endTime = LocalDateTime.ofEpochSecond(end, 0, ZoneOffset.UTC);
                    task.setTime(startTime, endTime, repeatInterval);
                }
                tasks.add(task);
            }
        } catch (IOException e) {

        }
    }

    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        Gson gson = new Gson();
        out.write(gson.toJson(tasks, tasks.getClass()));
        out.close();
    }

    public static void read(AbstractTaskList tasks, Reader in) throws IOException {
        Gson gson = new Gson();
        AbstractTaskList tasksFrom;
        if (tasks.getClass().equals(ArrayTaskList.class)) {
            tasksFrom = gson.fromJson(in, ArrayTaskList.class);
        } else {
            tasksFrom = gson.fromJson(in, LinkedTaskList.class);
        }
        for (Task task : tasksFrom) {
            tasks.add(task);
        }
        in.close();
    }

    public static void writeText(AbstractTaskList tasks, File out) throws IOException {
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(out);
        writer.write(gson.toJson(tasks, tasks.getClass()));
        writer.close();
    }

    public static void readText(AbstractTaskList tasks, File in) throws IOException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(in);
        AbstractTaskList tasksFrom;
        if (tasks.getClass().equals(ArrayTaskList.class)) {
            tasksFrom = gson.fromJson(fileReader, ArrayTaskList.class);
        } else {
            tasksFrom = gson.fromJson(fileReader, LinkedTaskList.class);
        }
        for (Task task : tasksFrom) {
            tasks.add(task);
        }
        fileReader.close();
    }
}
