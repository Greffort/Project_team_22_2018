package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.controller.Converter;
import com.team22.Project_team_22_2018.models.Account;
import com.team22.Project_team_22_2018.models.Observable;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import com.team22.Project_team_22_2018.view.util_view.NumberTableCellFactory;
import com.team22.Project_team_22_2018.view.util_view.TableViewData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
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
@Log4j
public class MainController implements Observer {

    private Account account = RuntimeHolder.getModelHolder();
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
    private ObservableList<String> langs2 = FXCollections.observableArrayList("+", "-");
    private boolean flagHelpStage = false;
    @Getter
    @Setter
    private boolean flagAddStage = false;

    @FXML
    private TableView<TableViewData> tableView;
    @FXML
    private TableColumn<TableViewData, String> taskColumn;
    @FXML
    private TableColumn<TableViewData, String> statusColumn;
    @FXML
    private TableColumn numberingColumn;

    @FXML
    private TextField namePurpose;
    @FXML
    private TextField textCriterionCompleted;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea textDescription;
    @FXML
    private DatePicker dataPicDeadline;
    @FXML
    private ComboBox comboBoxStatus;
    @FXML
    private ComboBox comboBoxStageStatus;
    @FXML
    private Label labelCreateDate;
    @FXML
    private Label labelCloseDate;


    @FXML
    private TextField textStage;
    @FXML
    private Button editPurpose;
    @FXML
    private Button saveEditPurpose;
    @FXML
    private Button addNewStage;
    @FXML
    private Button closePurpose;

    public MainController() {
        ((Observable) account).registerObserver(this);
    }

    @FXML
    private void initialize() {
        tableView.setEditable(true);

        labelCloseDate.setVisible(false);

        setEditPane(true, 0.9);
        dataPicDeadline.setConverter(converter); //dataPicDeadline.setPromptText("dd-MM-yyyy");

        //TableView
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        numberingColumn.setCellFactory(new NumberTableCellFactory<>());

        //редактирование столбца заданий
        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        taskColumn.setOnEditCommit(this::handle);


        listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
                updatePurposeInfo(listView.getSelectionModel().getSelectedIndex()));


    }

    //USE ACCOUNT
    @FXML
    /**
     * Обрабатывает нажатие кнопки "Добавить Цель"
     */
    private void buttonAddPurpose() throws IOException{
//        try {
            if (flagAddStage) {
                return;
            } else {
                flagAddStage = false;
                flagAddStage = false;
                FXMLLoader loader = new FXMLLoader(Resources.ADD_PURPOSE);
                final Parent root = loader.load();
//                final CreateTaskFormController controller = loader.getController();
                val scene = new Scene(root);
                val stage = new Stage();
                stage.setTitle("Windows add");
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event -> {
                    flagAddStage = false;
                });
            }
