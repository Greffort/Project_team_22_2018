package com.team22.Project_team_22_2018.ui.controllers;

import com.team22.Project_team_22_2018.ui.rows.TaskRow;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

public class CreateTaskFormController {

    @Setter
    private ObservableList<TaskRow> tasks;

    @FXML
    private TextField taskNameTextField;

    @FXML
    private TextArea taskDescriptionTextArea;

    @FXML
    private DatePicker taskDeadlineDatePicker;

    public void closeWindow(){
//        System.exit(0);
    }

    public void buttonCreateTask() {
        TaskRow newTask = new TaskRow(taskNameTextField.getText(), taskDescriptionTextArea.getText(), taskDeadlineDatePicker.getValue().toString(),"none","none");
        tasks.addAll(newTask);
    }
}
