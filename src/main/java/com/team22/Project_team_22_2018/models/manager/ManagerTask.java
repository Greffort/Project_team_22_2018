package com.team22.Project_team_22_2018.models.manager;

import com.team22.Project_team_22_2018.models.IObservable;
import com.team22.Project_team_22_2018.models.task.ITask;
import com.team22.Project_team_22_2018.view.IObserver;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observer;

/**
 * @author Greffort
 * <p>
 * Содержит коллекцию задач. Работает с ней.
 */

@Getter
@Setter
public final class ManagerTask implements IManagerTask,Serializable, IObservable {

    private final List<ITask> tasks;

    private List<IObserver> observers;

    public ManagerTask() {
        this(new ArrayList<>());
    }

    public ManagerTask(List<ITask> tasks) {
        this.tasks = tasks;
    }

    public void addTask(final ITask task) {
        tasks.add(task);
    }

    public void removeTask(int index){
        tasks.remove(index);
    }

    public ITask getTask(int index){
        return tasks.get(index);
    }

    public void setTask(int index, ITask task){
        tasks.set(index, task);
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

    @Override
    public String toString() {
        @NotNull val s = new StringBuilder();
        for (@NotNull val task : tasks) {
            s.append(task.toString()).append("   ");
        }
        return s.toString();
    }

        @Override
    public void registerObserver(IObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        //словили изменение -> оповестили всех слушателей
        for(IObserver observer : observers) {
            observer.handleEvent();
        }
    }
}