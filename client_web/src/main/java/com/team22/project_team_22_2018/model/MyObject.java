package com.team22.project_team_22_2018.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Log4j
@AllArgsConstructor
public class MyObject implements Serializable {
    private static final String FILL_DEFAULT = "not specified";
    private static final String FILL_DATE_DEFAULT = "1970-01-01";

    private String uuid;

    @JsonSerialize
    private List<MySubObject> goalStages;

    private String name;

    private String criterionCompleted;

    private String description;

    private String status;

    private String criticalTime;
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String deadline;
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String dateOpen;
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String dateClose;

    private int check = 0;

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
                0
        );
    }

    public MyObject(final List<MySubObject> goalStages,
                    final String name,
                    final String criterionCompleted,
                    final String description,
                    final String status,
                    final String deadline,
                    final String dateOpen,
                    final String criticalTime,
                    final String uuid,
                    final int bool) {
        this.goalStages = goalStages;
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

    public MySubObject getGoalStageI(final int index) {
        return this.goalStages.get(index);
    }

    public MySubObject getGoalStage(final String uuid) {
        if (goalStages.size() == 0) {
            return null;
        }
        for (int i = 0; i < goalStages.size() - 1; i++) {
            if (goalStages.get(i).getUuid().equals(uuid)) {
                return goalStages.get(i);
            }
        }
        return null;
    }

    public List<MySubObject> getGoalStages() {
        return goalStages;
    }

    public boolean isCheck() {
        if (this.check == 1) {
            return true;
        } else {
            return false;
        }

    }

    public void setCheck(final boolean check) {
        if (check == true) {
            this.check = 1;
        } else {
            this.check = 0;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyObject myObject = (MyObject) o;
        return check == myObject.check &&
                Objects.equals(uuid, myObject.uuid) &&
                Objects.equals(goalStages, myObject.goalStages) &&
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
        return Objects.hash(uuid, goalStages, name, criterionCompleted, description, status, criticalTime, deadline, dateClose, dateOpen, check);
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "uuid='" + uuid + '\'' +
                ", goalStages=" + goalStages +
                ", name='" + name + '\'' +
                ", criterionCompleted='" + criterionCompleted + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", criticalTime='" + criticalTime + '\'' +
                ", deadline='" + deadline + '\'' +
                ", dateOpen='" + dateOpen + '\'' +
                ", dateClose='" + dateClose + '\'' +
                ", check=" + check +
                '}';
    }
}
