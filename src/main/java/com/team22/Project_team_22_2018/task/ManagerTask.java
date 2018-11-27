package com.team22.Project_team_22_2018.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Greffort
 * <p>
 * Содержит коллекцию задач. Работает с ней.
 */

@RequiredArgsConstructor
public final class ManagerTask implements Serializable {

    @Getter
    private final List<Task> tasks;

    public void addTask(final Task task) {
        tasks.add(task);
    }

    @Override
    public String toString() {
        String s = "";
        for (Task task : tasks) {

            s += task.toString() + "   ";
        }
        return s;
    }
}