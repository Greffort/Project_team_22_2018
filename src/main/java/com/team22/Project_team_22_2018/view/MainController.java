package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.controller.Converter;
import com.team22.Project_team_22_2018.models.ManagerTask;
import com.team22.Project_team_22_2018.models.Observable;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import com.team22.Project_team_22_2018.util.Tree;
import com.team22.Project_team_22_2018.view.session_data.NumberTableCellFactory;
import com.team22.Project_team_22_2018.view.session_data.SessionDataManagerTask;
import com.team22.Project_team_22_2018.view.session_data.SessionDataTask;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


/**
 * @author Greffort
 * <p>
 * Определяет поведние входной формы "LoginForm"
 */
@Slf4j
public class MainController implements Observer {

    private ManagerTask managerTask = RuntimeHolder.getModelHolder();
    private Controller controller = RuntimeHolder.getControllerHolder();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
    private StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };
    private ObservableList<String> langs = FXCollections.observableArrayList("IN_PROGRESS", "CLOSE", "OPEN", "WAITING", "TERMINATED");

    @FXML
    private TreeView tree;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<SessionDataTask, String> taskColumn;
    @FXML
    private TableColumn<SessionDataTask, String> deadLineColumn;
    @FXML
    private TableColumn<SessionDataTask, String> statusColumn;
    @FXML
    private TableColumn<SessionDataTask, String> descriptionColumn;
    @FXML
    private TableColumn<SessionDataTask, Boolean> checkBoxColumn;
    @FXML
    private TableColumn numberingColumn;

    @FXML
    private TextField textTask;
    @FXML
    private TextArea textDescription;
    @FXML
    private DatePicker dataPicDeadline;
    @FXML
    private DatePicker dataPicDateOpen;
    @FXML
    private DatePicker dataPicDateClose;
    @FXML
    private ComboBox comboBoxStatus;
    @FXML
    private ComboBox comboBoxTeg;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button editTask;
    @FXML
    private Button saveEditTask;
    @FXML
    private FontAwesomeIconView hidePanelOpen;
//    @FXML
//    private FontAwesomeIconView hidePanelClose;


    @FXML
    private MenuItem menuItemHelp;
    @FXML
    private VBox vBoxSplitPane2;


    public MainController() {
        ((Observable) managerTask).registerObserver(this);
    }

    @FXML
    private void initialize() {
        tableView.setEditable(true);

        //right Vbox
        buttonHidePanelClose();
        setEditPane(true, 0.9);
        dataPicDeadline.setConverter(converter); //dataPicDeadline.setPromptText("dd-MM-yyyy");
        dataPicDateOpen.setConverter(converter);
        dataPicDateClose.setConverter(converter);

        //TableView
        handleEvent();
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deadLineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        numberingColumn.setCellFactory(new NumberTableCellFactory<>());
        checkBoxColumn.setCellFactory(p -> {
            CheckBoxTableCell cell = new CheckBoxTableCell();
            cell.setAlignment(Pos.CENTER_RIGHT);
            return cell;
        });
        checkBoxColumn.setCellValueFactory(cellData -> {
            SessionDataTask cellValue = cellData.getValue();
            BooleanProperty property = cellValue.selectedProperty();
            property.addListener((observable, Value, newValue) -> cellValue.setSelected(newValue));
            return property;
        });
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                val row = tableView.getSelectionModel().getSelectedIndex();
                if (0 <= row) {
                    tableView.getItems().get(row);
                }
                SessionDataTask task = (SessionDataTask) tableView.getSelectionModel().getSelectedItem();
                updateTaskInfo(task);
                buttonHidePanelOpen();
            } else {
                updateTaskInfo(null);
            }
        });

        //TreeView

//        TreeItem root = new TreeItem(controller.getTasks());
//        root.setExpanded(true);


//        Parent tree = new Tree(controller.getTasks());
////        this.tree = tree;

    }

    @FXML
    /*
     * BUG
     * Если окно уже открыто, то не открывать его снова, но и не блокировать остальные окна
     * Добавить запись в лог
     */
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
    /*
     * Добавить запись в лог
     */
    private void buttonRemoteTask() {
        //List<Task> collect = items.stream().filter(Task::isSelected).collect(Collectors.toList());

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
//запись в лог
        }
        //удаление выделенного элемента
