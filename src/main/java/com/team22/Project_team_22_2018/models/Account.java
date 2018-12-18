package com.team22.Project_team_22_2018.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.team22.Project_team_22_2018.view.Observer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Account
 */
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

    public void removePurpose(int index) {
        try {
            purposes.remove(index);
            notifyAllObservers();
        } catch (IndexOutOfBoundsException e) {
            log.error(e);
        }
    }

    public Purpose getPurpose(int index) {
            try{
                return purposes.get(index);
            }catch (IndexOutOfBoundsException e){
                log.error(e);
                return  null;
            }
    }

    public void setPurpose(int index, Purpose purpose) {
        purposes.set(index, purpose);
        notifyAllObservers();
    }

    public void clearParposes() {
        purposes = new ArrayList<>();
        notifyAllObservers();
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
}
