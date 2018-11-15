package com.team22.Project_team_22_2018;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;


/**
 * @author Main
 */
public class Main extends Application {
    @NotNull
    private static final String APPLICATION_NAME = "team_22_project";

    public static void main(final String[] args) {
        launch(args);
    }

    //

    private static final int APPLICATION_WIDTH = 450;
    private static final int APPLICATION_HEIGHT = 335;

    @Override
    public void start(@NotNull final Stage primaryStage) throws IOException {
        URL url = Paths.get("src/main/resources/view/LoginForm.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        @NotNull final Scene scene = new Scene(root, APPLICATION_WIDTH, APPLICATION_HEIGHT);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}