//        val row = tableView.getSelectionModel().getSelectedIndex();
//        if (0 <= row) {
//            tableView.getItems().remove(row);
//        }
    }

    @FXML
    /*
     * BUG
     * Добавить обработку исключений+
     * Сделать сохранение в XML формат
     * Добавить запись в лог
     */
    private void saveAction() throws Exception {
//        ObservableList<Task> items = tableView.getItems();
//        List<ITask> list = new ArrayList<>();
//        for (Task item : items) {
//            list.add(item.toTask());
//        }

//        List<Task> collect = tableView.getItems().stream().map(Task::toTask).collect(Collectors.toList());
//
//        Util.writeTasks(new com.team22.Project_team_22_2018.models.ManagerTask(collect), Resources.LOCAL_SAVE.getPath());
    }

    @FXML
    /*
     * BUG
     * Добавить обработку исключений
     * Сделать сохранение в XML формат
     * Добавить запись в лог
     */
    public void saveAsAction() throws IOException {
        val fileChooser = new FileChooser();
        val file = fileChooser.showSaveDialog(null);
//        List<Task> collect = tableView.getItems().stream().map(Task::toTask).collect(Collectors.toList());
        if (file != null) {
//            Util.writeTasks(new com.team22.Project_team_22_2018.models.ManagerTask(collect), file.getPath());
        }
    }

    @FXML
    /*
     * BUG
     * Добавить обработку исключений
     * Загружать из XML с проверкой
     * Добавить запись в лог
     */
    private void loadAction() throws IOException, ClassNotFoundException {
//        com.team22.Project_team_22_2018.models.ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
//        final List<Task> collect = managerTask.getTasks().stream().map(Task::new).collect(Collectors.toList());
//        tableView.getItems().setAll(collect);
    }

    @FXML
    /*
     * BUG
     * Добавить обработку исключений
     * Загружать из XML с проверкой
     * Добавить запись в лог
     */
    public void loadFromAction() throws IOException, ClassNotFoundException {
//        val fileChooser = new FileChooser();
//        val file = fileChooser.showOpenDialog(null);
//        if (file != null) {
//            com.team22.Project_team_22_2018.models.ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
//            final List<Task> collect = managerTask.getTasks().stream().map(Task::new).collect(Collectors.toList());
//            tableView.getItems().setAll(collect);
//        }
    }

    /*
     * BUG
     * Если окно уже открыто, то не открывать его снова, но и не блокировать остальные окна
     * Добавить запись в лог
     */
    @FXML
    private void helpAction() throws IOException {
        Parent root = FXMLLoader.load(Resources.HELP_FORM);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Нelp");
        stage.setScene(scene);
        stage.show();
        menuItemHelp.setDisable(true);
    }

    @FXML
    /*
     * добавить диалоговое окно "Вы уверенны что хотите выйти?"
     * Добавить запись в лог
     */
    public void closeAction() {
        System.exit(0);
    }

    private void updateTaskInfo(SessionDataTask task) {
        if (task != null) {
            textTask.setText(task.getName());
            textDescription.setText(task.getDescription());
            dataPicDeadline.setValue(LocalDate.parse(task.getDeadline()));
            dataPicDateOpen.setValue(LocalDate.parse(task.getDateOpen()));
            dataPicDateClose.setValue(LocalDate.parse(task.getDateClose()));
            comboBoxStatus.setItems(langs);
            comboBoxStatus.setValue(task.getStatus());

        } else {
            textTask.setText("");
            textDescription.setText("");
            dataPicDeadline.setValue(null);
            dataPicDeadline.getEditor().clear();
            dataPicDateOpen.setValue(null);
            dataPicDateOpen.getEditor().clear();
            dataPicDateClose.setValue(null);
            dataPicDateClose.getEditor().clear();
            comboBoxStatus.setValue(null);
            comboBoxStatus.getEditor().clear();
            comboBoxTeg.setValue(null);
            comboBoxTeg.getEditor().clear();
        }
    }

    @Override
    public void handleEvent() {
        //здесь нужно вывести графическое обновление данных форы, тобишь запрос к контроллеру на новые данны
        tableView.setItems(controller.getTasks());
//tree.setTr
    }

    @FXML
    /*
     * Добавить дружелюбности, месседж бокс "Выберите задачу!" || "Не выбранна задача!"
     * Добавить запись в лог
     */
    private void buttonEditTask() {
        if (textTask.getText().trim().equals("")) {
            return;
        } else {
            setEditPane(false, 1);
            editTask.setDefaultButton(true);
        }

    }

    @FXML
    /*
     * Добавить дружелюбности, месседж бокс "Задача сохранена", или в лейбл внизу формы выводить
     * Добавить запись в лог
     */
    public void saveEditAction() {
        setEditPane(true, 0.9);
        val row = tableView.getSelectionModel().getSelectedIndex();
        if (0 <= row) {
            tableView.getItems().get(row);
        }
        SessionDataTask sessionDataTask = (SessionDataTask) tableView.getSelectionModel().getSelectedItem();
        SessionDataTask task = new SessionDataTask(
                textTask.getText(),
                textDescription.getText(),
                dataPicDeadline.getValue().format(formatter),
                sessionDataTask.getRestTime(),
                dataPicDateClose.getValue().format(formatter),
                dataPicDateOpen.getValue().format(formatter),
                comboBoxStatus.getValue().toString(),
                sessionDataTask.getProgressBar(),
                sessionDataTask.getSubTasks());
        controller.setTask(row, task);
    }

    public void setEditPane(Boolean bool, double opacity) {
        editTask.setDisable(!bool);
        saveEditTask.setDisable(bool);
        textTask.setDisable(bool);
        textDescription.setDisable(bool);
        dataPicDeadline.setDisable(bool);
        dataPicDateOpen.setDisable(bool);
        dataPicDateClose.setDisable(bool);
        comboBoxStatus.setDisable(bool);
        comboBoxTeg.setDisable(bool);

        textTask.setOpacity(opacity);
        textDescription.setOpacity(opacity);
        dataPicDeadline.setOpacity(opacity);
        dataPicDateOpen.setOpacity(opacity);
        dataPicDateClose.setOpacity(opacity);
        comboBoxStatus.setOpacity(opacity);
        comboBoxTeg.setOpacity(opacity);
        dataPicDeadline.setOpacity(opacity);
    }

    public void buttonHidePanelClose() {
        vBoxSplitPane2.setPrefWidth(0);
        vBoxSplitPane2.setVisible(false);
        hidePanelOpen.setVisible(true);
        hidePanelOpen.setSize("25");
    }

    public void buttonHidePanelOpen() {
        vBoxSplitPane2.setPrefWidth(215);
        vBoxSplitPane2.setVisible(true);
        hidePanelOpen.setVisible(false);
        hidePanelOpen.setSize("0");


    }


}