//        } catch (IOException e) {
//            log.error(e);
//        }
    }

    @FXML
    /**
     * Обрабатывает нажатие кнопки "Удалить Цель"
     */
    private void buttonRemotePurpose() {

        controller.removePurpose(listView.getSelectionModel().getSelectedIndex());

        try {
            System.out.println(Converter.toJSON(RuntimeHolder.getModelHolder()));
        } catch (IOException e) {
            log.error(e);
        }
    }


    //VIEW PURPOSE

    @FXML
    /*
     * Добавить дружелюбности, месседж бокс "Выберите задачу!" || "Не выбранна задача!"
     */
    private void buttonEditPurpose() {
        if (namePurpose.getText() == null) {
            return;
        } else {
            setEditPane(false, 1);
//        }
        }
    }

    public void closePurpose() {
    }

    //USE STAGE
    @FXML
    /**
     * Отправляет значения имени и статуса "Этапа" контроллеру
     */
    public void buttonAddNewStage() {
        if (textStage.getText() == null || comboBoxStageStatus.getItems() == null) {
            return;
        } else {
            controller.addPurposeStage(listView.getSelectionModel().getSelectedIndex(), textStage.getText(), comboBoxStageStatus.getValue().toString());
            updatePurposeInfo(listView.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    /*
     * Добавить дружелюбности, месседж бокс "Задача сохранена", или в лейбл внизу формы выводить
     */
    /**
     * Обрабатывает событие сохранения "Цели". Отправляет команду на перезапись объекта Purpose в модели
     */
    public void saveEditAction() {
        setEditPane(true, 0.9);
        controller.setPurpose(
                listView.getSelectionModel().getSelectedIndex(),
                tableView.getItems(),
                namePurpose.getText(),
                textCriterionCompleted.getText(),
                textDescription.getText(),
                comboBoxStatus.getValue().toString(),
                dataPicDeadline.getValue().format(formatter),
                LocalDate.now().toString()
        );
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Project team 22");
        alert.setHeaderText("Изменения сохранены");
    }

    @FXML
    /*
     * BUG
     * Сделать сохранение в XML формат
     */
    private void saveAction() throws Exception {
//        try {
//            ObservableList<Task> items = tableView.getItems();
//        List<ITask> list = new ArrayList<>();
//        for (Task item : items) {
//            list.add(item.toTask());
//        }

//        List<Task> collect = tableView.getItems().stream().map(Task::toTask).collect(Collectors.toList());
//
//        Util.writeTasks(new com.team22.Project_team_22_2018.models.old.ManagerTask(collect), Resources.LOCAL_SAVE.getPath());

//        }catch (IOException e){
//            log.error(e);
//        }


//        ObservableList<Task> items = tableView.getItems();
//        List<ITask> list = new ArrayList<>();
//        for (Task item : items) {
//            list.add(item.toTask());
//        }

//        List<Task> collect = tableView.getItems().stream().map(Task::toTask).collect(Collectors.toList());
//
//        Util.writeTasks(new com.team22.Project_team_22_2018.models.old.ManagerTask(collect), Resources.LOCAL_SAVE.getPath());
    }

    @FXML
    /*
     * BUG
     * Добавить обработку исключений
     * Сделать сохранение в XML формат
     * Добавить запись в лог
     */
    public void saveAsAction() {
//        try{
        val fileChooser = new FileChooser();
        val file = fileChooser.showSaveDialog(null);
        //List<Task> collect = tableView.getItems().stream().map(Task::toTask).collect(Collectors.toList());
        if (file != null) {
            // Util.writeTasks(new com.team22.Project_team_22_2018.models.old.ManagerTask(collect), file.getPath());
//       }}catch (*//*IOException e*//*){
//            log.error(e); }
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
//        com.team22.Project_team_22_2018.models.old.ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
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
//            com.team22.Project_team_22_2018.models.old.ManagerTask managerTask = Util.readTasks(Resources.LOCAL_SAVE.getPath());
//            final List<Task> collect = managerTask.getTasks().stream().map(Task::new).collect(Collectors.toList());
//            tableView.getItems().setAll(collect);
//        }
    }

    @FXML
    private void helpAction() {
        try {
            if (flagHelpStage) {
                return;
            } else {
                flagHelpStage = true;
                Parent root = FXMLLoader.load(Resources.HELP_FORM);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Нelp");
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event -> {
                    flagHelpStage = false;
                });
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    @FXML
    public void closeAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Project team 22");
        alert.setHeaderText("Вы уверены, что хотите выйти?");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.exit(0);
                log.info("Приложение закрыто");
            }
            if (rs == ButtonType.CANCEL) {
                return;
            }
        });
    }


    private void updatePurposeInfo(int index) {
        namePurpose.setText(controller.getNamePurpose(index));
        textCriterionCompleted.setText(controller.getCriterionCompleted(index));
        textDescription.setText(controller.getDescription(index));

        labelCreateDate.setText("Дата создания цели: " + controller.getCreateDate(index));


        if ("null".equals(controller.getCloseDate(index))) {
            labelCloseDate.setVisible(false);
        } else {
            labelCloseDate.setVisible(true);
            labelCloseDate.setText("Цель выполнена! Дата закрытия цели: " + controller.getCloseDate(index));
        }
        try {
            dataPicDeadline.setValue(LocalDate.parse(controller.getDeadlineDate(index)));
            if (controller.getStageNames(listView.getSelectionModel().getSelectedIndex()) != null) {
                tableView.setItems(getTableViewDatas());
            }
            comboBoxStageStatus.setValue("-");
            comboBoxStageStatus.setItems(langs2);
            textStage.setText(controller.getStageName(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex()));
        } catch (NullPointerException e) {
            log.error(e);
        }

        comboBoxStageStatus.setItems(langs2);

        comboBoxStatus.setItems(langs);
        comboBoxStatus.setValue(controller.getStatus(index));


    }

    private void updatePurposeStageInfo() {
        try {
            comboBoxStageStatus.setValue(controller.getStageStatus(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex()));
            comboBoxStageStatus.setItems(langs2);
            textStage.setText(controller.getStageName(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex()));
        } catch (NullPointerException e) {
            log.error(e);
        }


    }

    private ObservableList<TableViewData> getTableViewDatas() {
        ObservableList<TableViewData> tableViewData = FXCollections.observableArrayList();
        ObservableList<String> stageNames = controller.getStageNames(listView.getSelectionModel().getSelectedIndex());
        ObservableList<String> stageStatus = controller.getStageStatuses(listView.getSelectionModel().getSelectedIndex());

        for (int i = 0; i < stageNames.size(); i++) {
            tableViewData.add(new TableViewData(stageNames.get(i), stageStatus.get(i)));
        }
        return tableViewData;
    }

    /**
     * Вызывается при обновлении данных в модели, и вызывает изменения формы
     */
    @Override
    public void handleEvent() {

        try {
            listView.setItems(controller.getPurposes());
        } catch (NullPointerException e) {
            log.error(e);
        }
    }


    public void setEditPane(Boolean bool, double opacity) {
        textStage.setDisable(bool);
        editPurpose.setDisable(!bool);
        saveEditPurpose.setDisable(bool);
        namePurpose.setDisable(bool);
        textDescription.setDisable(bool);
        dataPicDeadline.setDisable(bool);
        comboBoxStatus.setDisable(bool);
        tableView.setDisable(bool);
        textCriterionCompleted.setDisable(bool);
        comboBoxStageStatus.setDisable(bool);
        addNewStage.setDisable(bool);
        closePurpose.setDisable(bool);

        textStage.setOpacity(opacity);
        textCriterionCompleted.setOpacity(opacity);
        tableView.setOpacity(opacity);
        namePurpose.setOpacity(opacity);
        textDescription.setOpacity(opacity);
        dataPicDeadline.setOpacity(opacity);
        comboBoxStatus.setOpacity(opacity);
        dataPicDeadline.setOpacity(opacity);
        comboBoxStageStatus.setOpacity(opacity);
        addNewStage.setOpacity(opacity);
        closePurpose.setOpacity(opacity);

    }


    private void handle(TableColumn.CellEditEvent<TableViewData, String> t) {
        controller.setStageName(listView.getSelectionModel().getSelectedIndex(),
                tableView.getSelectionModel().getSelectedIndex(), t.getNewValue().toString());
    }
}



