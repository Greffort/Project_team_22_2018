package com.team22.project_team_22_2018.server.util;

import com.team22.project_team_22_2018.server.controller.ControllerModel;
import com.team22.project_team_22_2018.server.models.Account;

/**
 * @author ServerRuntimeHolder
 */
public class ServerRuntimeHolder {
    private static final Account modelHolder = new Account();
    private static final ControllerModel controllerModelHolder = new ControllerModel();

    public static Account getModelHolder() {
        return modelHolder;
    }

    public static ControllerModel getControllerModelHolder() {
        return controllerModelHolder;
    }
}
