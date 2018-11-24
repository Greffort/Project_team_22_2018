package com.team22.Project_team_22_2018.task;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @author ManagerTask
 *
 * Содержит коллекцию задач. Работает с ней.
 */
@Getter
@Setter
public final class ManagerTask {

    @Getter
    private ArrayList<Task> tasks;
//    private ObservableList<Task> tasks;

    public ManagerTask(final ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ManagerTask() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(final Task task) {
        tasks.add(task);
    }

    public void updateList(ArrayList<Task> arrL){
        this.tasks = arrL;
    }

}
