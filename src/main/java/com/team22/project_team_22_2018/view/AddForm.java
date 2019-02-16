package com.team22.project_team_22_2018.view;

import com.team22.project_team_22_2018.controller.Controller;
import com.team22.project_team_22_2018.util.RuntimeHolder;
import com.team22.project_team_22_2018.view.util.NumberTableCellFactory;
import com.team22.project_team_22_2018.view.util.TableViewData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.log4j.Log4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j
public class AddForm {

    Controller controller = RuntimeHolder.getControllerHolder();
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
    private ObservableList<String> listPurposeStatusValue = FXCollections.observableArrayList("Overdue", "Burning", "Common", "Pending");
    private ObservableList<String> listStageStatusValue = FXCollections.observableArrayList("Завершен", "Выполняется");

    @FXML
    private TextField name;
    @FXML
    private TextField criterionCompleted;
    @FXML
    private TextField criticalTime;
    @FXML
    private TextArea description;
    @FXML
    private ComboBox<String> status;
    @FXML
    private DatePicker deadline;
    @FXML
    private TableView<TableViewData> tableView;
    @FXML
    private TableColumn<Object, Object> numberingColumn;
    @FXML
    private TableColumn<TableViewData, String> taskColumn;
    @FXML
    private TableColumn<TableViewData, String> statusColumn;
    @FXML
    private TextField stage;
    @FXML
    private ComboBox<String> stageStatus;

    @FXML
    private void initialize() {
        tableView.setEditable(true);

        deadline.setConverter(converter); //"dd-MM-yyyy"

        //TableView
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        numberingColumn.setCellFactory(new NumberTableCellFactory<>());

        //редактирование столбца заданий
        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        stageStatus.setValue("-");
        stageStatus.setItems(listStageStatusValue);
        status.setItems(listPurposeStatusValue);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updatePurposeStageInfo(newValue);
            }
        });
    }

    @FXML
    public void buttonAddNewStage() {

        if (stage.getText().equals("") || stageStatus.getItems() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Заполните поля");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    if (stage.getText().equals("")) {
                        stage.requestFocus();
                    } else {
                        stageStatus.requestFocus();
                    }
                }
            });
        } else {
            ObservableList<TableViewData> tableViewData = tableView.getItems();
            tableViewData.add(new TableViewData(stage.getText(), stageStatus.getValue()));
            stage.setText("");
        }

    }

    @FXML
    public void buttonAddPurpose() {
        if (checkFilling()) {
            controller.addPurpose(
                    tableView.getItems(),
                    this.name.getText(),
                    this.criterionCompleted.getText(),
                    this.description.getText(),
                    this.status.getValue(),
                    this.deadline.getValue().toString(),
                    LocalDate.now().toString(),
                    this.criticalTime.getText()
            );
            closeWindow();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Заполните все поля!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    log.info("Не заполнены поля");
                }
            });
        }
    }

    private boolean checkFilling() {
        boolean name = this.name.getText().equals("");
        boolean criterionCompleted = this.criterionCompleted.getText().equals("");
        boolean description = this.description.getText().equals("");
        boolean status = this.status.getValue() == null;
        boolean deadline = this.deadline.getValue() == null;
        boolean criticalTime = this.criticalTime.getText().equals("");

        if (name) {
            this.name.requestFocus();
            return false;
        }
        if (criterionCompleted) {
            this.criterionCompleted.requestFocus();
            return false;
        }
        if (description) {
            this.description.requestFocus();
            return false;
        }
        if (status) {
            this.status.requestFocus();
            return false;
        }
        if (deadline) {
            this.deadline.requestFocus();
            return false;
        }
        if (criticalTime) {
            this.criticalTime.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) this.name.getScene().getWindow();
        stage.onCloseRequestProperty();
        stage.close();
    }

    @FXML
    public void buttonRemoveStage() {
        if (tableView.getItems() != null) {
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    public void buttonSaveEditStage() {
        tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), new TableViewData(stage.getText(), stageStatus.getValue()));
        stage.setText("");
    }

    public void buttonCancel() {
        closeWindow();
    }

    private void updatePurposeStageInfo(final TableViewData object) {
        if (object != null) {
            stage.setText(object.getStage());
            stageStatus.setValue(object.getStatus());
        }
    }
}
