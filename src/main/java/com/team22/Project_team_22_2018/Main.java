package com.team22.Project_team_22_2018;

import com.futur.common.helpers.resources.FXMLHelper;
import com.futur.common.log.LogInitializer;
import com.team22.Project_team_22_2018.service.ResourceService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;


/**
 * @author Main
 */
public class Main extends Application {
    @NotNull
    private static final String LOG_PROPERTY_NAME = "ISLogbackPath";
    @NotNull
    private static final String APPLICATION_NAME = "team_22_project";


    public static void main(final String[] args) {
        launch(args);
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("resources/loginForm.fxml"));
//        primaryStage.setTitle("ViewTask");
//        primaryStage.setScene(new Scene(root, 600, 402));
//        primaryStage.show();
//    }
//}


    private static final int APPLICATION_WIDTH = 600;
    private static final int APPLICATION_HEIGHT = 800;



    @Override
    public void start(@NotNull final Stage primaryStage) throws Exception {

        LogInitializer.initLogFileName(APPLICATION_NAME, LOG_PROPERTY_NAME);

        @NotNull final Parent root = FXMLHelper.loadNode(ResourceService.START_LAYOUT_FXML);
        @NotNull final Scene scene = new Scene(root, APPLICATION_WIDTH, APPLICATION_HEIGHT);

        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }}