package com.team22.Project_team_22_2018.controller;

import com.team22.Project_team_22_2018.models.Task;
import com.team22.Project_team_22_2018.models.ManagerTask;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

//import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
//import org.apache.logging.log4j.Logger;

/**
 * @author Greffort
 */

public class Controller {

    ManagerTask managerTask;
    //    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    public Controller(ManagerTask managerTask) {
        this.managerTask = RuntimeHolder.getModelHolder();
    }

//    public ObservableList getListStatus(){
//        managerTask.
//    }

//    public void getListStatus(){
//        managerTask.getListStatus();
//    }

    public void addTask(String name, String deadline, String dateClose, String dateOpen, String status, String description) {

        managerTask.addTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description));
    }

    public void addSubTask(int indexTask, String name, String deadline, String dateClose, String dateOpen, String status, String description) {
        managerTask.getTask(indexTask).addSubTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description));
    }

    public void addSubTask(int indexTask, int indexSubTask, String name, String deadline, String dateClose, String dateOpen, String status, String description) {
        managerTask.getTask(indexTask).addSubTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description), indexSubTask);
    }

    public void removeTask(int index) {
        managerTask.removeTask(index);
    }

    public Task getTask(int index) {
        return managerTask.getTask(index);
    }

    public void setTask(int index, com.team22.Project_team_22_2018.view.session_data.SessionDataTask task) {
        try {
            managerTask.setTask(index,Converter.toJavaObject(Converter.toJSON(task), Task.class));
        } catch (IOException e) {
            //запись в лог
        }

//        managerTask.setTask(index, task);
    }

    public ObservableList getTasks() {
//        SessionDataManagerTask sessionDataManagerTask = new SessionDataManagerTask();
        ObservableList<com.team22.Project_team_22_2018.view.session_data.SessionDataTask> sessionDataTasks = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < managerTask.getTasks().size(); i++) {
                String s = Converter.toJSON(managerTask.getTask(i));
                sessionDataTasks.add(Converter.toJavaObject(s, com.team22.Project_team_22_2018.view.session_data.SessionDataTask.class));
            }
            return sessionDataTasks;
        } catch (IOException e) {
            //запись в лог
        }
        return null;
    }

    public void setTasks(ObservableList tasks) {

        ArrayList arrayList = new ArrayList();
        try {
            for (int i = 0; i < tasks.size(); i++) {
                String s = Converter.toJSON(tasks.get(i));
                arrayList.add(Converter.toJavaObject(s, Task.class));
            }
            managerTask.setTasks(arrayList);
        } catch (IOException e) {
            //запись в лог
        }
    }
}
