package com.team22.Project_team_22_2018.models.old;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.team22.Project_team_22_2018.models.Observable;
import com.team22.Project_team_22_2018.view.Observer;
import lombok.Getter;
import lombok.Setter;

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
    private List<Task> tasks;

    @JsonIgnore
    private List<Observer> observers = new ArrayList<>();

//    enum Status {IN_PROGRESS, CLOSE, OPEN, WAITING, TERMINATED}

    public ManagerTask() {
        this(new ArrayList<>());
    }

//    public com.team22.Project_team_22_2018.models.old.ManagerTask(List<Task> tasks) {
//
//        this(tasks, new ArrayList<>());
//    }

    public ManagerTask(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(final Task task) {

        tasks.add(task);
        notifyAllObservers();
    }

    public void removeTask(int index) {
        tasks.remove(index);
        notifyAllObservers();
    }

    public Task getTask(int index) {
        if(tasks.size()==0){
            return null;
        }else{
            return tasks.get(index);
        }
    }

    public void setTask(int index, Task task) {
        tasks.set(index, task);
        notifyAllObservers();
    }

//    public void getListStatus(){
////        ArrayList arrayList = Arrays.toString(Status.values());//new ArrayList();
//        System.out.println(Arrays.toString(Status.values()));
//
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerTask that = (ManagerTask) o;
        return Objects.equals(tasks, that.tasks) &&
                Objects.equals(observers, that.observers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks, observers);
    }

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
    public void searchBySubString(String subString){

        List<Task> filteredTasks = new ArrayList<>();

        for(Task i : tasks){
            if(i.toString().contains(subString)) filteredTasks.add(i);
        }
        //тут должен вызываться метод контроллера, который передаст список вьюхе и та обновит таблицу
    }
}