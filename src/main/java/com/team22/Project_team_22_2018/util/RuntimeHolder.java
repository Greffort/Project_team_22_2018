package com.team22.Project_team_22_2018.util;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.controller.OldController;
import com.team22.Project_team_22_2018.models.Account;
import com.team22.Project_team_22_2018.models.old.ManagerTask;
import lombok.extern.log4j.Log4j;

/**
 * @author RuntimeHolder
 * Здесь создаются экземпляры модели и контроллера, в дальнейшем везде работаем с ними
 */

@Log4j
public class RuntimeHolder {

    private static final Account modelHolder = new Account();
    private static final Controller controllerHolder = new Controller(modelHolder);

    public static Account getModelHolder() {
        return modelHolder;
    }

    public static Controller getControllerHolder() {
        return controllerHolder;
    }
}
