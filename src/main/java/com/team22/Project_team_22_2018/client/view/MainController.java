package com.team22.project_team_22_2018.client.view;

import com.team22.project_team_22_2018.client.controller.ControllerView;
import com.team22.project_team_22_2018.client.util.ClientRuntimeHolder;
import com.team22.project_team_22_2018.client.view.util.model.MyModel;
import com.team22.project_team_22_2018.client.view.util.model.Observable;
import com.team22.project_team_22_2018.client.view.util.tableview.NumberTableCellFactory;
import com.team22.project_team_22_2018.client.view.util.tableview.TableViewData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import lombok.extern.log4j.Log4j;
import lombok.val;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.team22.project_team_22_2018.util.Resources.*;

/**
 * Определяет поведние входной формы "LoginForm"
 */
@Log4j
public class MainController implements Observer {

    //region Variables
    private ControllerView controllerView = ClientRuntimeHolder.getControllerViewHolder();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
    private ObservableList<String> sortedPurposes;
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
    private ObservableList<String> listPurposeStatusValue = FXCollections.observableArrayList(
            "Просроченная", "Горящая", "Обычная", "В ожидании", "Закрытая");
    private ObservableList<String> listStageStatusValue = FXCollections.observableArrayList("Завершен", "Выполняется");
    private static boolean flagHelpStage = false;

    @Getter
    private static boolean flagAddStage = false;

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
    private TextField criticalTime;
    @FXML
    private TextField textCriterionCompleted;
    @FXML
    private CheckBox checkBoxSortByStatus;

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
    private Button saveEditStage;
    @FXML
    private Button removeStage;
    @FXML
    private Button loadAction;
    @FXML
    private Button buttonHome;

    @FXML
    private TextField textFieldSearch;
    @FXML
    private CheckBox checkBoxRegularX = new CheckBox("checkBoxRegularX");
    //endregion

    public MainController() {
        ((Observable) controllerView).registerObserver(this);
//        handleEvent();
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

        checkBoxSortByStatus.selectedProperty().addListener((observable, oldValue, newValue) -> handleEvent());

        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        criticalTime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                criticalTime.setText(oldValue);
            }
        });
        handleEvent();
    }

    public static void setFlagAddStage(boolean flag) {
        flagAddStage = flag;
    }

    public static void setFlagHelpStage(boolean flag) {
        flagHelpStage = flag;
    }

    //region "Purpose" methods
    @FXML
    private void buttonAddPurpose() {
        try {
//            controller.setCriticalTime(0,"9");
            if (!flagAddStage) {
                flagAddStage = true;
                FXMLLoader loader = new FXMLLoader(ADD_PURPOSE);
                final Parent root = loader.load();
                val scene = new Scene(root);
                val stage = new Stage();
                stage.setTitle("Windows add");
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Project team 22");
                    alert.setHeaderText("Вы уверены, что хотите закрыть окно?");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            flagAddStage = false;
                            stage.close();
                            log.info("Окно добавления задачи закрыто");
                        }
                    });
                });
