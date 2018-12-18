package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.controller.Controller;
import com.team22.Project_team_22_2018.models.Account;
import com.team22.Project_team_22_2018.models.Observable;
import com.team22.Project_team_22_2018.models.Purpose;
import com.team22.Project_team_22_2018.models.PurposeStage;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import com.team22.Project_team_22_2018.view.util_view.NumberTableCellFactory;
import com.team22.Project_team_22_2018.view.util_view.TableViewData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.log4j.Log4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * @author AddForm
 */
@Log4j
public class AddForm implements Observer {

    private Account account = RuntimeHolder.getModelHolder();
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
    private ObservableList<String> langs = FXCollections.observableArrayList("IN_PROGRESS", "CLOSE", "OPEN", "WAITING", "TERMINATED");
    private ObservableList<String> langs2 = FXCollections.observableArrayList("+", "-");


    @FXML
    private TextField name;
    @FXML
    private TextField criterionCompleted;
    @FXML
    private TextArea description;
    @FXML
    private ComboBox status;
    @FXML
    private DatePicker deadline;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn numberingColumn;
    @FXML
    private TableColumn<TableViewData, String> taskColumn;
    @FXML
    private TableColumn<TableViewData, String> statusColumn;
    @FXML
    private TextField stage;
    @FXML
    private ComboBox stageStatus;
    @FXML
    private Button addNewStage;
    @FXML
    private Button addPurpose;

    public AddForm() {
        ((Observable) account).registerObserver(this);
    }

    @FXML
    private void initialize() {
        tableView.setEditable(true);

        deadline.setConverter(converter); //dataPicDeadline.setPromptText("dd-MM-yyyy");

        //TableView
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        numberingColumn.setCellFactory(new NumberTableCellFactory<>());

        //редактирование столбца заданий
        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        stageStatus.setValue("-");
        stageStatus.setItems(langs2);
        status.setItems(langs);
//        taskColumn.setOnEditCommit(this::handle);
//
//tableView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->  updatePurposeInfo());
//
//        listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
//                updatePurposeInfo(listView.getSelectionModel().getSelectedIndex()));
//

    }

    @FXML
    public void buttonAddNewStage() {
        if (stage.getText() == null || stageStatus.getItems() == null) {
            return;
        } else {
            ObservableList<TableViewData> tableViewData = tableView.getItems();
            tableViewData.add(new TableViewData(stage.getText(), stageStatus.getValue().toString()));
        }

    }

    @FXML
    public void buttonAddPurpose() {
        controller.addPurpose(
                tableView.getItems(),
                name.getText(),
                criterionCompleted.getText(),
                description.getText(),
                status.getValue().toString(),
                deadline.getValue().toString(),
                LocalDate.now().toString()
        );

//        controller.addPurpose(new Purpose());
//        controller.addPurposeStage(0, new PurposeStage());
//        controller.addTask("Имя задачи","1997-10-01","1997-10-02","1997-10-03","WAITING","Описание");
//        controller.addSubTask(0,0,"Подзадача","1997-10-01","1997-10-02","1997-10-03","WAITING","Описание");
//        Task newTask = new Task(taskNameTextField.getText(), LocalDate.of(1, 1, 1).toString(), LocalDate.of(1, 1, 1).toString(), LocalDate.of(1, 1, 1).toString(), taskDescriptionTextArea.getText(), LocalDate.of(1, 1, 1).toString(), "", "");
//        controller.addTask(newTask);
//        tasks.addAll(newTask);
//try{
////    System.out.println(Converter.toJSON(RuntimeHolder.getModelHolder()));
//}catch (IOException e ){
//
//}
//        controller.setFlagAddStage(false);
        closeWindow();

    }

    public void closeWindow() {
//        Stage stage = (Stage) closeButton.getScene().getWindow();
//        stage.close();
    }


//    private ObservableList<TableViewData> getTableViewDatas() {
//        ObservableList<TableViewData> tableViewData = FXCollections.observableArrayList();
////        ObservableList<String> stageNames = controller.getStageNames(listView.getSelectionModel().getSelectedIndex());
////        ObservableList<String> stageStatus = controller.getStageStatuses(listView.getSelectionModel().getSelectedIndex());
//
//        for (int i = 0; i < stageNames.size(); i++) {
//            tableViewData.add(new TableViewData(stageNames.get(i), stageStatus.get(i)));
//        }
//        return tableViewData;
//    }

    @Override
    public void handleEvent() {

        try {
//            tableView.setItems();
//            listView.setItems(controller.getPurposes());
        } catch (NullPointerException e) {
//            log.error(e);
        }
    }

//    private void updatePurposeInfo(int index) {
//
//        name.setText(controller.getNamePurpose(index));
//                tableView.setItems(getTableViewDatas());
//
//            stageStatus.setValue("-");
//        stageStatus.setItems(langs2);
//
//        } catch (NullPointerException e) {
//            log.error(e);
//        }
//
//        comboBoxStageStatus.setItems(langs2);
//
//        comboBoxStatus.setItems(langs);
//        comboBoxStatus.setValue(controller.getStatus(index));
//
//
//    }
}
