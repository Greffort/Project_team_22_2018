package com.team22.Project_team_22_2018.controller;

import com.team22.Project_team_22_2018.models.Account;
import com.team22.Project_team_22_2018.models.Purpose;
import com.team22.Project_team_22_2018.models.PurposeStage;
import com.team22.Project_team_22_2018.util.RuntimeHolder;
import com.team22.Project_team_22_2018.view.util_view.TableViewData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j;

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
            arrayList.add(new PurposeStage(purposeStages.get(i).getStage(),purposeStages.get(i).getStatus()));
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
        try {
            this.account.removePurpose(index);
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    public void removePurposeStage(int indexPurpose, int indexPurposeStage) {
        try {
            this.account.getPurpose(indexPurpose).removePurposeStage(indexPurposeStage);
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    public void clearPurposes(int indexPurpose, int indexPurposeStage) {
        this.account.clearParposes();
    }

    //set
    public void setPurpose(int index, Purpose purpose) {
        try {
            this.account.setPurpose(index, purpose);
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    public void setPurposeStage(int indexPurpose, int indexPurposeStage, PurposeStage parpose) {
        try {
            this.account.getPurpose(indexPurpose).setPurposeStage(indexPurposeStage, parpose);
        } catch (NullPointerException e) {
            log.error(e);
        }
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
        try {
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
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    public void setStageName(int indexPurpose, int indexPurposeStage, String name) {
        try {
            this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).setName(name);
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    public void setStageStatus(int indexPurpose, int indexPurposeStage, String status) {
        try {
            this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).setCompleted(status);
        } catch (NullPointerException e) {
            log.error(e);
        }
    }

    //get
    public Purpose getPurpose(int index) {
        try {
            return this.account.getPurpose(index);
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public PurposeStage getPurposeStage(int indexPurpose, int indexPurposeStage) {
        try {
            return this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage);
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    //GET NEW
    public ObservableList<String> getPurposes() {
        try {
            List<Purpose> list = this.account.getPurposes();
            ObservableList<String> observableList = FXCollections.observableArrayList();

            for (Purpose purpose : list) {
                observableList.add(purpose.getName());
            }
            return observableList;
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getNamePurpose(int index) {
        try {
            return this.account.getPurpose(index).getName();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getCriterionCompleted(int index) {
        try {
            return this.account.getPurpose(index).getCriterionCompleted();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getDescription(int index) {
        try {
            return this.account.getPurpose(index).getDescription();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getCreateDate(int index) {
        try {
            return this.account.getPurpose(index).getDateOpen().toString();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getCloseDate(int index) {
        try {
            return this.account.getPurpose(index).getDateClose().toString();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getDeadlineDate(int index) {
        try {
            return this.account.getPurpose(index).getDeadline().toString();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getStatus(int index) {
        try {
            return this.account.getPurpose(index).getStatus();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public ObservableList<String> getStageNames(int index) {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<PurposeStage> list = this.account.getPurpose(index).getPurposeStages();
            for (PurposeStage purposeStage : list) {
                observableList.add(purposeStage.getName());
//                observableList.add(purposeStage.getCompleted());
            }
            return observableList;
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public ObservableList<String> getStageStatuses(int index) {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<PurposeStage> list = this.account.getPurpose(index).getPurposeStages();
            for (PurposeStage purposeStage : list) {
                observableList.add(purposeStage.getCompleted());
            }
            return observableList;
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getStageName(int indexPurpose, int indexPurposeStage) {
        try {
            return this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).getName();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    public String getStageStatus(int indexPurpose, int indexPurposeStage) {
        try {
            return this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).getCompleted();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
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
//            String s = Converter.toJSON(task);
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
//            managerTask.setTask(index, Converter.toJavaObject(Converter.toJSON(task), Task.class));
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
//                String s = Converter.toJSON(managerTask.getTask(i));
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
//                String s = Converter.toJSON(tasks.get(i));
//                arrayList.add(Converter.toJavaObject(s, Task.class));
//            }
//            managerTask.setTasks(arrayList);
//        } catch (IOException e) {
//            //запись в лог
//        }
//    }
}
