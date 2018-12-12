package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.controller.Converter;
import com.team22.Project_team_22_2018.models.ManagerTask;
import com.team22.Project_team_22_2018.models.Observable;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import com.team22.Project_team_22_2018.view.session_data.NumberTableCellFactory;
import com.team22.Project_team_22_2018.view.session_data.SessionDataTask;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;


/**
 * @author Greffort
 * <p>
 * Определяет поведние входной формы "LoginForm"
 */
@Slf4j
public class MainController implements Observer {

    ManagerTask managerTask = RuntimeHolder.getModelHolder();
    Controller controller = RuntimeHolder.getControllerHolder();

    //    @FXML
//    private TableView<SessionDataTask> tasks_TableView;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<SessionDataTask, String> taskColumn;
    @FXML
    private TableColumn<SessionDataTask, String> deadLineColumn;
//    @FXML
//    private TableColumn<SessionDataTask, String> daysBeforeDeadlineColumn;
    @FXML
    private TableColumn<SessionDataTask, String> statusColumn;
    @FXML
    private TableColumn<SessionDataTask, String> descriptionColumn;
    @FXML
    private TableColumn<SessionDataTask, Boolean> checkBoxColumn;
    @FXML
    private TableColumn numberingColumn;

    private SimpleBooleanProperty selected = new SimpleBooleanProperty();

//    @FXML private TableColumn numberingColumn;


    public MainController() {
        ((Observable) managerTask).registerObserver(this);
    }

    @FXML
    private void initialize() {
//        ((Observable)managerTask).registerObserver(this);
        tableView.setEditable(true);

        taskColumn.setCellValueFactory(new PropertyValueFactory<SessionDataTask, String>("name"));
        deadLineColumn.setCellValueFactory(new PropertyValueFactory<SessionDataTask, String>("deadline"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<SessionDataTask, String>("dateClose"));
//        daysBeforeDeadlineColumn.setCellValueFactory(new PropertyValueFactory<SessionDataTask, String>("dateOpen"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<SessionDataTask, String>("status"));

        numberingColumn.setCellFactory(new NumberTableCellFactory<>());

        checkBoxColumn.setCellFactory(p -> {
                    CheckBoxTableCell cell = new CheckBoxTableCell();
                    cell.setAlignment(Pos.CENTER_RIGHT);
                    return cell;
                }
        );


        checkBoxColumn.setCellValueFactory(cellData -> {
            SessionDataTask cellValue = cellData.getValue();
            BooleanProperty property = cellValue.selectedProperty();
            property.addListener((observable, Value, newValue) -> cellValue.setSelected(newValue));
            return property;
        });


        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        taskColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<SessionDataTask, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setName(t.getNewValue());
controller.setTasks(tableView.getItems());
                });

        deadLineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        deadLineColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<SessionDataTask, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDeadline(t.getNewValue());
                    controller.setTasks(tableView.getItems());
                });

        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<SessionDataTask, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDescription(t.getNewValue());
                    controller.setTasks(tableView.getItems());
//                    controller.setTask(0, t.getTablePosition());
                });

//        daysBeforeDeadlineColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        daysBeforeDeadlineColumn.setOnEditCommit(
//                (TableColumn.CellEditEvent<SessionDataTask, String> t) -> {
//                    t.getTableView().getItems().get(
//                            t.getTablePosition().getRow()).setRestTime(t.getNewValue());
//                    controller.setTasks(tableView.getItems());
//                });

        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<SessionDataTask, String> t) -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setDescription(t.getNewValue());
                    controller.setTasks(tableView.getItems());
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
        stage.minHeightProperty().setValue(200);
        stage.minWidthProperty().setValue(300);
        stage.setTitle("Windows add");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void buttonRemoteTask() {
        //List<SessionDataTask> collect = items.stream().filter(SessionDataTask::isSelected).collect(Collectors.toList());

        final ObservableList<SessionDataTask> items = tableView.getItems();
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).isSelected()) {
                items.remove(i);
                controller.removeTask(i);
            }
        }
        try {
            System.out.println(Converter.toJSON(RuntimeHolder.getModelHolder()));
        } catch (IOException e) {

        }
//        final ObservableList<SessionDataTask> items = tableView.getItems();
//        for (int i = tableView.getItems().size() - 1; i >= 0; i--) {
//            if (tableView.getItems().get(i).isSelected()) {
//                tableView.getItems().remove(i);
//                controller.removeTask(i);
//            }
//        }


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
//        ObservableList<SessionDataTask> items = tableView.getItems();
//        List<ITask> list = new ArrayList<>();
//        for (SessionDataTask item : items) {
//            list.add(item.toTask());
//        }

//        List<BaseTask> collect = tableView.getItems().stream().map(SessionDataTask::toTask).collect(Collectors.toList());
//
//        Util.writeTasks(new com.team22.Project_team_22_2018.models.ManagerTask(collect), Resources.LOCAL_SAVE.getPath());
    }

    @FXML
    public void saveAsAction() throws IOException {
        val fileChooser = new FileChooser();
        val file = fileChooser.showSaveDialog(null);
//        List<BaseTask> collect = tableView.getItems().stream().map(SessionDataTask::toTask).collect(Collectors.toList());
        if (file != null) {
//            Util.writeTasks(new com.team22.Project_team_22_2018.models.ManagerTask(collect), file.getPath());
        }
    }

    @FXML
    private void loadAction() throws IOException, ClassNotFoundException {
//        com.team22.Project_team_22_2018.models.ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
//        final List<SessionDataTask> collect = managerTask.getTasks().stream().map(SessionDataTask::new).collect(Collectors.toList());
//        tableView.getItems().setAll(collect);
    }

    @FXML
    public void loadFromAction() throws IOException, ClassNotFoundException {
//        val fileChooser = new FileChooser();
//        val file = fileChooser.showOpenDialog(null);
//        if (file != null) {
//            com.team22.Project_team_22_2018.models.ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
//            final List<SessionDataTask> collect = managerTask.getTasks().stream().map(SessionDataTask::new).collect(Collectors.toList());
//            tableView.getItems().setAll(collect);
//        }
    }

    /*
     * BUG
     * Если окно уже открыто, то не открывать его снова
     */
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
    /*
     * BUG
     * добавить диалоговое окно "Вы уверенны что хотите выйти?"
     */
    public void closeAction() {
        System.exit(0);
    }

    @Override
    public void handleEvent() {
        //здесь нужно вывести графическое обновление данных форы, тобишь запрос к контроллеру на новые данные

        tableView.setItems(controller.getTasks());

    }
}