//            stage.setOnCloseRequest(event -> flagAddStage = false);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    @FXML
    private void buttonRemotePurpose() {
        if (listView.getItems() != null && listView.getSelectionModel().getSelectedIndex() != -1) {
            controllerView.removePurpose(listView.getSelectionModel().getSelectedIndex());
            clearPurposeInfo();
            listView.getSelectionModel().clearSelection();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Выберите цель");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    listView.requestFocus();
                }
            });
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
        controllerView.setPurposeStatus(listView.getSelectionModel().getSelectedIndex(), "Закрытая");
        controllerView.setPurposeDateClose(listView.getSelectionModel().getSelectedIndex(), LocalDate.now().toString());
        updatePurposeInfo(listView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void buttonOpenPurpose() {
        setEditPane(true, 0.9);

//        ObservableList<TableViewData> tableViewData = tableView.getItems();

        /*
         * Добавить изменение одного статуса задачи. Только статуса. Не трогая все остальное.
         *
         */
        controllerView.setPurposeStatus(listView.getSelectionModel().getSelectedIndex(), "Обычная");

        controllerView.setPurposeDateCloseNull(listView.getSelectionModel().getSelectedIndex());
        updatePurposeInfo(listView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void buttonHome() {
        try {

            FXMLLoader loader = new FXMLLoader(HOME);
            final Parent root = loader.load();
            val scene = new Scene(root);
            val stage = new Stage();
            stage.setTitle("Home form");
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Project team 22");
                alert.setHeaderText("Вы уверены, что хотите закрыть окно?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        flagAddStage = false;
                        stage.close();
                        log.info("Окно закрыто");
                    }
                });
            });
        } catch (IOException e) {
            log.error(e);
        }
    }

    @FXML
    private void saveAction() {
//        controllerView.saveAs();
        controllerView.save();


    }

    @FXML
    private void changeValueCheckBoxRegularX() {
        checkBoxRegularX.setSelected(!checkBoxRegularX.isSelected());
        changeTextInSearchTextField(); // <- это нужно для того, чтобы после нажатия на чекбокс таблица так-же обновилась
    }

    //при смене текста в текстбоксе search происходит поиск в таблице в зависимости от галачки на checkbox-е
    //если галка стоит - ищем по регулярным выражениям, если нет - обычный поиск
    @FXML
    private void changeTextInSearchTextField() {

        if (textFieldSearch.getText().equals("")) {
            listView.setItems(
//                    controller.getModel()
                    controllerView.getMyObjectsName()

            );
        } else {
            sortedPurposes = controllerView.getMyObjectsName();
//            sortedPurposes = controller.g();

            if (!checkBoxRegularX.isSelected()) {

                for (int i = 0; i < sortedPurposes.size(); i++) {

                    if (!sortedPurposes.get(i).contains(textFieldSearch.getText())) {
                        sortedPurposes.remove(i);
                        i--;
                    }
                }

            } else {
                try {
                    for (int i = 0; i < sortedPurposes.size(); i++) {


                        if (!sortedPurposes.get(i).matches(textFieldSearch.getText())) {
                            sortedPurposes.remove(i);
                            i--;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    log.error(e);
                    sortedPurposes.clear();
                }
            }

            if (sortedPurposes.size() == 0)
                sortedPurposes.add("-"); //возвращает - если ничто не подходит под критерии поиска

            listView.setItems(sortedPurposes);
        }
    }

    @FXML
    public void saveAsAction() {
        controllerView.saveAs();
    }

    @FXML
    private void loadAction() {
        controllerView.updateModel();
    }

    @FXML
    public void loadFromAction() {
        processFile();
    }

    public void changUser() {
        try {
            closeWindow();
            FXMLLoader loader = new FXMLLoader(LOGIN);
            final Parent root = loader.load();
            val scene = new Scene(root);
            val stageLoginForm = new Stage();

            stageLoginForm.setHeight(231);
            stageLoginForm.setWidth(159);
            stageLoginForm.maxHeightProperty().setValue(231);
            stageLoginForm.maxWidthProperty().setValue(159);
            stageLoginForm.minHeightProperty().setValue(231);
            stageLoginForm.minWidthProperty().setValue(159);

            stageLoginForm.setTitle("Login form");
            stageLoginForm.setScene(scene);
            stageLoginForm.show();
            stageLoginForm.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Project team 22");
                alert.setHeaderText("Вы уверены, что хотите закрыть окно?");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        stageLoginForm.close();
                        log.info("Окно регистрации закрыто");
                    }
                });
            });
        } catch (IOException e) {
            log.error(e);
        }
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
                }
            });
        } else {
            controllerView.addPurposeStage(
                    listView.getSelectionModel().getSelectedIndex(),
                    textStage.getText(),
                    comboBoxStageStatus.getValue()
            );
            updatePurposeInfo(listView.getSelectionModel().getSelectedIndex());
            textStage.setText("");
        }
    }

    /**
     * Обрабатывает событие сохранения "Цели". Отправляет команду на перезапись объекта Purpose в модели
     */
    @FXML
    public void saveEditAction() {
//        String sD = dataPicDeadline.getValue().format(formatter);
//        String critical = String.valueOf(LocalDate.parse(sD).minusDays(
//                Long.parseLong(this.criticalTime.getText()))
//        );

        setEditPane(true, 0.9);
        controllerView.setPurpose(
                listView.getSelectionModel().getSelectedIndex(),
                tableView.getItems(),
                namePurpose.getText(),
                textCriterionCompleted.getText(),
                textDescription.getText(),
                comboBoxStatus.getValue(),
                dataPicDeadline.getValue().format(formatter),
                controllerView.getDateOpen(listView.getSelectionModel().getSelectedIndex()),
                this.criticalTime.getText()

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
        if (tableView.getItems() != null && tableView.getSelectionModel().getSelectedIndex() != -1) {
            int index = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().remove(index);
            controllerView.removePurposeStage(listView.getSelectionModel().getSelectedIndex(), index);
            textStage.setText("");
            comboBoxStageStatus.setValue("");
        }
    }

    @FXML
    public void buttonSaveEditStage() {


        if (tableView.getSelectionModel().getSelectedIndex() != -1) {
            tableView.getItems().set(
                    tableView.getSelectionModel().getSelectedIndex(),
                    new TableViewData(textStage.getText(), comboBoxStageStatus.getValue())
            );


            controllerView.setPurposeStages(
                    listView.getSelectionModel().getSelectedIndex(),
                    tableView.getItems());
            textStage.setText("");
        }
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
        controllerView.save();
        controllerView.close();
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
    //добавить здесь вывод criticalTime
    private void updatePurposeInfo(final int index) {
        if (index != -1) {
            boolean name = controllerView.getNamePurpose(index) == null;
            boolean criterionCompleted = controllerView.getCriterionCompleted(index) == null;
            boolean description = controllerView.getDescription(index) == null;
            boolean createDate = controllerView.getCreateDate(index) == null;
            boolean status = controllerView.getStatus(index) == null;
            boolean deadline = controllerView.getDeadlineDate(index) == null;
            boolean criticalTime = controllerView.getCriticalTime(index) == null;
            boolean closeDate = false;
            if (controllerView.getCloseDate(index) == null) {
                closeDate = true;
            } else if (controllerView.getCloseDate(index).equals("1970-01-01")) {
                closeDate = true;
            }

            comboBoxStageStatus.setItems(listStageStatusValue);
            comboBoxStatus.setItems(listPurposeStatusValue);
            comboBoxStageStatus.setValue("-");
            comboBoxStatus.setValue(listPurposeStatusValue.get(0));

            buttonOpenPurpose.setVisible(true);
            buttonClosePurpose.setVisible(true);

            //region Text Fields
            namePurpose.setText(!name ? controllerView.getNamePurpose(index) : "Поле не заполнено");
//            namePurpose.setText(!name ? controllerView.getCriticalTime(index) : "Поле не заполнено");
            textCriterionCompleted.setText(!criterionCompleted ? controllerView.getCriterionCompleted(index) : "Поле не заполнено");

            if (!criticalTime) {
                this.criticalTime.setText(controllerView.getCriticalTime(index));
            } else {
                this.criticalTime.setText("Поле не заполнено");
            }
            if (!description) {
                textDescription.setText(controllerView.getDescription(index));
            } else {
                textDescription.setText("Поле не заполнено");
            }
            if (!createDate) {
                labelCreateDate.setText("Дата создания цели: " + controllerView.getCreateDate(index));
            } else {
                labelCreateDate.setText("Поле не заполнено");
            }
            if (!status) {
                comboBoxStatus.setValue(controllerView.getStatus(index));
            } else {
                comboBoxStatus.setValue("Поле не заполнено");
            }
            if (!deadline) {
                dataPicDeadline.setValue(LocalDate.parse(controllerView.getDeadlineDate(index)));
            } else {
                comboBoxStatus.setValue("Поле не заполнено");
            }
            //endregion

            //region TableView

            if (listView.getSelectionModel().getSelectedIndex() != -1 || controllerView.getMyObjectsName() == null) {
                if (controllerView.getStatus(index).equals("Закрытая")) {
                    tableView.setItems(getTableViewDatesForOpen());
                } else {
                    tableView.setItems(getTableViewDates());
                }
            }
            //endregion

            //region Close Date
            if (!closeDate) {
                labelCloseDate.setText("Цель выполнена! Дата закрытия цели: " + controllerView.getCloseDate(index));
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
        criticalTime.setText("");
    }


    /**
     * Конвертирует данные в список объектов TableViewDate. Используется tableView для отображения данных.
     */
    private ObservableList<TableViewData> getTableViewDates() {
        ObservableList<TableViewData> tableViewData = FXCollections.observableArrayList();
        ObservableList<String> stageNames = controllerView.getStageNames(listView.getSelectionModel().getSelectedIndex());
        ObservableList<String> stageStatus = controllerView.getStageStatuses(listView.getSelectionModel().getSelectedIndex());

        for (int i = 0; i < stageNames.size(); i++) {
            tableViewData.add(new TableViewData(stageNames.get(i), stageStatus.get(i)));
        }
        return tableViewData;
    }

    private ObservableList<TableViewData> getTableViewDatesForOpen() {
        ObservableList<TableViewData> tableViewData = FXCollections.observableArrayList();
        ObservableList<String> stageNames = controllerView.getStageNames(listView.getSelectionModel().getSelectedIndex());
        ObservableList<String> stageStatus = controllerView.getStageStatuses(listView.getSelectionModel().getSelectedIndex());

        for (int i = 0; i < stageNames.size(); i++) {
            tableViewData.add(new TableViewData(stageNames.get(i), "Завершен"));
        }
        return tableViewData;
    }


    /**
     * Вызывается при обновлении данных в модели, и вызывает изменения формы
     */
    @Override
    public void handleEvent() {
        try {
            MyModel myModel = controllerView.getModel();
            if (!checkBoxSortByStatus.isSelected()) {
                controllerView.updateModel();
                listView.setItems(controllerView.getMyObjectsName());
            } else {
                controllerView.sortByStatus();
                listView.setItems(controllerView.getMyObjectsName());
            }
        } catch (NullPointerException e) {
            log.info("Доступных для отображения целей нет");
        }
    }


    /**
     * Изменяет видимость и активность элементов просмотра/редактирования объектов Purpose(Цель)
     */
    public void setEditPane(final Boolean bool, final double opacity) {
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
        criticalTime.setDisable(bool);
        saveEditStage.setDisable(bool);
        removeStage.setDisable(bool);

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
        criticalTime.setOpacity(opacity);
        saveEditStage.setOpacity(opacity);
        removeStage.setOpacity(opacity);
    }

    private void handle(final TableColumn.CellEditEvent<TableViewData, String> editEvent) {
        controllerView.setStageName(
                listView.getSelectionModel().getSelectedIndex(),
                tableView.getSelectionModel().getSelectedIndex(),
                editEvent.getNewValue()
        );
    }

    /**
     * Вызывает метод считывания файла
     */
    private void processFile() {
        if (!controllerView.loadAsB()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Ошибка чтения файла");
            alert.showAndWait().ifPresent(e -> {
                if (e == ButtonType.OK) {
                    log.info("Ошибка загрузки файла");
                    loadAction.requestFocus();
                }
            });
        } else {
            handleEvent();
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) this.listView.getScene().getWindow();
        stage.onCloseRequestProperty();
        stage.close();
    }


//endregion
}




