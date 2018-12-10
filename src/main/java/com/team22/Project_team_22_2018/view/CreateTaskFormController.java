package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.controller.Converter;
import com.team22.Project_team_22_2018.models.BaseTask;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import com.team22.Project_team_22_2018.view.session_data.SessionDataTask;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class CreateTaskFormController {

    Controller controller = RuntimeHolder.getControllerHolder();

    @FXML
    private javafx.scene.control.Button closeButton;

    @Setter
    private ObservableList<SessionDataTask> tasks;

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

        controller.addTask("1","1997-10-22","1997-10-22","1997-10-22","Выполняется","1");
//        SessionDataTask newTask = new SessionDataTask(taskNameTextField.getText(), LocalDate.of(1, 1, 1).toString(), LocalDate.of(1, 1, 1).toString(), LocalDate.of(1, 1, 1).toString(), taskDescriptionTextArea.getText(), LocalDate.of(1, 1, 1).toString(), "", "");
//        controller.addTask(newTask);
//        tasks.addAll(newTask);
try{
    System.out.println(Converter.toJSON(RuntimeHolder.getModelHolder()));
}catch (IOException e ){

}

        closeWindow();
    }

    public void loadTask(TableView.TableViewSelectionModel<SessionDataTask> selectionModel) throws ParseException {

//        taskNameTextField.setText(selectionModel.getSelectedItem().getName());
//        taskDescriptionTextArea.setText(selectionModel.getSelectedItem().getDescription());

        String string = "January 2, 2010";
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = format.parse(string);
        taskDeadlineDatePicker.setUserData(date);

    }
}