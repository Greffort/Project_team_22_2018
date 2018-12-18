package com.team22.Project_team_22_2018.controller;

import com.team22.Project_team_22_2018.models.old.Task;
import com.team22.Project_team_22_2018.models.old.ManagerTask;
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

public class OldController {

    ManagerTask managerTask;
    //    private static final Logger log = LoggerFactory.getLogger(OldController.class);
//    public OldController(ManagerTask managerTask) {
//        this.managerTask = RuntimeHolder.getModelHolder();
//    }
//
////    public ObservableList getListStatus(){
////        managerTask.
////    }
//
////    public void getListStatus(){
////        managerTask.getListStatus();
////    }
//
//    public void addTask(String name, String deadline, String dateClose, String dateOpen, String status, String description) {
//
//        managerTask.addTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description));
//    }
//
//    public void addSubTask(int indexTask, String name, String deadline, String dateClose, String dateOpen, String status, String description) {
//        managerTask.getTask(indexTask).addSubTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description));
//    }
//
//    public void addSubTask(int indexTask, int indexSubTask, String name, String deadline, String dateClose, String dateOpen, String status, String description) {
//        managerTask.getTask(indexTask).addSubTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description), indexSubTask);
//    }
//
//    public void removeTask(int index) {
//        managerTask.removeTask(index);
//    }
//
//    public SessionDataTask getTask(int index) {
//        try {
//Task task = managerTask.getTask(index);
//String s = Converter.toJSON(task);
//SessionDataTask sessionDataTask = Converter.toJavaObject(s, SessionDataTask.class);
//
//            System.out.println();
//           return sessionDataTask;
//
//        } catch (IOException e) {
//            //запись в лог
//        }
//
//        return null;
//    }
//
//    public void setTask(int index, SessionDataTask task) {
//        try {
//            managerTask.setTask(index,Converter.toJavaObject(Converter.toJSON(task), Task.class));
//        } catch (IOException e) {
//            //запись в лог
//        }
//
////        managerTask.setTask(index, task);
//    }
//
//    public ObservableList getTasks() {
////        SessionDataManagerTask sessionDataManagerTask = new SessionDataManagerTask();
//        ObservableList<SessionDataTask> sessionDataTasks = FXCollections.observableArrayList();
//        try {
//            for (int i = 0; i < managerTask.getTasks().size(); i++) {
//                String s = Converter.toJSON(managerTask.getTask(i));
//                sessionDataTasks.add(Converter.toJavaObject(s, SessionDataTask.class));
//            }
//            return sessionDataTasks;
//        } catch (IOException e) {
//            //запись в лог
//        }
//        return null;
//    }

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
