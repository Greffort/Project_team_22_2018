package com.team22.Project_team_22_2018.view.fxcontrollers;

import com.team22.Project_team_22_2018.view.TaskRow;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class CreateTaskFormController {

    @FXML
    private javafx.scene.control.Button closeButton;

    @Setter

    private ObservableList<TaskRow> tasks;

    @FXML

    private TextField taskNameTextField;

    @FXML

    private TextArea taskDescriptionTextArea;

    @FXML

    private DatePicker taskDeadlineDatePicker;

    public void closeWindow() {

        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.close();

    }

    public void buttonCreateTask() {

        TaskRow newTask = new TaskRow(taskNameTextField.getText(), LocalDate.of(1,1,1)/*taskDeadlineDatePicker.getValue()*/, LocalDate.of(1,1,1),0, taskDescriptionTextArea.getText());

        tasks.addAll(newTask);

        closeWindow();

    }

    public void loadTask(TableView.TableViewSelectionModel<TaskRow> selectionModel) throws ParseException {

        taskNameTextField.setText(selectionModel.getSelectedItem().getName());

        taskDescriptionTextArea.setText(selectionModel.getSelectedItem().getDescription());

        String string = "January 2, 2010";

        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

        Date date = format.parse(string);

        taskDeadlineDatePicker.setUserData(date);

    }
}