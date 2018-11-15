package com.team22.Project_team_22_2018.controllers;

import com.team22.Project_team_22_2018.task.ManagerTask;
import com.team22.Project_team_22_2018.task.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author ControllerLoginForm
 */
public class ControllerLoginForm {

    ManagerTask managerTask = new ManagerTask();

    @FXML
    private TableView<Task> tableViewTask;

    @FXML
    private TableColumn<Task, String> columnTask;
    @FXML
    private TableColumn<Task, String> columnDeadLine;
    @FXML
    private TableColumn<Task, String> columnDescription;

    public void buttonAddTask(ActionEvent actionEvent) throws IOException {

        managerTask.addTask(new Task()); //для проверки

        Stage stage = new Stage();

        URL url = Paths.get("./src/main/resources/view/LoginForm.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Windows add");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void buttonRemoteTask(ActionEvent actionEvent) {
        System.out.println("remote");
    }

    public void buttonEditTask(ActionEvent actionEvent) {
        System.out.println("edit");
    }

    @FXML
    private void initialize() {
        //удалить
        managerTask.addTask(new Task("Task1", "qwerty", "data"));
        managerTask.addTask(new Task("Task2", "dsfsdfw", "data"));
        managerTask.addTask(new Task("Task3", "dsfdsfwe", "data"));
        managerTask.addTask(new Task("Task4", "iueern", "data"));
        managerTask.addTask(new Task("Task5", "woeirn", "data"));
        //
        columnTask.setCellValueFactory(new PropertyValueFactory<Task, String>("nameTask"));
        columnDeadLine.setCellValueFactory(new PropertyValueFactory<Task, String>("deadLineTask"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<Task, String>("descriptionTask"));
        tableViewTask.setItems(managerTask.getTasks());
    }
}
