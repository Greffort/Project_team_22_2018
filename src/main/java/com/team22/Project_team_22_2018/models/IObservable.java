package com.team22.Project_team_22_2018.models;

import com.team22.Project_team_22_2018.view.IObserver;

import java.util.Observer;

/**
 * @author IObservable
 */
public interface IObservable {

    void registerObserver(IObserver observer);
    void notifyAllObservers();

}
