package com.team22.Project_team_22_2018;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.models.manager.ManagerTask;
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
        ManagerTask mt = new ManagerTask();
//        ManagerTask mt2 = new ManagerTask();
//
//        ITask iTask1 = new BaseTask("ЗАДАНИЕ1", LocalDate.of(2018, 12, 12), LocalDate.of(2017, 12, 12));
//
//        SubTaskTask task3 = new SubTaskTask(new BaseTask("ЗАДАНИЕ  11111", LocalDate.of(2018, 12, 12), LocalDate.of(2017, 12, 12)));
//
//        task3.addSubTask(new SubTaskTask(new BaseTask("Подзадание 2222", LocalDate.of(2018, 12, 12), LocalDate.of(2017, 12, 12))),0);
//
//        SubTaskTask task1 = new SubTaskTask(new BaseTask("ЗАДАНИЕ  выаыаыв авацкё12 ", LocalDate.of(2018, 12, 12), LocalDate.of(2017, 12, 12)));
//
//        mt.addTask(task3);
//        mt.addTask(task3);
//        mt.addTask(task3);
//        mt.addTask(task3);
//        mt.addTask(task3);
//
//
//        mt2.addTask(task1);
//        mt2.addTask(task1);
//        mt2.addTask(task1);
//        mt2.addTask(task1);
//        mt2.addTask(task1);
//        System.out.println(task3.getDateClose());
//        System.out.println(task3.getName());

        Controller c = new Controller(mt);
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