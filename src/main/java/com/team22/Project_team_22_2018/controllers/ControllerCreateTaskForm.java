package com.team22.Project_team_22_2018.controllers;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import com.team22.Project_team_22_2018.task.Task;
import javafx.application.Platform;
import javafx.fxml.FXML;


public class ControllerCreateTaskForm extends MainController {

    @FXML
    private TextField taskNameTextField;

    @FXML
    private TextArea taskDescriptionTextArea;

    @FXML
    private DatePicker taskDeadlineDatePicker;

    public void closeWindow() {
//        System.exit(0);
    }

    public void buttonCreateTask() {
        Task newTask = new Task(taskNameTextField.getText(), taskDescriptionTextArea.getText(), taskDeadlineDatePicker.getValue().toString());
        //System.out.println(newTask.getName() + " " + newTask.getDescription() + " " + newTask.getDeadLine());
        getManagerTask().addTask(newTask);
    }
}
