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
    ObservableList<String> sortedPurposes;
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
    @FXML
    private TextField textFieldSearch;
    @FXML
    private CheckBox checkBoxRegularX = new CheckBox("checkBoxRegularX");

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

        tableView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> updatePurposeStageInfo(tableView.getSelectionModel().getSelectedIndex()));
    }

    //USE ACCOUNT
    @FXML
    /**
     * Обрабатывает нажатие кнопки "Добавить Цель"
     */
    private void buttonAddPurpose() throws IOException {
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
            stage.setMinHeight(465);
            stage.setMinWidth(650);
            stage.setTitle("добавить цель");
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

    //метод меняющий значемние, хранимое в checkbox-е
    @FXML
    private void changeValueCheckBoxRegularX(){
        checkBoxRegularX.setSelected(!checkBoxRegularX.isSelected());
        changeTextInSearchTextfield(); // <- это нужно для того, чтобы после нажатия на чекбокс таблица так-же обновилась
    }

    //при смене текста в текстбоксе search происходит поиск в зависимости от галачки на  checkbox-е
    //если галка стоит - ищем по регулярным выражениям, если нет - просто поиск
    @FXML
    private void changeTextInSearchTextfield(){

        if(textFieldSearch.getText().equals("")){
            listView.setItems(controller.getPurposes());
        }

        else {
            sortedPurposes = controller.getPurposes();

            if(!checkBoxRegularX.isSelected()) {

                for (int i = 0; i < sortedPurposes.size(); i++) {

                    if (!sortedPurposes.get(i).contains(textFieldSearch.getText())) {
                        sortedPurposes.remove(i);
                        i--;
                    }
                }

            }
            else{

                try{
                    for (int i = 0; i < sortedPurposes.size(); i++) {


                        if (!sortedPurposes.get(i).matches(textFieldSearch.getText())) {
                            sortedPurposes.remove(i);
                            i--;
                        }
                    }
                }
                catch (IllegalArgumentException e){}
            }

            if(sortedPurposes.size() == 0)
                sortedPurposes.add("-"); //возвращает - если ничто не подходит под критерии поиска

            listView.setItems(sortedPurposes);
        }
    }

    @FXML
    /**
     * Обрабатывает нажатие кнопки "Удалить Цель"
     */
    private void buttonRemotePurpose() {
        if (listView.getItems() != null) {
            controller.removePurpose(listView.getSelectionModel().getSelectedIndex());
        }

//Удалить вывод модели
        try {
            System.out.println(Converter.toJson(RuntimeHolder.getModelHolder()));
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
    private void saveAction() {
        controller.saveAs();
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
    public void saveAsAction() {
        controller.saveAs();
    }


    @FXML
    /*
     * BUG
     * Добавить обработку исключений
     * Загружать из XML с проверкой
     * Добавить запись в лог
     */
    private void loadAction() throws IOException, ClassNotFoundException {
        controller.loadAs();
        handleEvent();
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
        controller.loadAs();
        handleEvent();
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
        boolean name = controller.getNamePurpose(index) != null;
        boolean criterionCompleted = controller.getCriterionCompleted(index) != null;
        boolean description = controller.getDescription(index) != null;
        boolean createDate = controller.getCreateDate(index) != null;
//        boolean stageName = controller.getStageName(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex()) != null;
        boolean status = controller.getStatus(index) != null;
        boolean deadline = controller.getDeadlineDate(index) != null;
        boolean closeDate = controller.getCloseDate(index) != null;



        comboBoxStageStatus.setItems(langs2);
        comboBoxStatus.setItems(langs);
        comboBoxStageStatus.setValue("-");
        comboBoxStatus.setValue(langs.get(0));

        if (listView.getSelectionModel() != null && tableView.getItems() != null) {
            tableView.setItems(getTableViewDatas());
        }

        if (closeDate) {
            labelCloseDate.setText("Цель выполнена! Дата закрытия цели: " + controller.getCloseDate(index));
            labelCloseDate.setVisible(true);
        } else {
            labelCloseDate.setVisible(false);
        }

        if (name && criterionCompleted && description && createDate && /*stageName &&*/ status && deadline) {
            namePurpose.setText(controller.getNamePurpose(index));
            textCriterionCompleted.setText(controller.getCriterionCompleted(index));
            textDescription.setText(controller.getDescription(index));
            labelCreateDate.setText("Дата создания цели: " + controller.getCreateDate(index));
//
            dataPicDeadline.setValue(LocalDate.parse(controller.getDeadlineDate(index)));
        } else {
            namePurpose.setText("Поле не заполнено");
            textCriterionCompleted.setText("Поле не заполнено");
            textDescription.setText("Поле не заполнено");
            labelCreateDate.setText("Поле не заполнено");
            textDescription.setText("Поле не заполнено");
        }
    }

    private void updatePurposeStageInfo(int index) {
        //добавить проверки
        boolean stageName = controller.getStageName(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex()) != null;
        textStage.setText(controller.getStageName(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex()));
        tableView.setItems(getTableViewDatas());
//        try {
//            comboBoxStageStatus.setValue(controller.getStageStatus(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex()));
//            comboBoxStageStatus.setItems(langs2);
//            textStage.setText(controller.getStageName(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex()));
//        } catch (NullPointerException e) {
//            log.error(e);
//        }
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



