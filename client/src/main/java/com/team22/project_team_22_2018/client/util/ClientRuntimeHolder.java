package com.team22.project_team_22_2018.client.util;

import com.team22.project_team_22_2018.client.controller.ControllerView;

public class ClientRuntimeHolder {

    private static final ControllerView CONTROLLER_VIEW_HOLDER = new ControllerView();

    public static ControllerView getControllerViewHolder() {
        return CONTROLLER_VIEW_HOLDER;
    }
}
