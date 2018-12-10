package com.team22.Project_team_22_2018.util;

import java.net.URL;

/**
 * @author Greffort
 */
public interface Resources {

    URL LOGIN_FORM = Resources.class.getClassLoader().getResource("view/MainForm.fxml");
    URL CREATE_TASK_FORM = Resources.class.getClassLoader().getResource("view/CreateTaskForm.fxml");
    URL HELP_FORM = Resources.class.getClassLoader().getResource("view/HelpForm.fxml");

    URL HELP_TEXT = Resources.class.getClassLoader().getResource("file/help.txt");
    URL LOCAL_SAVE = Resources.class.getClassLoader().getResource("file/save");

}
