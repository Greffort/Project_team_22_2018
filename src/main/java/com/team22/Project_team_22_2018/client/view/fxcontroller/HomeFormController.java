package com.team22.project_team_22_2018.client.view.fxcontroller;

import com.team22.project_team_22_2018.client.controller.ControllerView;
import com.team22.project_team_22_2018.client.util.ClientRuntimeHolder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import lombok.val;

import java.io.IOException;

import static com.team22.project_team_22_2018.util.Resources.LOGIN;

@Log4j
public class HomeFormController {

    private ControllerView controllerView = ClientRuntimeHolder.getControllerViewHolder();

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldLogin;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonChangeUser;

    @FXML
    void buttonChangeUser() {
        Stage stage = (Stage) this.textFieldLogin.getScene().getWindow();
        stage.onCloseRequestProperty();
        stage.close();
        openLoginForm();

        //добавить щакрытие главного окна
        //сменить пользователя
    }

    @FXML
    void buttonExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Project team 22");
        alert.setHeaderText("Вы уверены, что хотите выйти из приложения?");
        controllerView.save();
        controllerView.close();
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.exit(0);
                log.info("Приложение закрыто");
            }
        });
    }

    private void openLoginForm() {
        try {
            FXMLLoader loader = new FXMLLoader(LOGIN);
            final Parent root = loader.load();
            val scene = new Scene(root);
            val stageLoginForm = new Stage();

            stageLoginForm.setHeight(231);
            stageLoginForm.setWidth(159);
            stageLoginForm.minHeightProperty().setValue(231);
            stageLoginForm.minWidthProperty().setValue(159);
            stageLoginForm.maxHeightProperty().setValue(231);
            stageLoginForm.maxWidthProperty().setValue(159);

            stageLoginForm.setTitle("Login form");
            stageLoginForm.setScene(scene);
            stageLoginForm.show();
            stageLoginForm.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Project team 22");
                alert.setHeaderText("Вы уверены, что хотите закрыть окно?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        stageLoginForm.close();
                        log.info("Окно регистрации закрыто");
                    }
                });
            });
        } catch (IOException e) {
            log.error(e);
        }
    }
}
