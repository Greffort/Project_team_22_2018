package com.team22.Project_team_22_2018;

import com.team22.Project_team_22_2018.models.ManagerTask;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
//import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Greffort
 */
public class Main extends Application {

    @NotNull
    private static final String APPLICATION_NAME = "team_22_project";
    private ManagerTask data = RuntimeHolder.getModelHolder();

    public static void main(@NotNull final String[] args) {

//        final ManagerTaskConverter obj = new ManagerTaskConverter();

//        final Logger log = LoggerFactory.getLogger(Main.class);
//        log.error("ddfdf");

//        obj.addTask(new SessionDataTask());
//        SessionDataTask task3 = new SessionDataTask("ЗАДАНИЕ  11111", LocalDate.of(2018, 12, 12), LocalDate.of(2017, 12, 12));
//        obj.addTask(task3);
//        ManagerTaskConverter managerTask;
//        try {
//            final String s = Converter.toJSON(obj);
//            System.out.println(s);
//            managerTask = Converter.toJavaObject(s,ManagerTaskConverter.class);
//
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }

//        ManagerTaskConverter mt = new ManagerTaskConverter();
//        Controller c = new Controller(mt);
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