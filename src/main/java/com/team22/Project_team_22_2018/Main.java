package com.team22.Project_team_22_2018;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.team22.Project_team_22_2018.util.Resources.LOGIN_FORM;

@Log4j
public class Main extends Application {

    @NotNull
    private static final String APPLICATION_NAME = "team_22_project";

    public static void main(@NotNull final String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull final Stage primaryStage) {
        try {
            assert LOGIN_FORM != null;
            @NotNull final Parent root = FXMLLoader.load(LOGIN_FORM);
            @NotNull final Scene scene = new Scene(root);
            primaryStage.minHeightProperty().setValue(600);
            primaryStage.minWidthProperty().setValue(1100);
            primaryStage.setWidth(1100);
            primaryStage.setHeight(600);
            primaryStage.setTitle(APPLICATION_NAME);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            log.error(e);
        }
    }
}