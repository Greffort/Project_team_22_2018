package com.team22.Project_team_22_2018.util;

import com.team22.Project_team_22_2018.models.task.ManagerTask;
import lombok.val;

import java.io.*;

/**
 * @author Greffort
 */
public final class Util {

    public static ManagerTask readTasks(final String path) throws IOException, ClassNotFoundException {
        return readObject(ManagerTask.class, path);
    }

    public static void writeTasks(final ManagerTask object, final String path) throws IOException {
        writeObject(object, path);
    }

    private static synchronized <T> T readObject(final Class<T> clazz, final String path) throws IOException, ClassNotFoundException {
        try (val oin = new ObjectInputStream(new FileInputStream(path))) {
            val o = oin.readObject();
            if (clazz.isInstance(o)) {
                return (T) o;
            }
        }
        return null;
    }

    private static synchronized void writeObject(final Object object, final String path) throws IOException {
        try (val oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(object);
        }
    }
}
