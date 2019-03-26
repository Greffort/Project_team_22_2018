package com.team22.project_team_22_2018.client.view.util.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Log4j
@AllArgsConstructor
public class MyObject {
    private static final String FILL_DEFAULT = "not specified";
    private static final String FILL_DATE_DEFAULT = "1970-01-01";

    private String uuid;

    private List<MySubObject> purposeStages;

    private String name;

    private String criterionCompleted;

    private String description;

    private String status;

    private String criticalTime;

    private String deadline;

    private String dateClose;

    private String dateOpen;

    private boolean check = false;

    public MyObject() {
        this(
                new ArrayList<>(),
                FILL_DEFAULT,
                FILL_DEFAULT,
                FILL_DEFAULT,
                FILL_DEFAULT,
                FILL_DATE_DEFAULT,
                FILL_DATE_DEFAULT,
                FILL_DATE_DEFAULT,
                UUID.randomUUID().toString(),
                false
        );
    }

    public MyObject(final List<MySubObject> purposeStages,
                    final String name,
                    final String criterionCompleted,
                    final String description,
                    final String status,
                    final String deadline,
                    final String dateOpen,
                    final String criticalTime,
                    final String uuid,
                    final boolean bool) {
        this.purposeStages = purposeStages;
        this.name = name;
        this.criterionCompleted = criterionCompleted;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.dateOpen = dateOpen;
        this.dateClose = FILL_DATE_DEFAULT;
        this.criticalTime = criticalTime;
        this.uuid = uuid;
        this.check = bool;
    }

    public MySubObject getPurposeStageI(final int index) {
        return this.purposeStages.get(index);
    }

    public MySubObject getPurposeStage(final String uuid) {
        if (purposeStages.size() == 0) {
            return null;
        }
        for (int i = 0; i < purposeStages.size() - 1; i++) {
            if (purposeStages.get(i).getUuid().equals(uuid)) {
                return purposeStages.get(i);
            }
        }
        return null;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(final boolean check) {
        this.check = check;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyObject myObject = (MyObject) o;
        return check == myObject.check &&
                Objects.equals(uuid, myObject.uuid) &&
                Objects.equals(purposeStages, myObject.purposeStages) &&
                Objects.equals(name, myObject.name) &&
                Objects.equals(criterionCompleted, myObject.criterionCompleted) &&
                Objects.equals(description, myObject.description) &&
                Objects.equals(status, myObject.status) &&
                Objects.equals(criticalTime, myObject.criticalTime) &&
                Objects.equals(deadline, myObject.deadline) &&
                Objects.equals(dateClose, myObject.dateClose) &&
                Objects.equals(dateOpen, myObject.dateOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, purposeStages, name, criterionCompleted, description, status, criticalTime, deadline, dateClose, dateOpen, check);
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "uuid='" + uuid + '\'' +
                ", purposeStages=" + purposeStages +
                ", name='" + name + '\'' +
                ", criterionCompleted='" + criterionCompleted + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", criticalTime='" + criticalTime + '\'' +
                ", deadline='" + deadline + '\'' +
                ", dateClose='" + dateClose + '\'' +
                ", dateOpen='" + dateOpen + '\'' +
                ", check=" + check +
                '}';
    }
}
