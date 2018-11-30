package com.team22.Project_team_22_2018;

import com.team22.Project_team_22_2018.util.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author Greffort
 */
public class Main extends Application {

    @NotNull
    private static final String APPLICATION_NAME = "team_22_project";

    public static void main(@NotNull final String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull final Stage primaryStage) throws IOException {
        @NotNull final Parent root = FXMLLoader.load(Resources.LOGIN_FORM);
        @NotNull final Scene scene = new Scene(root);
        primaryStage.minHeightProperty().setValue(600);
        primaryStage.minWidthProperty().setValue(880);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}