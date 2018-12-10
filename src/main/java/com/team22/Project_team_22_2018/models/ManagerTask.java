package com.team22.Project_team_22_2018.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.team22.Project_team_22_2018.view.Observer;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Greffort
 * Содержит коллекцию задач. Работает с ней.
 */

@Getter
@Setter
public final class ManagerTask implements Serializable, Observable {

    @JsonSerialize
    private List<BaseTask> tasks;

    @JsonIgnore
    private List<Observer> observers = new ArrayList<>();

    public ManagerTask() {
        this(new ArrayList<>());
    }

//    public ManagerTask(List<BaseTask> tasks) {
//
//        this(tasks, new ArrayList<>());
//    }

    public ManagerTask(List<BaseTask> tasks) {
        this.tasks = tasks;
    }

    public void addTask(final BaseTask task) {

        tasks.add(task);
        notifyAllObservers();
    }

    public void removeTask(int index) {
        tasks.remove(index);
        notifyAllObservers();
    }

    public BaseTask getTask(int index) {
        return tasks.get(index);
    }

    public void setTask(int index, BaseTask task) {
        tasks.set(index, task);
        notifyAllObservers();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerTask that = (ManagerTask) o;
        return Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks);
    }

//    @Override
//    public String toString() {
//        @NotNull val s = new StringBuilder();
//        for (@NotNull val task : tasks) {
//            s.append(task.toString()).append("   ");
//        }
//        return s.toString();
//    }

    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.handleEvent();
        }
    }
}