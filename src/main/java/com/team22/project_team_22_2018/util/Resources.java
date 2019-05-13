package com.team22.project_team_22_2018.util;

import java.net.URL;

public interface Resources {

    URL MAIN_FORM = Resources.class.getClassLoader().getResource("view/MainForm.fxml");
    URL HELP_FORM = Resources.class.getClassLoader().getResource("view/HelpForm.fxml");
    URL ADD_PURPOSE = Resources.class.getClassLoader().getResource("view/AddForm.fxml");
    URL LOGIN = Resources.class.getClassLoader().getResource("view/LoginForm.fxml");
//    URL REGISTR = Resources.class.getClassLoader().getResource("view/RegistrForm.fxml");
//    URL HOME = Resources.class.getClassLoader().getResource("view/HomeForm.fxml");
//    URL SAVE = Resources.class.getClassLoader().getResource("1");
    URL SSERVER_FORM = Resources.class.getClassLoader().getResource("view/ServerForm.fxml");
    URL NOTIFICATION = Resources.class.getClassLoader().getResource("view/NotificationForm.fxml");

    URL HELP_TEXT = Resources.class.getClassLoader().getResource("file/help.txt");
}
