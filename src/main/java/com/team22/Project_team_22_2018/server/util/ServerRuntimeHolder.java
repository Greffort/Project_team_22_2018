package com.team22.project_team_22_2018.server.util;

import com.team22.project_team_22_2018.server.controller.ControllerModel;
import com.team22.project_team_22_2018.server.models.Account;

/**
 * @author ServerRuntimeHolder
 */
public class ServerRuntimeHolder {
    private Account modelHolder = new Account();
    private static ControllerModel controllerModelHolder = new ControllerModel();

    public Account getModelHolder() {
        return this.modelHolder;
    }

    public void setModelHolder(Account account) {
        modelHolder = account;
    }

    public ControllerModel getControllerModelHolder() {
        return controllerModelHolder;
    }
}
