package com.team22.Project_team_22_2018.controller;

import com.team22.Project_team_22_2018.models.task.ITask;

import java.util.List;

/**
 * @author IController
 */
public interface IController {

    void addTask(final ITask task);

    void removeTask(int index);

    /*ITask*/ String getTask(int index);

    void setTask(int index, ITask task);

    List<ITask> getTasks();

}
