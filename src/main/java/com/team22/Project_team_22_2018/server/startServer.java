package com.team22.project_team_22_2018.server;

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
import static com.team22.project_team_22_2018.util.Resources.SSERVER_FORM;


@Log4j
public class StartServer extends Application {
    @NotNull
    private static final String APPLICATION_NAME = "team_22_project";

    public static void main(@NotNull final String[] args) {
        launch(args);

    }

    @Override
    public void start(@NotNull final Stage primaryStage) {
        try {
            assert SSERVER_FORM != null;
            @NotNull final Parent root = FXMLLoader.load(SSERVER_FORM);
            @NotNull final Scene scene = new Scene(root);
            primaryStage.minHeightProperty().setValue(700);
            primaryStage.minWidthProperty().setValue(700);
            primaryStage.setHeight(700);
            primaryStage.setWidth(700);
            primaryStage.maxHeightProperty().setValue(700);
            primaryStage.maxWidthProperty().setValue(700);

            primaryStage.setTitle(APPLICATION_NAME);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            log.error(e);
        }

    }
}
