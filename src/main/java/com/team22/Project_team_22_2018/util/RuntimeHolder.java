package com.team22.Project_team_22_2018.util;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.models.ManagerTask;

/**
 * @author RuntimeHolder
 * Здесь создаются экземпляры модели и контроллера, в дальнейшем везде работаем с ними
 */
public class RuntimeHolder {

    private static final ManagerTask modelHolder = new ManagerTask();
    private static final Controller controllerHolder = new Controller(modelHolder);

    public static ManagerTask getModelHolder() {
        return modelHolder;
    }

    public static Controller getControllerHolder() {
        return controllerHolder;
    }
}
