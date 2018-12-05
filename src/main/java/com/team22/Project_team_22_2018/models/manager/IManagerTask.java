package com.team22.Project_team_22_2018.models.manager;

import com.team22.Project_team_22_2018.models.task.ITask;

import java.util.List;

/**
 * @author IManagerTask
 */
public interface IManagerTask {

    void addTask(final ITask task);

    void removeTask(int index);

    ITask getTask(int index);

    void setTask(int index, ITask task);

    List<ITask> getTasks();

}
