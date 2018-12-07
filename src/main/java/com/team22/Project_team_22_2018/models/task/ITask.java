package com.team22.Project_team_22_2018.models.task;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ITask
 */
public interface ITask {

    int getStatus();

    void setStatus(int status);

    String getDescription();

    void setDescription(String description);

    String getName();

    void setName(String name);

    int getProgressBar();

    void setProgressBar(int number);

    LocalDate getDeadline();

    void setDeadline(LocalDate date);

    LocalDate getRestTime();

    void setRestTime(LocalDate date);

    LocalDate getDateClose();

    void setDateClose(LocalDate date);

    LocalDate getDateOpen();

    void setDateOpen(LocalDate date);

    List<ITask> getSubTasks();

    ITask createTask();

}
