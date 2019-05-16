package com.team22.project_team_22_2018.client;

import com.team22.project_team_22_2018.client.util.ClientRuntimeHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;

import static com.team22.project_team_22_2018.util.Resources.LOGIN;

@Log4j
public class StartClient extends Application {
    @NotNull
    private static final String APPLICATION_NAME = "team_22_project_client";

    public static void main(@NotNull final String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull final Stage primaryStage) {
        try {
            assert LOGIN != null;
            @NotNull final Parent root = FXMLLoader.load(LOGIN);
            @NotNull final Scene scene = new Scene(root);
            primaryStage.minHeightProperty().setValue(250);
            primaryStage.minWidthProperty().setValue(475);
            primaryStage.setHeight(250);
            primaryStage.setWidth(475);
            primaryStage.maxHeightProperty().setValue(250);
            primaryStage.maxWidthProperty().setValue(475);

            primaryStage.setTitle(APPLICATION_NAME);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Project team 22 - Client");
                alert.setHeaderText("Вы уверены, что хотите выйти?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        log.info("Приложение закрыто");
                        System.exit(0);
                    } else {
                        event.consume();
                    }
                });
            });
            Thread thread = new Thread(ClientRuntimeHolder.getControllerViewHolder());
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
