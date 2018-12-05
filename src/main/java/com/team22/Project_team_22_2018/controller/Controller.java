package com.team22.Project_team_22_2018.controller;

import com.team22.Project_team_22_2018.models.manager.IManagerTask;

import com.team22.Project_team_22_2018.view.IView;
import com.team22.Project_team_22_2018.view.fxcontrollers.MainController;

/**
 * @author Controller
 */
public class Controller implements IController {

    IManagerTask managerTask;
    IView view;

    public Controller(IManagerTask managerTask){
        this.managerTask = managerTask;
        view = new MainController(this,managerTask);
    }
}
