package com.team22.project_team_22_2018.client;

import com.team22.project_team_22_2018.client.controller.ControllerView;
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
public class StartApp extends Application {
    @NotNull
    private static final String APPLICATION_NAME = "team_22_project";

    @NotNull
//    private ControllerView controllerView = ClientRuntimeHolder.getControllerViewHolder();

    public static void main(@NotNull final String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull final Stage primaryStage) {
        try {
            assert LOGIN != null;
            @NotNull final Parent root = FXMLLoader.load(LOGIN);
            @NotNull final Scene scene = new Scene(root);
            primaryStage.minHeightProperty().setValue(209);
            primaryStage.minWidthProperty().setValue(159);
            primaryStage.setHeight(209);
            primaryStage.setWidth(159);
            primaryStage.maxHeightProperty().setValue(209);
            primaryStage.maxWidthProperty().setValue(159);

            primaryStage.setTitle(APPLICATION_NAME);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Project team 22");
                alert.setHeaderText("Вы уверены, что хотите выйти?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
//                        controllerView.close();
                        log.info("Приложение закрыто");
                        System.exit(0);
                    }
                });
            });
            Thread thread = new Thread(ClientRuntimeHolder.getControllerViewHolder());
            thread.setDaemon(true);
//            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        } catch (Exception e) {
            log.error(e);
        }

    }
}
