package com.team22.project_team_22_2018.controller;

import com.team22.project_team_22_2018.models.Account;
import com.team22.project_team_22_2018.models.Purpose;
import com.team22.project_team_22_2018.models.PurposeStage;
import com.team22.project_team_22_2018.util.Converter;
import com.team22.project_team_22_2018.util.Resources;
import com.team22.project_team_22_2018.util.RuntimeHolder;
import com.team22.project_team_22_2018.view.util.TableViewData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import lombok.extern.log4j.Log4j;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class Controller {

    private Account account;

    public Controller() {
        this.account = RuntimeHolder.getModelHolder();
    }

    //region Add methods

    /**
     * Добавляет объект Purpose (Цель) в список Account
     */
    public void addPurpose(
            final ObservableList<TableViewData> purposeStages,
            final String name,
            final String criterionCompleted,
            final String description,
            final String status,
            final String deadline,
            final String dateOpen,
            final String criticalTime) {
        ArrayList<PurposeStage> arrayList = new ArrayList<>();
        for (TableViewData purposeStage : purposeStages) {
            arrayList.add(new PurposeStage(purposeStage.getStage(), purposeStage.getStatus()));
        }
        this.account.addPurpose(new Purpose(
                arrayList,
                name,
                criterionCompleted,
                description,
                status,
                LocalDate.parse(deadline),
                LocalDate.parse(dateOpen),
                LocalDate.parse(deadline).minusDays(Long.parseLong(criticalTime))
        ));
    }

    /**
     * Добавляет объект PurposeStage (Этап выполнения цели) в список Account
     */
    public void addPurposeStage(final int indexPurpose, final String nameStage, final String status) {
        this.account.getPurpose(indexPurpose).addPurposeStage(new PurposeStage(nameStage, status));
    }
    //endregion

    //region Removal methods

    /**
     * Удаляет объект Purpose (Цель) из списка Account
     */
    public void removePurpose(final int index) {
        if (index >= 0 && index < account.getPurposes().size()) {
            this.account.removePurpose(index);
        }
    }

    /**
     * Удаляет объект PurposeStage (Этап выполнения цели) из списка Account
     */
    public void removePurposeStage(final int indexPurpose, final int indexPurposeStage) {
        this.account.getPurpose(indexPurpose).removePurposeStage(indexPurposeStage);
    }

    public void clearPurposes() {
        this.account.clearPurposes();
    }
    //endregion

    //region Set methods

    /**
     * Перезаписывает значение конкретного объекта Purpose
     */
    public void setPurpose(final int index, final Purpose purpose) {
        this.account.setPurpose(index, purpose);
    }

    /**
     * Перезаписывает весь список Purpose(Целей)
     */
    public void setPurposes(final List<Purpose> list) {
        this.account.setPurposes(list);
    }

    /**
     * Перезаписывавет конкретный объект Purpose
     */
    public void setPurpose(
            final int index,
            final ObservableList<TableViewData> purposeStages,
            final String name,
            final String criterionCompleted,
            final String description,
            final String status,
            final String deadline
    ) {
        ArrayList<PurposeStage> list = new ArrayList<>();

        for (TableViewData aTableViewData : purposeStages) {
            list.add(new PurposeStage(aTableViewData.getStage(), aTableViewData.getStatus()));
        }
        this.account.getPurpose(index).setPurposeStages(list);
        this.account.getPurpose(index).setName(name);
        this.account.getPurpose(index).setCriterionCompleted(criterionCompleted);
        this.account.getPurpose(index).setDescription(description);
        this.account.getPurpose(index).setStatus(status);
        this.account.getPurpose(index).setDeadline(LocalDate.parse(deadline));
    }

    /**
     * Перезаписывает значение списка PurposeStages в выбранном Purpose
     */
    public void setPurposeStage(final int indexPurpose, final ObservableList<TableViewData> purposesStage) {
        ArrayList<PurposeStage> arrayList = new ArrayList<>();

        for (TableViewData aPurposesStage : purposesStage) {
            arrayList.add(new PurposeStage(aPurposesStage.getStage(), aPurposesStage.getStatus()));
        }
        this.account.getPurpose(indexPurpose).setPurposeStages(arrayList);
    }

    /**
     * Перезаписывает значение даты закрытия цели
     */
    public void setPurposeDateClose(final int index, final String dateClose) {
        account.getPurpose(index).setDateClose(LocalDate.parse(dateClose));
    }

    /**
     * Перезаписывает значение даты закрытия цели
     */
    public void setPurposeDateClose(final int index) {
        account.getPurpose(index).setDateClose(null);
    }

    /**
     * Перезаписывает значение имени этапа цели
     */
    public void setStageName(final int indexPurpose, final int indexPurposeStage, final String name) {
        this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).setName(name);
    }

    /**
     * Перезаписывает значение статуса этапа цели
     */
    public void setStageStatus(final int indexPurpose, final int indexPurposeStage, final String status) {
        this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).setCompleted(status);
    }

    public void setCriticalTime(final int index, final String countDays) {
        try {
            this.account.getPurpose(index).setCriticalTime(this.account.getPurpose(index).getDeadline().minusDays(Long.parseLong(countDays)));
        } catch (NumberFormatException e) {
            log.error(e);
        }
    }
    //endregion

    //region Get methods
    public ObservableList<String> getPurposes() {
        List<Purpose> list = this.account.getPurposes();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (Purpose purpose : list) {
            observableList.add(purpose.getName());
        }
        return observableList;
    }

    public String getNamePurpose(final int index) {
        if (this.account.getPurpose(index).getName() == null) {
            return "Имя не указанно";
        } else {
            return this.account.getPurpose(index).getName();
        }
    }

    public String getCriterionCompleted(final int index) {
        return this.account.getPurpose(index).getCriterionCompleted();
    }

    public String getDescription(final int index) {
        return this.account.getPurpose(index).getDescription();
    }

    public String getCreateDate(final int index) {
        return this.account.getPurpose(index).getDateOpen().toString();
    }

    public String getCloseDate(final int index) {
        if (this.account.getPurpose(index).getDateClose() == null) {
            return null;
        } else {
            return this.account.getPurpose(index).getDateClose().toString();
        }
    }

    public String getDeadlineDate(final int index) {
        return this.account.getPurpose(index).getDeadline().toString();
    }

    public String getStatus(final int index) {
        return this.account.getPurpose(index).getStatus();
    }

    public ObservableList<String> getStageNames(final int index) {

        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<PurposeStage> list = this.account.getPurpose(index).getPurposeStages();
        for (PurposeStage purposeStage : list) {
            observableList.add(purposeStage.getName());
        }
        return observableList;
    }

    public ObservableList<String> getStageStatuses(final int index) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<PurposeStage> list = this.account.getPurpose(index).getPurposeStages();
        for (PurposeStage purposeStage : list) {
            observableList.add(purposeStage.getCompleted());
        }
        return observableList;
    }

    public String getStageName(final int indexPurpose, final int indexPurposeStage) {
        return this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).getName();
    }

    public String getStageStatus(final int indexPurpose, final int indexPurposeStage) {
        return this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).getCompleted();
    }

    public String getCriticalTime(final int index) {
        return this.account.getPurpose(index).getCriticalTime().toString();
    }

    public String getCriticalTimeCountDays(final int index) {
        long number = ChronoUnit.DAYS.between(
                this.account.getPurpose(index).getCriticalTime(),
                this.account.getPurpose(index).getDeadline()
        );
        return String.valueOf(number);
    }
// endregion

    //region Save methods
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
    //endregion

    //region Loads methods
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

    public boolean loadAsB() {
        try {
            val fileChooser = new FileChooser();
            val file = fileChooser.showOpenDialog(null);
            if (file != null) {
                account.setPurposes(Converter.toJavaObject(file, account.getClass()).getPurposes());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    //endregion

}
