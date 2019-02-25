package com.team22.project_team_22_2018.server.controller;

import com.team22.project_team_22_2018.server.models.Account;
import com.team22.project_team_22_2018.server.models.Purpose;
import com.team22.project_team_22_2018.server.models.PurposeStage;
import com.team22.project_team_22_2018.server.util.ServerRuntimeHolder;
import com.team22.project_team_22_2018.util.Converter;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class ControllerModel {

    private Account account;

    public ControllerModel() {
        this.account = ServerRuntimeHolder.getModelHolder();
    }

    //region GET methods
    public Account getModel() {
        String s = this.account.toString();
        return this.account;
    }

    public ArrayList<String> getPurposes() {
        List<Purpose> list = this.account.getPurposes();
        ArrayList<String> arrayList = new ArrayList<>();
        for (Purpose purpose : list) {
            arrayList.add(purpose.getName());
        }
        return arrayList;
    }
    //endregion

    //region SET methods
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
     * Перезаписывает значение даты закрытия цели
     */
    public void setPurposeDateClose(final int index, final String dateClose) {
        account.getPurpose(index).setDateClose(LocalDate.parse(dateClose));
    }

    /**
     * Перезаписывает значение даты закрытия цели
     */
    public void setPurposeDateClose(final int index) {
        account.getPurpose(index).setDateClose(LocalDate.of(1970, 1, 1));
    }

    /**
     * Перезаписывает значение имени этапа цели
     */
    public void setStageName(final int indexPurpose, final int indexPurposeStage, final String name) {
        this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).setName(name);
    }
    //endregion

    //region ADD methods
    /**
     * Добавляет объект Purpose (Цель) в список Account
     */
    public void addPurpose(
            final ArrayList arrayList, final String name,
            final String criterionCompleted, final String description,
            final String status, final String deadline,
            final String dateOpen, final String criticalTime) {
        this.account.addPurpose(new Purpose(
                arrayList,
                name,
                criterionCompleted,
                description,
                status,
                LocalDate.parse(deadline),
                LocalDate.parse(dateOpen),
                criticalTime
//                LocalDate.parse(deadline).minusDays(Long.parseLong(criticalTime))
        ));
    }

    public void addPurpose(String line) {
        try {
            log.info("Добавление задачи");
            log.info(line);
            this.account.addPurpose(Converter.toJavaObject(line, Purpose.class));
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Добавляет объект PurposeStage (Этап выполнения цели) в список Account
     */
    public void addPurposeStage(final int indexPurpose, final String nameStage, final String status) {
        this.account.getPurpose(indexPurpose).addPurposeStage(new PurposeStage(nameStage, status));
    }
    //endregion

    //region REMOVE methods
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
    //endregion
}
