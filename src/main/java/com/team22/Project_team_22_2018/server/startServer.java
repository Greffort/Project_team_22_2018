package com.team22.project_team_22_2018.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;

import static com.team22.project_team_22_2018.util.Resources.SSERVER_FORM;

@Log4j
public class StartServer extends Application {

    private final String APPLICATION_NAME = "team_22_project_server";


    private static Thread threadGUI = new Thread(Application::launch);

    public static void main(@NotNull final String[] args) {
        if (!threadGUI.isAlive()) {
            threadGUI.start();
        }
    }

    @Override
    public void start(@NotNull final Stage primaryStage) {
        try {
            assert SSERVER_FORM != null;
            @NotNull final Parent root = FXMLLoader.load(SSERVER_FORM);
            @NotNull final Scene scene = new Scene(root);
            primaryStage.minHeightProperty().setValue(435);
            primaryStage.minWidthProperty().setValue(510);
            primaryStage.setHeight(435);
            primaryStage.setWidth(510);
            primaryStage.maxHeightProperty().setValue(435);
            primaryStage.maxWidthProperty().setValue(510);

            primaryStage.setTitle(APPLICATION_NAME);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Project team 22 - Server");
                alert.setHeaderText("Вы уверены, что хотите выйти?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        log.info("Приложение закрыто");
                        System.exit(0);
                    }
                    if (rs == ButtonType.CANCEL) {
                        log.info("Отмена закрытия приложения");
                        event.consume();
                    }
                });
            });

        } catch (Exception e) {
            log.error(e);
        }
    }
}
