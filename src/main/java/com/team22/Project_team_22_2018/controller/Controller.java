package com.team22.Project_team_22_2018.controller;

import com.team22.Project_team_22_2018.models.Account;
import com.team22.Project_team_22_2018.models.Purpose;
import com.team22.Project_team_22_2018.models.PurposeStage;
import com.team22.Project_team_22_2018.util.Resources;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import com.team22.Project_team_22_2018.view.util_view.TableViewData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import lombok.extern.log4j.Log4j;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Controller
 */
@Log4j
public class Controller {

    Account account;

    public Controller(Account account) {
        this.account = RuntimeHolder.getModelHolder();
    }

    //add
    public void addPurpose(
            ObservableList<TableViewData> purposeStages,
            String name,
            String criterionCompleted,
            String description,
            String status,
            String deadline,
            String dateOpen) {
        ArrayList<PurposeStage> arrayList = new ArrayList();

        for (int i = 0; i < purposeStages.size(); i++) {
            arrayList.add(new PurposeStage(purposeStages.get(i).getStage(), purposeStages.get(i).getStatus()));
        }
        this.account.addPurpose(new Purpose(
                arrayList,
                name,
                criterionCompleted,
                description,
                status,
                LocalDate.parse(deadline),
                LocalDate.parse(dateOpen)
        ));
    }

    public void addPurposeStage(int indexPurpose, PurposeStage purposeStage) {
        this.account.getPurpose(indexPurpose).addPurposeStage(purposeStage);//addPurpose(purposeStage);
    }

    //ADD NEW
    public void addPurpose(Purpose purposeStage) {

        this.account.addPurpose(purposeStage);
    }

    public void addPurposeStage(int indexPurpose, String nameStage, String status) {

        this.account.getPurpose(indexPurpose).addPurposeStage(new PurposeStage(nameStage, status));
        //addPurpose(purposeStage);
    }

    //remove
    public void removePurpose(int index) {
        if(index >= 0 && index < account.getPurposes().size()){
            this.account.removePurpose(index);
        }else {
         return;
        }
    }

    public void removePurposeStage(int indexPurpose, int indexPurposeStage) {
        this.account.getPurpose(indexPurpose).removePurposeStage(indexPurposeStage);
    }

    public void clearPurposes(int indexPurpose, int indexPurposeStage) {
        this.account.clearParposes();
    }

    //set
    public void setPurpose(int index, Purpose purpose) {
        this.account.setPurpose(index, purpose);
    }

    public void setPurposeStage(int indexPurpose, int indexPurposeStage, PurposeStage parpose) {

        this.account.getPurpose(indexPurpose).setPurposeStage(indexPurposeStage, parpose);
    }

    public void setPurposes(List<Purpose> list) {
        this.account.setPurposes(list);
    }

    //NEW SET
    public void setPurpose(int index,
                           ObservableList<TableViewData> purposeStages,
                           String name,
                           String criterionCompleted,
                           String description,
                           String status,
                           String deadline,
                           String dateOpen) {
        ObservableList<TableViewData> tableViewData = purposeStages;
        ArrayList<PurposeStage> list = new ArrayList<>();

        for (int i = 0; i < tableViewData.size(); i++) {
            list.add(new PurposeStage(tableViewData.get(i).getStage(), tableViewData.get(i).getStatus()));
        }
        this.account.setPurpose(index,
                new Purpose(
                        list,
                        name,
                        criterionCompleted,
                        description,
                        status,
                        LocalDate.parse(deadline),
                        LocalDate.parse(dateOpen)));
    }

