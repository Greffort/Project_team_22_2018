package com.team22.Project_team_22_2018.models.task.decorator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team22.Project_team_22_2018.models.task.BaseTask;
import com.team22.Project_team_22_2018.models.task.ITask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author TaskDecorator
 */
public class TaskDecorator implements ITask {

    private ITask task;

    public TaskDecorator(ITask task) {
        this.task = task;
    }

    @Override
    public int getStatus() {
        return task.getStatus();
    }

    @Override
    public void setStatus(int status) {
        task.setStatus(status);
    }

    @Override
    public String getDescription() {
        return task.getDescription();
    }

    @Override
    public void setDescription(String description) {
        task.setDescription(description);
    }

    @Override
    public String getName() {
        return task.getName();
    }

    @Override
    public void setName(String name) {
        task.setName(name);
    }

    @Override
    public int getProgressBar() {
        return task.getProgressBar();
    }

    @Override
    public void setProgressBar(int number) {
        task.setProgressBar(number);
    }

    @Override
    public LocalDate getDeadline() {
        return task.getDeadline();
    }

    @Override
    public void setDeadline(LocalDate date) {
        task.setDeadline(date);
    }

    @Override
    public LocalDate getRestTime() {
        return task.getRestTime();
    }

    @Override
    public void setRestTime(LocalDate date) {
        task.setRestTime(date);
    }

    @Override
    public LocalDate getDateClose() {
        return task.getDateClose();
    }

    @Override
    public void setDateClose(LocalDate date) {
        task.setDateClose(date);
    }

    @Override
    public LocalDate getDateOpen() {
        return task.getDateOpen();
    }

    @Override
    public void setDateOpen(LocalDate date) {
        task.getDateOpen();
    }

    @Override
    public List<ITask> getSubTasks() {
        List<ITask> list = new ArrayList<>();
        list.add(this);
        return list;
    }

    @Override
    public ITask createTask() {
//        return new SubTaskTask(new BaseTask());
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDecorator that = (TaskDecorator) o;
        return Objects.equals(task, that.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task);
    }
}
