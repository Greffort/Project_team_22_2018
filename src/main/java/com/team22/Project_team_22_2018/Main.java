package com.team22.Project_team_22_2018;

import com.team22.Project_team_22_2018.controller.Converter;
import com.team22.Project_team_22_2018.models.Account;
import com.team22.Project_team_22_2018.models.old.ManagerTask;
import com.team22.Project_team_22_2018.models.old.Task;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
//import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Greffort
 */

@Log4j
public class Main extends Application {

    @NotNull
    private static final String APPLICATION_NAME = "team_22_project";
    private Account data = RuntimeHolder.getModelHolder();

    public static void main(@NotNull final String[] args) {

//        final ManagerTaskConverter obj = new ManagerTaskConverter();

//        final Logger log = LoggerFactory.getLogger(Main.class);
//        log.error("ddfdf");

//        ManagerTask managerTask = RuntimeHolder.getModelHolder();
////        managerTask.addTask(new Task());
////        Task task3 = new Task("ЗАДАНИЕ  11111", LocalDate.of(2018, 12, 12), LocalDate.of(2017, 12, 12));
////        managerTask.addTask(task3);

//        OldController controller = RuntimeHolder.getControllerHolder();
//        Parent tree = new Tree(controller.getTasks());

//        try {
//            String s = "";
//            Converter.toJavaObject(s,Task.class);
//        } catch (IOException e) {
//            //запись в лог
//            log.error(e);
//        }


        launch(args);
    }

    @Override
    public void start(@NotNull final Stage primaryStage) throws IOException {
        @NotNull final Parent root = FXMLLoader.load(Resources.LOGIN_FORM);
        @NotNull final Scene scene = new Scene(root);
        primaryStage.minHeightProperty().setValue(600);
        primaryStage.minWidthProperty().setValue(1000);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}