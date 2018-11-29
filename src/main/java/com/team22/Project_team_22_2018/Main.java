package com.team22.Project_team_22_2018;

import com.team22.Project_team_22_2018.util.Resources;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

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
        URL a = Main.class.getResource("C:\\Users\\Aleks\\IdeaProjects\\Project_team_22_2018\\src\\main\\resources\\view\\MainForm.fxml");
        @NotNull final Parent root = FXMLLoader.load(Resources.LOGIN_FORM);
        @NotNull final Scene scene = new Scene(root);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();

//        Scene scene = new Scene(new Group(), 450, 250);
//        ObservableList<String> list = FXCollections.observableArrayList("1","2","3","4");
//        ComboBox<String> emailComboBox = new ComboBox<String>(list);
//
//        emailComboBox.setValue("A");
//        System.out.println(emailComboBox.getCellFactory());
//
//        GridPane grid = new GridPane();
//        grid.setVgap(4);
//        grid.setHgap(10);
////        grid.setPadding(new Insets(5, 5, 5, 5));
////        grid.add(new Label("To: "), 0, 0);
//        grid.add(emailComboBox, 1, 0);
//
//
//        Group root = (Group) scene.getRoot();
//        root.getChildren().add(grid);
////        Stage stage;
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }
}