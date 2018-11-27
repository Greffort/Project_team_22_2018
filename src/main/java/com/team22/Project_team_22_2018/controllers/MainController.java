package com.team22.Project_team_22_2018.controllers;

import com.team22.Project_team_22_2018.task.ManagerTask;
import com.team22.Project_team_22_2018.task.Task;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.val;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Greffort
 * <p>
 * Определяет поведние входной формы "LoginForm"
 */
//@Slf4j
public class MainController {

    @FXML
    private TableView<Task> tableViewTask;

    @FXML
    private TableColumn<Task, String> columnTask;
    @FXML
    private TableColumn<Task, String> columnDeadLine;
    @FXML
    private TableColumn<Task, String> columnDescription;


    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        columnTask.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        columnDeadLine.setCellValueFactory(new PropertyValueFactory<Task, String>("deadLine"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));


        tableViewTask.setEditable(true);

        //редактирование столбца заданий
        columnTask.setCellFactory(TextFieldTableCell.<Task>forTableColumn());
        columnTask.setOnEditCommit(
                (TableColumn.CellEditEvent<Task, String> t) -> {
                    ((Task) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });

        //редактирование столбца дедлайнов
        columnDeadLine.setCellFactory(TextFieldTableCell.<Task>forTableColumn());
        columnDeadLine.setOnEditCommit(
                (
                        TableColumn.CellEditEvent<Task, String> t) -> {
                    ((Task) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDeadLine(t.getNewValue());
                });

        //редактирование столбца описания
        columnDescription.setCellFactory(TextFieldTableCell.<Task>forTableColumn());
        columnDescription.setOnEditCommit(
                (
                        TableColumn.CellEditEvent<Task, String> t) -> {
                    ((Task) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDescription(t.getNewValue());
                });

    }

    @FXML
    private void buttonAddTask() throws Exception {
        FXMLLoader loader = new FXMLLoader(Resources.CREATE_TASK_FORM);
        final Parent root = loader.load();
        final CreateTaskFormController controller = loader.getController();
        controller.setTasks(tableViewTask.getItems());
        val scene = new Scene(root);
        val stage = new Stage();
        stage.setTitle("Windows add");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void buttonRemoteTask() {
        val row = tableViewTask.getSelectionModel().getSelectedIndex();
        if (0 <= row) {
            tableViewTask.getItems().remove(row);
        }
    }

    @FXML
    private void buttonEditTask() {
        System.out.println("edit");
    }

    @FXML
    private void saveAction() throws Exception {
        val fileChooser = new FileChooser();
        val file = fileChooser.showSaveDialog(null);
        if (file != null) {
            Util.writeTasks(new ManagerTask(new ArrayList<>(tableViewTask.getItems())), file.getPath());
        }
    }


    @FXML
    private void loadAction() throws IOException, ClassNotFoundException {
        val fileChooser = new FileChooser();
        val file = fileChooser.showOpenDialog(null);
        if (file != null) {
            ManagerTask managerTask = Util.readTasks(file.getPath());
            tableViewTask.getItems().setAll(managerTask.getTasks());
        }
    }

    @FXML
    private void closeAction() throws IOException {
        System.exit(0);
    }

    @FXML
    private void onHelpAction() throws IOException {
        Parent root = FXMLLoader.load(Resources.HELP_FORM);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Нelp");
        stage.setScene(scene);
        stage.show();
    }
}



