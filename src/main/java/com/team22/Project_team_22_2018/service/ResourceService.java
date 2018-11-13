package com.team22.Project_team_22_2018.service;

import com.futur.common.helpers.StringHelper;
import com.futur.common.helpers.resources.ResourcesHelper;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

public final class ResourceService {

    @NotNull
    public static final URL START_LAYOUT_FXML = ResourcesHelper.getInternalUrl("view/LoginForm.fxml");

    private ResourceService() {
        StringHelper.throwNonInitializeable();
    }
}
