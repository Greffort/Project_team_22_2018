package com.team22.Project_team_22_2018.controllers;

import com.team22.Project_team_22_2018.task.ManagerTask;
import com.team22.Project_team_22_2018.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author MainController
 *
 * Используется для хранения экземпляра коллекции ObservableList</>
 */
public class MainController {
    @Getter
    @Setter
    private static ManagerTask managerTask = new ManagerTask();

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    public void test() {
        Task task = new Task();
        String taskName = task.getName();
    }
}
