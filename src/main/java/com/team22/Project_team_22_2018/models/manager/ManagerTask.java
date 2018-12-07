package com.team22.Project_team_22_2018.models.manager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.team22.Project_team_22_2018.models.IModel;
import com.team22.Project_team_22_2018.models.IObservable;
import com.team22.Project_team_22_2018.models.task.BaseTask;
import com.team22.Project_team_22_2018.models.task.ITask;
import com.team22.Project_team_22_2018.models.task.decorator.SubTaskTask;
import com.team22.Project_team_22_2018.util.Converter;
import com.team22.Project_team_22_2018.view.IObserver;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Greffort
 * <p>
 * Содержит коллекцию задач. Работает с ней.
 */

@Getter
@Setter
public final class ManagerTask implements IModel, /*Serializable,*/ IObservable {

    @JsonSerialize
    private final List<ITask> tasks;
//@JsonIgnore
    private List<IObserver> observers;

    public ManagerTask() {
        this(new ArrayList<>());
        this.observers = new ArrayList<>();
    }

    public ManagerTask(List<ITask> tasks) {
        this.tasks = tasks;
    }

    public void addTask(final ITask task) {
        tasks.add(task);
    }

    public void removeTask(int index) {
        tasks.remove(index);
    }

    public String getTask(int index) {
        String s = "Ошибка";
//        ITask task = tasks.get(index);
//
//        System.out.println(task);
//        try {
//            s = Converter.toJSON(this);
//            System.out.println(s);
//            Converter.toJavaObject(s);
//            ManagerTask mt = Converter.toJavaObject(s);
//            System.out.println(mt);
//        } catch (IOException e) {
//            e.printStackTrace();
//            //Запись в лог
//        }
        return s;

//        ITask
//        try {
//Converter.toJavaObject(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null/*tasks.get(index)*/;
    }

    public void setTask(int index, ITask task) {
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

//    public String getJson(){
//        String s = "Ошибка";
//        ManagerTask mg2 = new ManagerTask();
//        try {
//            s = Converter.toJSON(mg2);
//            System.out.println(s);
//            ManagerTask mg1 = Converter.toJavaObject(s);
//            System.out.println(mg1);
//        } catch (IOException e) {
//            e.printStackTrace();
//            //Запись в лог
//        }
//        return s;
//    }

    @Override
    public void registerObserver(IObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        //словили изменение -> оповестили всех слушателей
        for (IObserver observer : observers) {
            observer.handleEvent();
        }
    }
}