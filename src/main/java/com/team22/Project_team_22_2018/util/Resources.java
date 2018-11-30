package com.team22.Project_team_22_2018.util;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.nio.file.Path;

/**
 * @author Greffort
 */
public interface Resources {

    URL LOGIN_FORM = Resources.class.getClassLoader().getResource("view/MainForm.fxml");
    URL CREATE_TASK_FORM = Resources.class.getClassLoader().getResource("view/CreateTaskForm.fxml");
    URL HELP_FORM = Resources.class.getClassLoader().getResource("view/HelpForm.fxml");
//    File SAVE = new File("src/main/resources/file/save.txt");

    URL HELP_TEXT = Resources.class.getClassLoader().getResource("file/help.txt");
    URL LOCAL_SAVE = Resources.class.getClassLoader().getResource("file/save");

    Path SAVE = null;

}
