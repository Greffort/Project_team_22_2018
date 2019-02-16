package com.team22.project_team_22_2018.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.team22.project_team_22_2018.view.Observer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Log4j
public class Account implements Serializable, Observable {

    @JsonSerialize
    private List<Purpose> purposes;

    @JsonIgnore
    private List<Observer> observers = new ArrayList<>();

    private String name;

    public Account() {
        this.purposes = new ArrayList<>();
        this.name = "not specified";
    }

    public Account(List<Purpose> purposes, String name) {
        this.purposes = purposes;
        this.name = name;
    }

    public void addPurpose(final Purpose purpose) {

        purposes.add(purpose);
        notifyAllObservers();
    }

    public void removePurpose(final int index) {
        purposes.remove(index);
        notifyAllObservers();
    }

    public Purpose getPurpose(final int index) {
        return purposes.get(index);
    }

    public void setPurpose(final int index,final  Purpose purpose) {
        purposes.set(index, purpose);
        notifyAllObservers();
    }

    public void clearPurposes() {
        purposes = new ArrayList<>();
        notifyAllObservers();
    }

    @Override
    public void registerObserver(final Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.handleEvent();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(purposes, account.purposes) &&
                Objects.equals(observers, account.observers) &&
                Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purposes, observers, name);
    }
}
