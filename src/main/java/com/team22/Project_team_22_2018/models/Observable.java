package com.team22.Project_team_22_2018.models;

import com.team22.Project_team_22_2018.view.Observer;

public interface Observable {
    void registerObserver(Observer observer);

    void notifyAllObservers();
}
