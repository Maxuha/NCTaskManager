package ua.edu.sumdu.j2se.zykov.tasks.model;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {
    private final static Logger logger = LoggerFactory.getLogger(TaskIO.class);

    /**
     * DEPRECATED
     * @param tasks - list task to save
     * @param out - output consumer
     * read task in listTask of file
     */
    @Deprecated
    public static void write(AbstractTaskList tasks, OutputStream out) {
        int size;
        long id;
        int nameLength;
        String name;
        String isActive;
        int repeatInterval;
        long start;
        long end;
        try (DataOutputStream data = new DataOutputStream(out)) {
            size = tasks.size();
            data.writeInt(size);
            for (Task task : tasks) {
                id = task.getId();
                name = task.getTitle();
                nameLength = name.length();
                if (task.isActive()) {
                    isActive = "Активність: 1";
                } else {
                    isActive = "Активність: 0";
                }
                repeatInterval = task.getRepeatInterval();
                start = task.getStartTime().toEpochSecond(ZoneOffset.UTC);
                data.writeLong(id);
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

    /**
     * DEPRECATED
     * @param tasks - which sheet to load from the file
     * @param in - input source
     * read in tasks of file
     */
    @Deprecated
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
        int size;
        long id;
        int nameLength;
        StringBuilder name;
        StringBuilder isActive;
        int repeatInterval;
        long start;
        long end;
        try (DataInputStream data = new DataInputStream(in)) {
            boolean isActiveBoolean;
            LocalDateTime startTime;
            LocalDateTime endTime;
            Task task;
            size = data.readInt();
            for (int i = 0; i < size; i++) {
                name = new StringBuilder();
                isActive = new StringBuilder();
                id = data.readLong();
                nameLength = data.readInt();
                for (int j = 0; j < nameLength; j++) {
                    name.append(data.readChar());
                }
                for (int j = 0; j < 13; j++) {
                    isActive.append(data.readChar());
                }
                repeatInterval = data.readInt();
                start = data.readLong();
                isActiveBoolean = isActive.toString().equals("Активність: 1");
                startTime = LocalDateTime.ofEpochSecond(start, 0, ZoneOffset.UTC);
                task = new Task(id, name.toString(), startTime);
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

    /**
     * DEPRECATED
     * @param tasks - list task to save
     * @param file - file consumer
     * read task in listTask of file (Binary format)
     */
    @Deprecated
    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException {
        int size;
        long id;
        int nameLength;
        String name;
        String isActive;
        int repeatInterval;
        long start;
        long end;
        try (DataOutputStream data = new DataOutputStream(new FileOutputStream(file))) {
            size = tasks.size();
            data.writeInt(size);
            for (Task task : tasks) {
                id = task.getId();
                name = task.getTitle();
                nameLength = name.length();
                ;
                if (task.isActive()) {
                    isActive = "Активність: 1";
                } else {
                    isActive = "Активність: 0";
                }
                repeatInterval = task.getRepeatInterval();
                start = task.getStartTime().toEpochSecond(ZoneOffset.UTC);
                data.writeLong(id);
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

    /**
     * DEPRECATED
     * @param tasks - which sheet to load from the file
     * @param file - file source
     * read in tasks of file (Binary format)
     */
    @Deprecated
    public static void readBinary(AbstractTaskList tasks, File file) throws IOException {
        int size;
        long id;
        int nameLength;
        StringBuilder name;
        StringBuilder isActive;
        int repeatInterval;
        long start;
        long end;
        try (DataInputStream data = new DataInputStream(new FileInputStream(file))) {
            boolean isActiveBoolean;
            LocalDateTime startTime;
            LocalDateTime endTime;
            Task task;
            size = data.readInt();
            for (int i = 0; i < size; i++) {
                name = new StringBuilder();
                isActive = new StringBuilder();
                id = data.readLong();
                nameLength = data.readInt();
                for (int j = 0; j < nameLength; j++) {
                    name.append(data.readChar());
                }
                for (int j = 0; j < 13; j++) {
                    isActive.append(data.readChar());
                }
                repeatInterval = data.readInt();
                start = data.readLong();
                isActiveBoolean = isActive.toString().equals("Активність: 1");
                startTime = LocalDateTime.ofEpochSecond(start, 0, ZoneOffset.UTC);
                task = new Task(id, name.toString(), startTime);
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


    /**
     * @param tasks - list task to save
     * @param out - output consumer
     * read task in listTask of file (Json format)
     */
    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        Gson gson = new Gson();
        out.write(gson.toJson(tasks, tasks.getClass()));
        out.close();
    }

    /**
     * @param tasks - which sheet to load from the file
     * @param in - input source
     * read in tasks of file (Json format)
     */
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

    /**
     * @param tasks - list task to save
     * @param out - file consumer
     * read task in listTask of file (Json format)
     */
    public static void writeText(AbstractTaskList tasks, File out) {
        Gson gson = new Gson();
        FileWriter writer = null;
        try {
            writer = new FileWriter(out);
            writer.write(gson.toJson(tasks, tasks.getClass()));
            logger.info("Saved tasks.json file.");
        } catch (IOException e) {
            logger.error("Failed to save tasks.json file.");
        }
       finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                logger.error("Failed to close tasks.json file");
            }
        }
    }

    /**
     * @param tasks - which sheet to load from the file
     * @param in - file source
     * read in tasks of file (Json format)
     */
    public static void readText(AbstractTaskList tasks, File in) {
        Gson gson = new Gson();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(in);
            AbstractTaskList tasksFrom;
            if (tasks.getClass().equals(ArrayTaskList.class)) {
                tasksFrom = gson.fromJson(fileReader, ArrayTaskList.class);
            } else {
                tasksFrom = gson.fromJson(fileReader, LinkedTaskList.class);
            }
            for (Task task : tasksFrom) {
                tasks.add(task);
            }
            logger.info("Loaded tasks.json file");
        } catch (FileNotFoundException e) {
            logger.error("Failed to load tasks.json file");
        }
        finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    logger.error("Failed to close tasks.json file");
                }
            }
        }
    }
}
