package com.team22.Project_team_22_2018.ui.controllers;

import com.team22.Project_team_22_2018.ui.rows.TaskRow;
import com.team22.Project_team_22_2018.models.task.ManagerTask;
import com.team22.Project_team_22_2018.models.task.Task;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.Util;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
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

    @Getter
    @Setter
    private BooleanProperty check = new SimpleBooleanProperty(false);

    @Getter
    @Setter
    private Boolean selected = new Boolean(true);

    @Getter
    @Setter
    CheckBoxTableCell checkBoxTableCell = new CheckBoxTableCell();

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
//            TableColumn<MainController,Boolean> checkBoxColumn;

    @FXML
    private void initialize() {
//        taskColumn.setCellValueFactory(new PropertyValueFactory<TaskRow, String>("name"));
//        deadLineColumn.setCellValueFactory(new PropertyValueFactory<TaskRow, String>("deadline"));
//        descriptionColumn.setCellValueFactory(new PropertyValueFactory<TaskRow, String>("description"));
//        daysBeforeDeadlineColumn.setCellValueFactory(new PropertyValueFactory<TaskRow, String>("restTime"));
//        statusColumn.setCellValueFactory(new PropertyValueFactory<TaskRow, String>("status"));
//        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<TaskRow, Boolean>("selected"));


//        checkBoxColumn.setCellFactory(
//                CheckBoxTableCell.forTableColumn(checkBoxColumn)
//        );


//        checkBoxColumn.getCellFactory(
//          CheckBoxTableCell.forTableColumn(checkBoxColumn, getSelected())
//        );
//        checkBoxColumn.setCellValueFactory(
//                new PropertyValueFactory()
//
//        );


        checkBoxColumn.setCellFactory(p -> {
                    CheckBoxTableCell cell = new CheckBoxTableCell();
                    cell.setAlignment(Pos.CENTER_RIGHT);
                    return cell;
                }
        );

        checkBoxColumn.setCellValueFactory(cellData -> {
            TaskRow cellValue = cellData.getValue();
            BooleanProperty property = cellValue.selectedProperty();

            // Add listener to handler change
            property.addListener((observable, oldValue, newValue) -> cellValue.setSelected(newValue));

            return property;
        });

        tableView.setEditable(true);

        //редактирование столбца заданий
        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        taskColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setName(t.getNewValue());
                });

        //редактирование столбца дедлайнов
        deadLineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        deadLineColumn.setOnEditCommit(
                (
                        TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDeadline(t.getNewValue());
                });

        //редактирование столбца описания
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(
                (
                        TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDescription(t.getNewValue());
                });

        //редактирование столбца описания
        daysBeforeDeadlineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        daysBeforeDeadlineColumn.setOnEditCommit(
                (
                        TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setRestTime(t.getNewValue());
                });

        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setOnEditCommit(
                (
                        TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDescription(t.getNewValue());
                });


//        checkBoxColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        checkBoxColumn.setOnEditCommit(
//                (
//                        TableColumn.CellEditEvent<MainController, Boolean> t) -> {
//                    t.getTableView().getItems().get(
//                            t.getTablePosition().getRow()).setCheckBoxTableCell(this.checkBoxTableCell.);//setS(t.getNewValue());
//                });
    }

    @Getter
    @Setter
    CheckBoxTableCell checkBoxTableCell1 = new CheckBoxTableCell();

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
//        val row = tableView.getSelectionModel().getSelectedIndex();
//        if (0 <= row) {
//            tableView.getItems().remove(row);
//        }
    }

    @FXML
    private void buttonEditTask() {
        System.out.println("edit");
    }

    @FXML
    private void saveAction() throws Exception {

//        val fileChooser = new FileChooser();
//        val file = fileChooser.showSaveDialog(null);
//        if (file != null) {
//            Util.writeTasks(new ManagerTask(new ArrayList<>(tableView.getItems())), file.getPath());
//        }

//        ObservableList<TaskRow> items = tableView.getItems();
//        List<Task> list = new ArrayList<>();
//        for (TaskRow item : items) {
//            list.add(item.toTask());
//        }

        List<Task> collect = tableView.getItems().stream().map(TaskRow::toTask).collect(Collectors.toList());

        Util.writeTasks(new ManagerTask(collect), Resources.LOCAL_SAVE.getPath());

    }

    @FXML
    private void loadAction() throws IOException, ClassNotFoundException {
//        val fileChooser = new FileChooser();
//        val file = fileChooser.showOpenDialog(null);
//        if (file != null) {
//            ManagerTask managerTask = Util.readTasks(file.getPath());
//            tableView.getItems().setAll(managerTask.getTasks());
//        }



        ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
        final List<TaskRow> collect = managerTask.getTasks().stream().map(TaskRow::new).collect(Collectors.toList());
        tableView.getItems().setAll(collect);
    }

//    @FXML
//    private void closeAction() {
//        System.exit(0);
//    }

    @FXML
    private void helpAction() throws IOException {
        Parent root = FXMLLoader.load(Resources.HELP_FORM);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Нelp");
        stage.setScene(scene);
        stage.show();
    }
//
//    private int getNumberOfVisibleRows() {
//        VirtualFlow<?> vf = loadVirtualFlow();
//        return vf.getLastVisibleCell().getIndex() - vf.getFirstVisibleCell().getIndex();
//    }
//
//
//    private VirtualFlow<?> loadVirtualFlow() {
//        return (VirtualFlow<?>) ((TableViewSkin<?>) tableView.getSkin()).getChildren().get(1);
//    }

//    public class CheckBoxTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
//        public TableCell<S, T> call(TableColumn<S, T> param) {
//            return new CheckBoxTableCell<S, T>();
//        }
//    }
}



