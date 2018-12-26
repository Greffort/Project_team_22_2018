package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.models.Purpose;
import com.team22.Project_team_22_2018.models.PurposeStage;
import com.team22.Project_team_22_2018.models.old.Task;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Log4j
public class CreateTaskFormController {

    Controller controller = RuntimeHolder.getControllerHolder();

    MainController mainController = new MainController();
    @FXML
    private javafx.scene.control.Button closeButton;

    @Setter
    private ObservableList<Task> tasks;

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

        controller.addPurpose(new Purpose());
        controller.addPurposeStage(0, new PurposeStage());
//        controller.addTask("Имя задачи","1997-10-01","1997-10-02","1997-10-03","WAITING","Описание");
//        controller.addSubTask(0,0,"Подзадача","1997-10-01","1997-10-02","1997-10-03","WAITING","Описание");
//        Task newTask = new Task(taskNameTextField.getText(), LocalDate.of(1, 1, 1).toString(), LocalDate.of(1, 1, 1).toString(), LocalDate.of(1, 1, 1).toString(), taskDescriptionTextArea.getText(), LocalDate.of(1, 1, 1).toString(), "", "");
//        controller.addTask(newTask);
//        tasks.addAll(newTask);
//try{
////    System.out.println(Converter.toJson(RuntimeHolder.getModelHolder()));
//}catch (IOException e ){
//
//}
        mainController.setFlagAddStage(false);
        closeWindow();
    }

    public void loadTask(TableView.TableViewSelectionModel<Task> selectionModel) throws ParseException {

//        taskNameTextField.setText(selectionModel.getSelectedItem().getName());
//        taskDescriptionTextArea.setText(selectionModel.getSelectedItem().getDescription());

        String string = "January 2, 2010";
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = format.parse(string);
        taskDeadlineDatePicker.setUserData(date);
    }
}