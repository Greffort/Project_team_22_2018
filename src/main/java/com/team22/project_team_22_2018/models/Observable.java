package com.team22.project_team_22_2018.models;

import com.team22.project_team_22_2018.view.Observer;

public interface Observable {


    void registerObserver(final Observer observer);

    void notifyAllObservers();

}
