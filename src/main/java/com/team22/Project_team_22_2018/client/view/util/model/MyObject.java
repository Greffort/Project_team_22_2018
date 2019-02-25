package com.team22.project_team_22_2018.client.view.util.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Log4j
@AllArgsConstructor
public class MyObject {
    private static final String FILL_DEFAULT = "not specified";
    private static final String FILL_DATE_DEFAULT = "1970-01-01";

    private List<MySubObject> purposeStages;

    private String name;

    private String criterionCompleted;

    private String description;

    private String status;

    private String criticalTime;

    private String deadline;

    private String dateClose;

    private String dateOpen;

    public MyObject() {
        this.purposeStages = new ArrayList<>();
        this.name = FILL_DEFAULT;
        this.criterionCompleted = FILL_DEFAULT;
        this.description = FILL_DEFAULT;
        this.status = FILL_DEFAULT;
        this.deadline = FILL_DATE_DEFAULT;
        this.dateOpen = FILL_DATE_DEFAULT;
        this.criticalTime = FILL_DATE_DEFAULT;
    }

    public MyObject(final List<MySubObject> purposeStages,
                    final String name,
                    final String criterionCompleted,
                    final String description,
                    final String status,
                    final String deadline,
                    final String dateOpen,
                    final String criticalTime) {
        this.purposeStages = purposeStages;
        this.name = name;
        this.criterionCompleted = criterionCompleted;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.dateOpen = dateOpen;
        this.criticalTime = criticalTime;
//        this.dataClose = FILL_DATE_DEFAULT;
    }

    public MySubObject getPurposeStage(final int index) {
        return this.purposeStages.get(index);
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "purposeStages=" + purposeStages +
                ", name='" + name + '\'' +
                ", criterionCompleted='" + criterionCompleted + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", criticalTime='" + criticalTime + '\'' +
                ", deadline='" + deadline + '\'' +
                ", dateClose='" + dateClose + '\'' +
                ", dateOpen='" + dateOpen + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyObject myObject = (MyObject) o;
        return Objects.equals(purposeStages, myObject.purposeStages) &&
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
        return Objects.hash(purposeStages, name, criterionCompleted, description, status, criticalTime, deadline, dateClose, dateOpen);
    }
}
