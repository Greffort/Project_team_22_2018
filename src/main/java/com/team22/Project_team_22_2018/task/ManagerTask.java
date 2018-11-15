package com.team22.Project_team_22_2018.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

/**
 * @author ManagerTask
 */

public class ManagerTask {

    @Getter
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    public ObservableList<Task> getTaskList() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

//    @Override
//    public String toString() {
//        String s ="";
//        for (int i = 0; i < tasks.size(); i++) {
//            s+=getTasks().get(i).getNameTask()+"  ";
//            s+=getTasks().get(i).getDescriptionTask()+"  ";
//            s+=getTasks().get(i).getDeadLineTask()+"  ";
//        }
//        return s;
//    }
}
