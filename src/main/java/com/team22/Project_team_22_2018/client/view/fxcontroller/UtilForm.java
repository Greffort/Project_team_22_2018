package com.team22.project_team_22_2018.client.view.fxcontroller;

import com.team22.project_team_22_2018.client.view.util.model.MyObject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.team22.project_team_22_2018.util.Resources.NOTIFICATION;

@Log4j
public class UtilForm {

    public static void showNotification(@NotNull final MyObject myObject) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NOTIFICATION);
            Parent root = loader.load();
            @NotNull NotificationFormController notificationFormController = loader.getController();
            notificationFormController.setMyObject(myObject);
            val scene = new Scene(root);
            Stage stage = new Stage();
            stage.minHeightProperty().setValue(375);
            stage.minWidthProperty().setValue(325);
            stage.setHeight(375);
            stage.setWidth(325);
            stage.maxHeightProperty().setValue(375);
            stage.maxWidthProperty().setValue(325);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> event.consume());
        } catch (IOException e) {
            log.error(e);
        }
    }

    public static void showAlertInformation(String title, String headerText, String logg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                log.info(logg);
            }
        });
    }
}
