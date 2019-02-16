package com.team22.project_team_22_2018.util;

import com.team22.project_team_22_2018.controller.Controller;
import com.team22.project_team_22_2018.models.Account;
import lombok.extern.log4j.Log4j;

/**
 * Здесь создаются экземпляры модели и контроллера, в дальнейшем везде работаем с ними
 */

@Log4j
public class RuntimeHolder {

    private static final Account modelHolder = new Account();
    private static final Controller controllerHolder = new Controller();

    public static Account getModelHolder() {
        return modelHolder;
    }

    public static Controller getControllerHolder() {
        return controllerHolder;
    }
}
