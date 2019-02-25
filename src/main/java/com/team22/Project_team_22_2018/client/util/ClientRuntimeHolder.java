package com.team22.project_team_22_2018.client.util;

import com.team22.project_team_22_2018.client.controller.ControllerView;

public class ClientRuntimeHolder {
    private static final ControllerView controllerViewHolder = new ControllerView();

    public static ControllerView getControllerViewHolder() {
        return controllerViewHolder;
    }
}
