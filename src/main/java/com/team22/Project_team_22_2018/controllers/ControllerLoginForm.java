package com.team22.Project_team_22_2018.controllers;

import com.team22.Project_team_22_2018.task.ManagerTask;
import com.team22.Project_team_22_2018.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.TestOnly;

import java.net.URL;
import java.nio.file.Paths;

/**
 * @author ControllerLoginForm
 *
 * Определяет поведние входной формы "LoginForm"
 */
public class ControllerLoginForm extends MainController {

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    ManagerTask mangerTask = new ManagerTask();

    @FXML
    private TableView<Task> tableViewTask;

    @FXML
    private TableColumn<Task, String> columnTask;
    @FXML
    private TableColumn<Task, String> columnDeadLine;
    @FXML
    private TableColumn<Task, String> columnDescription;

    @FXML
    private MenuBar loginFormMenuBar;

    @FXML
    private MenuItem menuItemHelp;

    public void buttonAddTask(ActionEvent actionEvent) throws Exception {
        URL url = Paths.get("src/main/resources/view/CreateTaskForm.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Windows add");
        stage.setScene(scene);
        stage.show();
    }

    public void buttonRemoteTask(ActionEvent actionEvent) {

        int row = tableViewTask.getSelectionModel().getSelectedIndex();
        tableViewTask.getItems().remove(row);
    }

    public void buttonEditTask(ActionEvent actionEvent) {
        System.out.println("edit");
    }

    @FXML
    private void initialize() {
        tasks.addAll(getManagerTask().getTasks());

        columnTask.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        columnDeadLine.setCellValueFactory(new PropertyValueFactory<Task, String>("deadLine"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        tableViewTask.setItems(tasks);
//        menuItemHelp.setAccelerator();

        menuItemHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    URL url = Paths.get("src/main/resources/view/HelpForm.fxml").toUri().toURL();
                    Parent root = FXMLLoader.load(url);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
//                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Windows help");
                    stage.setScene(scene);
                    stage.show();
                }
                catch (Exception e){

                }

            }
        });
    }



    // When user click on the Exit item.

}
