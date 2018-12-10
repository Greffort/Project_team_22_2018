package com.team22.Project_team_22_2018.controller;

import com.team22.Project_team_22_2018.models.BaseTask;
import com.team22.Project_team_22_2018.models.ManagerTask;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import com.team22.Project_team_22_2018.view.session_data.SessionDataManagerTask;
import com.team22.Project_team_22_2018.view.session_data.SessionDataTask;
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

    public void addTask(BaseTask task) {
        managerTask.addTask(task);
    }

    public void addTask(
            String name,
            String deadline,
            String dateClose,
            String dateOpen,
            String status,
            String description
            /*String progressBar*/) {
        int intStatus;
        if (status == "Выполняется") {
            intStatus = 0;
        }
        if (status == "Закрыта") {
            intStatus = -1;
        }
        if (status == "Просрочена") {
            intStatus = 1;
        } else {
            intStatus = 100500;
        }
        managerTask.addTask(new BaseTask(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), intStatus, description));
    }

    public void addSubTask(int indexTask, BaseTask subTask) {
        managerTask.getTask(indexTask).addSubTask(subTask);
    }

    public void addSubTask(int indexTask, int indexSubTask, BaseTask subTask) {
        managerTask.getTask(indexTask).addSubTask(subTask, indexSubTask);
    }

    public void addSubTask(int indexTask, String name, String deadline, String dateClose, String dateOpen, String status, String description) {
        int intStatus;
        if (status == "Выполняется") {
            intStatus = 0;
        }
        if (status == "Закрыта") {
            intStatus = -1;
        }
        if (status == "Просрочена") {
            intStatus = 1;
        } else {
            intStatus = 100500;
        }
        managerTask.getTask(indexTask).addSubTask(new BaseTask(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), intStatus, description));
    }

    public void addSubTask(int indexTask, int indexSubTask, String name, String deadline, String dateClose, String dateOpen, String status, String description) {
        int intStatus;
        if (status == "Выполняется") {
            intStatus = 0;
        }
        if (status == "Закрыта") {
            intStatus = -1;
        }
        if (status == "Просрочена") {
            intStatus = 1;
        } else {
            intStatus = 100500;
        }
        managerTask.getTask(indexTask).addSubTask(new BaseTask(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), intStatus, description), indexSubTask);
    }

    public void removeTask(int index) {
        managerTask.removeTask(index);
    }

    public BaseTask getTask(int index) {
        return managerTask.getTask(index);
    }

    public void setTask(int index, BaseTask task) {
        managerTask.setTask(index, task);
    }

    public ObservableList getTasks() {
        SessionDataManagerTask sessionDataManagerTask = new SessionDataManagerTask();
        try {
            for (int i = 0; i < managerTask.getTasks().size(); i++) {
                String s = Converter.toJSON(managerTask.getTask(i));
                sessionDataManagerTask.addTask(Converter.toJavaObject(s, SessionDataTask.class));
            }
            return sessionDataManagerTask.getTasks();
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
                arrayList.add(Converter.toJavaObject(s, BaseTask.class));
            }
            managerTask.setTasks(arrayList);
        } catch (IOException e) {
            //запись в лог
        }
    }
}
