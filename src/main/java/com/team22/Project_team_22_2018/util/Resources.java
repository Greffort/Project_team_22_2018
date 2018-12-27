package com.team22.Project_team_22_2018.util;

import java.net.URL;

public interface Resources {

    URL LOGIN_FORM = Resources.class.getClassLoader().getResource("view/MainForm.fxml");
    URL HELP_FORM = Resources.class.getClassLoader().getResource("view/HelpForm.fxml");
    URL ADD_PURPOSE = Resources.class.getClassLoader().getResource("view/AddForm.fxml");

    URL HELP_TEXT = Resources.class.getClassLoader().getResource("file/help.txt");
    URL LOCAL_SAVE = Resources.class.getClassLoader().getResource("C:\\Users\\Aleks\\save.txt");
}
