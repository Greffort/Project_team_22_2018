package com.team22.Project_team_22_2018.view.fxcontrollers;

import com.team22.Project_team_22_2018.controller.IController;
import com.team22.Project_team_22_2018.models.IObservable;
import com.team22.Project_team_22_2018.models.manager.IManagerTask;
import com.team22.Project_team_22_2018.models.manager.ManagerTask;
import com.team22.Project_team_22_2018.models.task.ITask;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.Util;
import com.team22.Project_team_22_2018.view.IObserver;
import com.team22.Project_team_22_2018.view.IView;
import com.team22.Project_team_22_2018.view.TaskRow;
import javafx.beans.property.BooleanProperty;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.val;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Greffort
 * <p>
 * Определяет поведние входной формы "LoginForm"
 */
//@Slf4j
@NoArgsConstructor
public class MainController implements IView, IObserver {

    IController controller;
    IManagerTask managerTask;


    @FXML
    private TableView<TaskRow> tableView;
    @FXML
    private TableColumn<TaskRow, String> taskColumn;
    @FXML
    private TableColumn<TaskRow, DateFormat> deadLineColumn;
    @FXML
    private TableColumn<TaskRow, String> descriptionColumn;
    @FXML
    private TableColumn<TaskRow, DateFormat> daysBeforeDeadlineColumn;
    @FXML
    private TableColumn<TaskRow, String> statusColumn;
    @FXML
    private TableColumn<TaskRow, Boolean> checkBoxColumn;

    public MainController(IController controller, IManagerTask managerTask) {
        this.controller = controller;
        this.managerTask = managerTask;
        ((IObservable) this.managerTask).registerObserver(this);//поставили форму слушать модель
    }

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
            property.addListener((observable, Value, newValue) -> cellValue.setSelected(newValue));

            return property;
        });

        tableView.setEditable(true);

        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        taskColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setName(t.getNewValue());
                });

//        deadLineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        deadLineColumn.setOnEditCommit(
//                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
//                    t.getTableView().getItems().get(
//                            t.getTablePosition().getRow()).setDeadline(t.getNewValue());
//                });

        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDescription(t.getNewValue());
                });

//        daysBeforeDeadlineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        daysBeforeDeadlineColumn.setOnEditCommit(
//                (TableColumn.CellEditEvent<TaskRow, String> t) -> {
//                    t.getTableView().getItems().get(
//                            t.getTablePosition().getRow()).setDayBeforeDeadline(t.getNewValue());
//                });

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
        ObservableList<TaskRow> items = tableView.getItems();
        List<ITask> list = new ArrayList<>();
        for (TaskRow item : items) {
            list.add(item.toTask());
        }

        List<ITask> collect = tableView.getItems().stream().map(TaskRow::toTask).collect(Collectors.toList());

        Util.writeTasks(new ManagerTask(collect), Resources.LOCAL_SAVE.getPath());

    }

    @FXML
    public void saveAsAction() throws IOException {
        val fileChooser = new FileChooser();
        val file = fileChooser.showSaveDialog(null);
        List<ITask> collect = tableView.getItems().stream().map(TaskRow::toTask).collect(Collectors.toList());
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

    @Override
    public void handleEvent() {

        //здесь нужно получить данные не напрямую из модели а через стринг или json?
        tableView.getItems().addAll((ObservableList) managerTask.getTasks());


        //отображение в tableView новых двнных
        //нужно получить данные из модели и обновить таблицу
        //для жтого нужно стать слушателем модели
    }
}



