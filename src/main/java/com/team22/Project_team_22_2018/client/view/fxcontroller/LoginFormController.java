package com.team22.project_team_22_2018.client.view.fxcontroller;

import com.team22.project_team_22_2018.client.controller.ControllerView;
import com.team22.project_team_22_2018.client.util.ClientRuntimeHolder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import lombok.val;

import static com.team22.project_team_22_2018.util.Resources.MAIN_FORM;

@Log4j
public class LoginFormController {

    private ControllerView controllerView = ClientRuntimeHolder.getControllerViewHolder();

    @FXML
    private TextField textFieldLogin;
    @FXML
    private TextField textFieldPassword;

    @FXML
    void buttonLogin() {

        if (checkTextFields()) {
            if (controllerView.login(textFieldLogin.getText(), textFieldPassword.getText())) {
                openMainForm();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Project team 22");
                alert.setHeaderText("Неверный логин или пароль");
                alert.show();
            }
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

    @FXML
    void buttonRegistration() {
        if (!checkTextFields()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Заполните все поля!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    log.info("Не заполнены поля");
                }
            });
        } else if (controllerView.registration(textFieldLogin.getText(), textFieldPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Новый пользователь добавлен. Войти?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    buttonLogin();
                    log.info("Новый пользователь добавлен");
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("При регистрации возникли проблемы. Попробуйте еще раз");
            alert.show();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) this.textFieldLogin.getScene().getWindow();
        stage.onCloseRequestProperty();
        stage.close();
    }

    private void openMainForm() {
        try {
            assert MAIN_FORM != null;
            FXMLLoader loader = new FXMLLoader(MAIN_FORM);
            final Parent root = loader.load();
            val scene = new Scene(root);
            val stage = new Stage();
            stage.minHeightProperty().setValue(770);
            stage.minWidthProperty().setValue(1100);
            stage.setHeight(770);
            stage.setWidth(1100);
            stage.setTitle("team_22_project");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Project team 22");
                alert.setHeaderText("Вы уверены, что хотите выйти из приложения?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        controllerView.close();
                        log.info("Приложение закрыто");
                        System.exit(0);
                    }
                });
            });
            closeWindow();
        } catch (Exception e) {
            log.error(e);
        }
    }

    private boolean checkTextFields() {
        boolean name = this.textFieldLogin.getText().equals("");
        boolean description = this.textFieldPassword.getText().equals("");
        if (name) {
            this.textFieldLogin.requestFocus();
            return false;
        }
        if (description) {
            this.textFieldPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}
