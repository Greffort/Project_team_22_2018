package com.team22.Project_team_22_2018.models.task.decorator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.team22.Project_team_22_2018.models.task.ITask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SubTaskTask
 */
public class SubTaskTask extends TaskDecorator {

    @JsonSerialize
    private List<ITask> subTasks;

    public SubTaskTask(ITask task) {
        super(task);
        this.subTasks = new ArrayList<>();
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(int status) {
        super.setStatus(status);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public int getProgressBar() {
        return super.getProgressBar();
    }

    @Override
    public void setProgressBar(int number) {
        super.setProgressBar(number);
    }

    @Override
    public LocalDate getDeadline() {
        return super.getDeadline();
    }

    @Override
    public void setDeadline(LocalDate date) {
        super.setDeadline(date);
    }

    @Override
    public LocalDate getRestTime() {
        return super.getRestTime();
    }

    @Override
    public void setRestTime(LocalDate date) {
        super.setRestTime(date);
    }

    @Override
    public LocalDate getDateClose() {
        return super.getDateClose();
    }

    @Override
    public void setDateClose(LocalDate date) {
        super.setDateClose(date);
    }

    @Override
    public LocalDate getDateOpen() {
        return super.getDateOpen();
    }

    @Override
    public void setDateOpen(LocalDate date) {
        super.setDateOpen(date);
    }

    public void addSubTask(ITask task, int index) {
        this.subTasks.add(index,task);
    }

    public ITask getSubTask(int index) {
        return this.subTasks.get(index);
    }

    public List<ITask> getSubTasks() {
        return subTasks;
    }

    public void setSubTask(ITask task, int index) {
        this.subTasks.set(index, task);
    }

    public void removeSubTask(int index) {
        this.subTasks.remove(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTaskTask that = (SubTaskTask) o;
        return Objects.equals(subTasks, that.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return "SubTaskTask{" +
                "subTasks=" + subTasks +
                '}';
    }
}
