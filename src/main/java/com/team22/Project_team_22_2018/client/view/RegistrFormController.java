package com.team22.project_team_22_2018.client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;


@Log4j
public class RegistrFormController {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Button buttonOK;

    @FXML
    private void buttonOK(ActionEvent event) {

        //добавляем в файл пользователя. На серввере.
        //В контроллере добавить команды регистрации и логирования.
        //отправляем на контролеер данные, объектом, и ждем ответа от сервера.
        //если регистрация успешна, то входим в систему, если нет, то уведомляем и остаемся на том же окне.
        if (checkTextFields()) {
            //добавление пользователя
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Новый пользователь добавлен");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    closeWindow();
                    log.info("Новый пользователь добавлен");
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Заполните все поля!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    log.info("Не заполнены поля");
                }
            });
        }
    }

    private boolean checkTextFields() {
        boolean name = this.textFieldLogin.getText().equals("");
        boolean criterionCompleted = this.textFieldName.getText().equals("");
        boolean description = this.textFieldPassword.getText().equals("");

        if (name) {
            this.textFieldLogin.requestFocus();
            return false;
        }
        if (criterionCompleted) {
            this.textFieldName.requestFocus();
            return false;
        }
        if (description) {
            this.textFieldPassword.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    private void closeWindow() {
        Stage stage = (Stage) this.textFieldName.getScene().getWindow();
        stage.onCloseRequestProperty();
        stage.close();
    }


}
