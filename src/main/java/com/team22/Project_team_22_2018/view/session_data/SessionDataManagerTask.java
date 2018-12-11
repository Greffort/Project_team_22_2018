package com.team22.Project_team_22_2018.view.session_data;

import com.team22.Project_team_22_2018.view.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SessionDataManagerTask
 * Этот клас нужен для представления полученных данных в таблице. В формате SimpleProperty.
 * Не используется
 */
@Getter
@Setter
public class SessionDataManagerTask {

    private final ObservableList<SessionDataTask> tasks;

    public SessionDataManagerTask() {
        this.tasks = FXCollections.observableArrayList();
    }

    public SessionDataManagerTask(ArrayList tasks) {
        this.tasks = (ObservableList<SessionDataTask>) tasks;
    }

    public ObservableList<SessionDataTask> getTasks() {
        return tasks;
    }

    public void addTask(SessionDataTask sessionDataTask) {
        this.tasks.add(sessionDataTask);
    }
}
