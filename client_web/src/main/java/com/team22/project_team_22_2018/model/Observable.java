package com.team22.project_team_22_2018.model;

import com.team22.project_team_22_2018.Observer;
//import com.team22.project_team_22_2018.client.view.Observer;

public interface Observable {
    void registerObserver(Observer observer);

    void notifyAllObservers();
}
