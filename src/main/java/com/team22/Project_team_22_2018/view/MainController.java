package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.models.Account;
import com.team22.Project_team_22_2018.models.Observable;
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

import static com.team22.Project_team_22_2018.util.Resources.ADD_PURPOSE;
import static com.team22.Project_team_22_2018.util.Resources.HELP_FORM;

@Log4j
/**
 * Определяет поведние входной формы "LoginForm"
 */
public class MainController implements Observer {
    //region Variables
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
    private ObservableList<String> listPurposeStatusValue = FXCollections.observableArrayList("IN_PROGRESS", "CLOSE", "OPEN", "WAITING", "TERMINATED");
    private ObservableList<String> listStageStatusValue = FXCollections.observableArrayList("+", "-");
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
    private TableColumn<Object, Object> numberingColumn;

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
    private ComboBox<String> comboBoxStatus;
    @FXML
    private ComboBox<String> comboBoxStageStatus;
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
    private Button buttonClosePurpose;
    @FXML
    private Button buttonOpenPurpose;

    @FXML
    private Button loadAction;
    //endregion

    public MainController() {
        ((Observable) account).registerObserver(this);
    }

    @FXML
    private void initialize() {
        tableView.setEditable(true);
        labelCloseDate.setVisible(false);
        buttonOpenPurpose.setVisible(false);
        buttonClosePurpose.setVisible(false);

        setEditPane(true, 0.9);
        dataPicDeadline.setConverter(converter); //dataPicDeadline.setPromptText("dd-MM-yyyy");

        //TableView
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        numberingColumn.setCellFactory(new NumberTableCellFactory<>());

        //редактирование столбца заданий
        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        taskColumn.setOnEditCommit(this::handle);

        listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null || listView.getSelectionModel().getSelectedIndex() != -1) {
                updatePurposeInfo(listView.getSelectionModel().getSelectedIndex());
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                TableViewData task = tableView.getSelectionModel().getSelectedItem();
                updatePurposeStageInfo(task);
            }
        });
    }

    //region "Purpose" methods
    @FXML
    private void buttonAddPurpose() {
        try {
            if (!flagAddStage) {
                flagAddStage = true;
                FXMLLoader loader = new FXMLLoader(ADD_PURPOSE);
                final Parent root = loader.load();
                val scene = new Scene(root);
                val stage = new Stage();
                stage.setTitle("Windows add");
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event -> flagAddStage = false);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    @FXML
    private void buttonRemotePurpose() {
        if (listView.getItems() != null) {
            controller.removePurpose(listView.getSelectionModel().getSelectedIndex());
            clearPurposeInfo();
            listView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void buttonEditPurpose() {
        if (namePurpose.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Выберите цель");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    listView.requestFocus();
                }
            });
        } else {
            setEditPane(false, 1);
        }
    }

    @FXML
    public void buttonClosePurpose() {
        controller.setPurposeDateClose(listView.getSelectionModel().getSelectedIndex(), LocalDate.now().toString());
        updatePurposeInfo(listView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void buttonOpenPurpose() {
        controller.setPurposeDateClose(listView.getSelectionModel().getSelectedIndex());
        updatePurposeInfo(listView.getSelectionModel().getSelectedIndex());
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
    private void loadAction() {
        processFile();
    }

    @FXML
    public void loadFromAction() {
        processFile();
    }
    //endregion

    //region "Stages" methods

    /**
     * Отправляет значения имени и статуса "Этапа" контроллеру
     */
    @FXML
    public void buttonAddNewStage() {
        if (textStage.getText().equals("") || comboBoxStageStatus.getItems() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Заполните поля");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    if (textStage.getText().equals("")) {
                        textStage.requestFocus();
                    } else {
                        comboBoxStageStatus.requestFocus();
                    }
                    return;
                }
            });
        } else {
            controller.addPurposeStage(listView.getSelectionModel().getSelectedIndex(), textStage.getText(), comboBoxStageStatus.getValue().toString());
            updatePurposeInfo(listView.getSelectionModel().getSelectedIndex());
            textStage.setText("");
        }
    }


    /**
     * Обрабатывает событие сохранения "Цели". Отправляет команду на перезапись объекта Purpose в модели
     */
    @FXML
    public void saveEditAction() {
        setEditPane(true, 0.9);
        controller.setPurpose(
                listView.getSelectionModel().getSelectedIndex(),
                tableView.getItems(),
                namePurpose.getText(),
                textCriterionCompleted.getText(),
                textDescription.getText(),
                comboBoxStatus.getValue(),
                dataPicDeadline.getValue().format(formatter)
        );
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Project team 22");
        alert.setHeaderText("Изменения сохранены");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                log.info("Внесены изменения в модель");
            }
        });
        textStage.setText("");
    }


    @FXML
    public void buttonRemoveStage() {
        if (tableView.getItems() != null) {
//            tableView.
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
            controller.removePurposeStage(listView.getSelectionModel().getSelectedIndex(), tableView.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    public void buttonSaveEditStage() {
        tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), new TableViewData(textStage.getText(), comboBoxStageStatus.getValue()));
        controller.setPurposeStage(
                listView.getSelectionModel().getSelectedIndex(),
                tableView.getItems());
        textStage.setText("");
    }
    //endregion

    //region MenuBar methods
    @FXML
    private void helpAction() {
        try {
            if (!flagHelpStage) {
                flagHelpStage = true;
                assert HELP_FORM != null;
                Parent root = FXMLLoader.load(HELP_FORM);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Нelp");
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event -> flagHelpStage = false);
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
        });
    }
    //endregion

    //region Util

    /**
     * Заполняет поля значениями объекта Purpose(Цель)
     */
    private void updatePurposeInfo(int index) {
        if (index != -1) {
            boolean name = controller.getNamePurpose(index) == null;
            boolean criterionCompleted = controller.getCriterionCompleted(index) == null;
            boolean description = controller.getDescription(index) == null;
            boolean createDate = controller.getCreateDate(index) == null;
            boolean status = controller.getStatus(index) == null;
            boolean deadline = controller.getDeadlineDate(index) == null;
            boolean closeDate = controller.getCloseDate(index) == null;

            comboBoxStageStatus.setItems(listStageStatusValue);
            comboBoxStatus.setItems(listPurposeStatusValue);
            comboBoxStageStatus.setValue("-");
            comboBoxStatus.setValue(listPurposeStatusValue.get(0));

            buttonOpenPurpose.setVisible(true);
            buttonClosePurpose.setVisible(true);

            //region Text Fields
            if (!name) {
                namePurpose.setText(controller.getNamePurpose(index));
            } else {
                namePurpose.setText("Поле не заполнено");
            }

            if (!criterionCompleted) {
                textCriterionCompleted.setText(controller.getCriterionCompleted(index));
            } else {
                textCriterionCompleted.setText("Поле не заполнено");
            }

            if (!description) {
                textDescription.setText(controller.getDescription(index));
            } else {
                textDescription.setText("Поле не заполнено");
            }

            if (!createDate) {
                labelCreateDate.setText("Дата создания цели: " + controller.getCreateDate(index));
            } else {
                labelCreateDate.setText("Поле не заполнено");
            }

            if (!status) {
                comboBoxStatus.setValue(controller.getStatus(index));
            } else {
                comboBoxStatus.setValue("Поле не заполнено");
            }

            if (!deadline) {
                dataPicDeadline.setValue(LocalDate.parse(controller.getDeadlineDate(index)));
            } else {
                comboBoxStatus.setValue("Поле не заполнено");
            }
//endregion

            //region TableView
            if (listView.getSelectionModel().getSelectedIndex() != -1 || controller.getPurposes() == null) {
                tableView.setItems(getTableViewDates());
            }
            //endregion

            //region Close Date
            if (!closeDate) {
                labelCloseDate.setText("Цель выполнена! Дата закрытия цели: " + controller.getCloseDate(index));
                labelCloseDate.setVisible(true);
            } else {
                labelCloseDate.setVisible(false);
            }
            //endregion
        }

    }

    /**
     * Выводит значения выделенного этапа в поля textStage и comboBoxStageStatus
     */
    private void updatePurposeStageInfo(TableViewData object) {
        if (object != null) {
            textStage.setText(object.getStage());
            comboBoxStageStatus.setValue(object.getStatus());
        }
    }

    private void clearPurposeInfo() {
        namePurpose.setText("");
        textCriterionCompleted.setText("");
        textDescription.setText("");
        dataPicDeadline.setValue(null);
        comboBoxStatus.setValue("");
        comboBoxStageStatus.setValue("");
        labelCreateDate.setText("");
        labelCloseDate.setText("");
        textStage.setText("");
        tableView.setItems(null);
    }


    /**
     * Конвертирует данные в список объектов TableViewDate. Используется tableView для отображения данных.
     */
    private ObservableList<TableViewData> getTableViewDates() {
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
//            listView.getSelectionModel().clearSelection();
            listView.setItems(controller.getPurposes());
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    /**
     * Изменяет видимость и активность элементов просмотра/редактирования объектов Purpose(Цель)
     */
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
//        buttonClosePurpose.setDisable(bool);

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
//        buttonClosePurpose.setOpacity(opacity);

    }

    private void handle(TableColumn.CellEditEvent<TableViewData, String> t) {
        controller.setStageName(
                listView.getSelectionModel().getSelectedIndex(),
                tableView.getSelectionModel().getSelectedIndex(),
                t.getNewValue()
        );
    }

    /**
     * Вызывает метод считывания файла
     */
    private void processFile() {
        if (!controller.loadAsB()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Ошибка чтения файла");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    log.info("Ошибка загрузки файла");
                    loadAction.requestFocus();
                }
            });
        } else {
            handleEvent();
        }
    }
//endregion

}



