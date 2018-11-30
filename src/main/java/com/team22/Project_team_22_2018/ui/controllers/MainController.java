package com.team22.Project_team_22_2018.ui.controllers;

import com.team22.Project_team_22_2018.models.task.ManagerTask;
import com.team22.Project_team_22_2018.models.task.Task;
import com.team22.Project_team_22_2018.ui.rows.TaskRow;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.Util;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Greffort
 * <p>
 * Определяет поведние входной формы "LoginForm"
 */
//@Slf4j
public class MainController {

    @FXML
    private TableView<TaskRow> tableView;
    @FXML
    private TableColumn<TaskRow, String> taskColumn;
    @FXML
    private TableColumn<TaskRow, String> deadLineColumn;
    @FXML
    private TableColumn<TaskRow, String> descriptionColumn;
    @FXML
    private TableColumn<TaskRow, String> daysBeforeDeadlineColumn;
    @FXML
    private TableColumn<TaskRow, String> statusColumn;
    @FXML
    private TableColumn<TaskRow, Boolean> checkBoxColumn;
    @FXML
    private VBox vBoxSplitPane;
    @FXML
    private SplitPane splitPane;

    @FXML
    private void initialize() {
        checkBoxColumn.setCellFactory(p -> {
                    CheckBoxTableCell cell = new CheckBoxTableCell();
                    cell.setAlignment(Pos.CENTER_RIGHT);
                    return cell;
                }
        );

        checkBoxColumn.setCellValueFactory(cellData -> {
            TaskRow cellValue = cellData.getValue();
            BooleanProperty property = cellValue.selectedProperty();
            property.addListener((observable, oldValue, newValue) -> cellValue.setSelected(newValue));

            return property;
        });

        tableView.setEditable(true);

        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        taskColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setName(t.getNewValue());
                });

        deadLineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        deadLineColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDeadline(t.getNewValue());
                });

        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDescription(t.getNewValue());
                });

        daysBeforeDeadlineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        daysBeforeDeadlineColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setRestTime(t.getNewValue());
                });

        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDescription(t.getNewValue());
                });
    }

    @FXML
    private void buttonAddTask() throws Exception {
        FXMLLoader loader = new FXMLLoader(Resources.CREATE_TASK_FORM);
        final Parent root = loader.load();
        final CreateTaskFormController controller = loader.getController();
        controller.setTasks(tableView.getItems());
        val scene = new Scene(root);
        val stage = new Stage();
        stage.setTitle("Windows add");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void buttonRemoteTask() {
        //List<TaskRow> collect = items.stream().filter(TaskRow::isSelected).collect(Collectors.toList());

        final ObservableList<TaskRow> items = tableView.getItems();
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).isSelected()) {
                items.remove(i);
            }
        }
        //удаление выделенного элемента
        /*val row = tableView.getSelectionModel().getSelectedIndex();
        if (0 <= row) {
            tableView.getItems().remove(row);
        }*/
    }

    @FXML
    private void buttonEditTask() {
        System.out.println("edit");
    }

    @FXML
    private void saveAction() throws Exception {
//        ObservableList<TaskRow> items = tableView.getItems();
//        List<Task> list = new ArrayList<>();
//        for (TaskRow item : items) {
//            list.add(item.toTask());
//        }

//        if ()
        List<Task> collect = tableView.getItems().stream().map(TaskRow::toTask).collect(Collectors.toList());

        Util.writeTasks(new ManagerTask(collect), Resources.LOCAL_SAVE.getPath());

    }

    @FXML
    public void saveAsAction() throws IOException {
        val fileChooser = new FileChooser();
        val file = fileChooser.showSaveDialog(null);


//        Resources.SAVE = file.toPath();
        List<Task> collect = tableView.getItems().stream().map(TaskRow::toTask).collect(Collectors.toList());
        if (file != null) {
            Util.writeTasks(new ManagerTask(collect), file.getPath());
        }
    }

    @FXML
    private void loadAction() throws IOException, ClassNotFoundException {
        ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
        final List<TaskRow> collect = managerTask.getTasks().stream().map(TaskRow::new).collect(Collectors.toList());
        tableView.getItems().setAll(collect);
    }

    @FXML
    public void loadFromAction() throws IOException, ClassNotFoundException {
        val fileChooser = new FileChooser();
        val file = fileChooser.showOpenDialog(null);
        if (file != null) {
            ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
            final List<TaskRow> collect = managerTask.getTasks().stream().map(TaskRow::new).collect(Collectors.toList());
            tableView.getItems().setAll(collect);
        }
    }

    @FXML
    private void helpAction() throws IOException {
        Parent root = FXMLLoader.load(Resources.HELP_FORM);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Нelp");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    //добавить диалоговое окно "Вы уверенны что хотите выйти?"
    public void closeAction() {
        System.exit(0);
    }

}