    public void setStageName(int indexPurpose, int indexPurposeStage, String name) {
        this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).setName(name);
    }

    public void setStageStatus(int indexPurpose, int indexPurposeStage, String status) {
        this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).setCompleted(status);
    }

    //get
    public Purpose getPurpose(int index) {
        return this.account.getPurpose(index);
    }

    public PurposeStage getPurposeStage(int indexPurpose, int indexPurposeStage) {
        return this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage);
    }

    //GET NEW
    public ObservableList<String> getPurposes() {
        List<Purpose> list = this.account.getPurposes();
        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (Purpose purpose : list) {
            observableList.add(purpose.getName());
        }
        return observableList;
    }

    public String getNamePurpose(int index) {
        if (this.account.getPurpose(index).getName() == null) {
            return "Имя не указанно";
        } else {
            return this.account.getPurpose(index).getName();
        }
    }

    public String getCriterionCompleted(int index) {
        return this.account.getPurpose(index).getCriterionCompleted();
    }

    public String getDescription(int index) {
        return this.account.getPurpose(index).getDescription();
    }

    public String getCreateDate(int index) {
        return this.account.getPurpose(index).getDateOpen().toString();
    }

    public String getCloseDate(int index) {
        if (this.account.getPurpose(index).getDateClose() == null) {
            return null;
        } else {
            return this.account.getPurpose(index).getDateClose().toString();
        }
    }

    public String getDeadlineDate(int index) {

        return this.account.getPurpose(index).getDeadline().toString();
    }

    public String getStatus(int index) {
        return this.account.getPurpose(index).getStatus();
    }

    public ObservableList<String> getStageNames(int index) {

        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<PurposeStage> list = this.account.getPurpose(index).getPurposeStages();
        for (PurposeStage purposeStage : list) {
            observableList.add(purposeStage.getName());
//                observableList.add(purposeStage.getCompleted());
        }
        return observableList;
    }

    public ObservableList<String> getStageStatuses(int index) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<PurposeStage> list = this.account.getPurpose(index).getPurposeStages();
        for (PurposeStage purposeStage : list) {
            observableList.add(purposeStage.getCompleted());
        }
        return observableList;
    }

    public String getStageName(int indexPurpose, int indexPurposeStage) {
        return this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).getName();
    }

    public String getStageStatus(int indexPurpose, int indexPurposeStage) {
        return this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).getCompleted();
    }

    //SAVE

    public void save() {
        try {
//                File dir = new File(Resources.LOCAL_SAVE);
            File file = new File("C:\\Users\\Aleks\\save.txt");
            Converter.toJsonAs(file, account);

        } catch (IOException e) {
            log.error(e);
        }
    }

    public void saveAs() {
        try {
            val fileChooser = new FileChooser();
            val file = fileChooser.showSaveDialog(null);
            if (file != null) {
                Converter.toJsonAs(file, account);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }


    //LOAD
    public void load() {
        try {
            if (Resources.LOCAL_SAVE != null) {
                account.setPurposes(Converter.toJavaObject(Resources.LOCAL_SAVE, account.getClass()).getPurposes());

            } else {
                File file = new File("C:\\Users\\Aleks\\save.txt");
                account.setPurposes(Converter.toJavaObject(file, account.getClass()).getPurposes());
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void loadAs() {
        try {

            val fileChooser = new FileChooser();
            val file = fileChooser.showOpenDialog(null);
            if (file != null) {
                account.setPurposes(Converter.toJavaObject(file, account.getClass()).getPurposes());
            }
        } catch (IOException e) {
            log.error(e);
        }
    }


//
//    public PurposeStage getPurposeStage(int index) {
//        return this.purposeStages.get(index);
//    }
//
//    public void setPurposeStage(PurposeStage task, int index) {
//        this.purposeStages.set(index, task);
//    }
//
//
//    public void addTask(String name, String deadline, String dateClose, String dateOpen, String status, String description) {
//
//        managerTask.addTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description));
//    }
//
//    public void addSubTask(int indexTask, String name, String deadline, String dateClose, String dateOpen, String status, String description) {
//        managerTask.getTask(indexTask).addSubTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description));
//    }
//
//    public void addSubTask(int indexTask, int indexSubTask, String name, String deadline, String dateClose, String dateOpen, String status, String description) {
//        managerTask.getTask(indexTask).addSubTask(new Task(name, LocalDate.parse(deadline), LocalDate.parse(dateClose), LocalDate.parse(dateOpen), status, description), indexSubTask);
//    }
//
//
//    public SessionDataTask getTask(int index) {
//        try {
//            Task task = managerTask.getTask(index);
//            String s = Converter.toJson(task);
//            SessionDataTask sessionDataTask = Converter.toJavaObject(s, SessionDataTask.class);
//
//            System.out.println();
//            return sessionDataTask;
//
//        } catch (IOException e) {
//            //запись в лог
//        }
//
//        return null;
//    }
//
//    public void setTask(int index, SessionDataTask task) {
//        try {
//            managerTask.setTask(index, Converter.toJavaObject(Converter.toJson(task), Task.class));
//        } catch (IOException e) {
//            //запись в лог
//        }
//
////        managerTask.setTask(index, task);
//    }
//
//    public ObservableList getTasks() {
////        SessionDataManagerTask sessionDataManagerTask = new SessionDataManagerTask();
//        ObservableList<SessionDataTask> sessionDataTasks = FXCollections.observableArrayList();
//        try {
//            for (int i = 0; i < managerTask.getTasks().size(); i++) {
//                String s = Converter.toJson(managerTask.getTask(i));
//                sessionDataTasks.add(Converter.toJavaObject(s, SessionDataTask.class));
//            }
//            return sessionDataTasks;
//        } catch (IOException e) {
//            //запись в лог
//        }
//        return null;
//    }
//
//    public void setTasks(ObservableList tasks) {
//
//        ArrayList arrayList = new ArrayList();
//        try {
//            for (int i = 0; i < tasks.size(); i++) {
//                String s = Converter.toJson(tasks.get(i));
//                arrayList.add(Converter.toJavaObject(s, Task.class));
//            }
//            managerTask.setTasks(arrayList);
//        } catch (IOException e) {
//            //запись в лог
//        }
//    }
}
