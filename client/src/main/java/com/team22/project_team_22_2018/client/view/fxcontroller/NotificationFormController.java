package com.team22.project_team_22_2018.client.view.fxcontroller;

import com.team22.project_team_22_2018.client.controller.ControllerView;
import com.team22.project_team_22_2018.client.util.ClientRuntimeHolder;
import com.team22.project_team_22_2018.client.view.util.model.MyObject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

@Log4j
public class NotificationFormController {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldNot;
    @FXML
    private DatePicker datePickerDeadline;
    @FXML
    private DatePicker datePickerDeadlineEdit;
    @FXML
    private Button saveEditPurpose1;
    @FXML
    private FontAwesomeIconView buttonOK;
    @FXML
    private ComboBox<String> comboBoxNot;
    @FXML
    private CheckBox checkBox;

    private String uuid;
    private MyObject myObject;
    private ControllerView controllerView = ClientRuntimeHolder.getControllerViewHolder();
    private Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");

    private ObservableList<String> listTime = FXCollections.observableArrayList("Минут", "Часов");

    @FXML
    public void initialize() {
        textFieldNot.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                textFieldNot.setText(oldValue);
            }
            if (comboBoxNot.getValue().equals("Минут")) {
                if (newValue.length() > 5) {
                    textFieldNot.setText(oldValue);
                }
            } else {
                if (newValue.length() > 3) {
                    String s = textFieldNot.getText().substring(0, 3);
                    textFieldNot.setText(s);
                }
            }
        });

        datePickerDeadline.setEditable(false);
        textFieldName.setEditable(false);
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (checkBox.isSelected()) {
                datePickerDeadlineEdit.setDisable(true);
                comboBoxNot.setDisable(true);
                textFieldNot.setDisable(true);
            } else {
                datePickerDeadlineEdit.setDisable(false);
                comboBoxNot.setDisable(false);
                textFieldNot.setDisable(false);
            }
        });

        comboBoxNot.setItems(listTime);
        comboBoxNot.setValue(listTime.get(0));
        comboBoxNot.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> textFieldNot.setText(""));
    }

    public void setMyObject(MyObject myObject) {
        this.uuid = myObject.getUuid();
        this.myObject = myObject;

        textFieldName.setText(myObject.getName());
        datePickerDeadline.setValue(LocalDate.parse(myObject.getDeadline()));
    }

    @FXML
    void buttonOK() {
        if (!checkBox.isSelected() && datePickerDeadlineEdit.getValue() == null && textFieldNot.getText().equals("")) {
            UtilForm.showAlertInformation(
                    "Project team 22",
                    "Изменити дедлайн, отложите уведомление или закройте цель",
                    "Ошибка при закрытии уведомления");
        } else {
            if (checkBox.isSelected()) {
                controllerView.setPurposeStatus(this.uuid, "Закрытая");
                controllerView.setPurposeDateClose(this.uuid, LocalDate.now().toString());
                close();
            }
            if (!datePickerDeadlineEdit.isDisable() && datePickerDeadlineEdit.getValue() != null) {
                controllerView.setGoal(
                        this.uuid,
                        this.myObject.getGoalStages(),
                        this.myObject.getName(),
                        this.myObject.getCriterionCompleted(),
                        this.myObject.getDescription(),
                        this.myObject.getStatus(),
                        String.valueOf(datePickerDeadlineEdit.getValue()),
                        this.myObject.getDateOpen(),
                        String.valueOf(DAYS.between(
                                LocalDate.parse(this.myObject.getCriticalTime()),
                                LocalDate.parse(this.myObject.getDeadline())
                        )),
                        false);
                close();
            }
            if (!textFieldNot.getText().equals("")) {
                long number;
                if (comboBoxNot.getValue().equals("Минут")) {
                    number = 60000000;//000
                } else {
                    number = 3600000;
                }
                long i = Long.parseLong(textFieldNot.getText());
                Thread thread = new Thread(() -> {
                    try {
                        Platform.runLater(this::close);
                        Thread.sleep(number * i);
                        Platform.runLater(() -> UtilForm.showNotification(this.myObject));
                    } catch (InterruptedException e) {
                        log.error(e);
                    }
                });
                thread.setDaemon(true);
                thread.start();

            }
        }
    }

    private boolean close() {
        Stage stage = (Stage) this.textFieldName.getScene().getWindow();
        stage.onCloseRequestProperty();
        stage.close();
        return true;
    }
}