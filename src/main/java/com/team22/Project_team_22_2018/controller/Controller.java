package com.team22.Project_team_22_2018.controller;

import com.team22.Project_team_22_2018.models.IModel;
import com.team22.Project_team_22_2018.models.task.ITask;
import com.team22.Project_team_22_2018.view.IView;
import com.team22.Project_team_22_2018.view.fxcontrollers.MainController;

import java.util.List;

/**
 * @author Controller
 */
public class Controller implements IController {

    IModel managerTask;
    IView view;

    public Controller(IModel managerTask) {
        this.managerTask = managerTask;
        view = new MainController(this, managerTask);
    }

    @Override
    public void addTask(ITask task) {
        managerTask.addTask(task);
    }

    @Override
    public void removeTask(int index) {
        managerTask.removeTask(index);
    }

    @Override
    public /*ITask*/ String getTask(int index) {
//        return managerTask.getTask(index);
        return "";
    }

    @Override
    public void setTask(int index, ITask task) {
        managerTask.setTask(index, task);
    }

    @Override
    public List<ITask> getTasks() {
        return managerTask.getTasks();
    }
}
