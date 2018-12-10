package com.team22.Project_team_22_2018.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Greffort
 */
@Setter
@Getter
public class BaseTask implements Serializable {

    private static final String FILL_DEFAULT = "not specified";
    private static final LocalDate FILL_DATE_DEFAULT = null;

    private List<BaseTask> subTasks;

    private String name;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate deadline;

//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
//    private LocalDate restTime;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateClose;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOpen;

    private int status;

    private String description;

//    private int progressBar;


    public BaseTask() {
        this(FILL_DEFAULT, FILL_DATE_DEFAULT, FILL_DATE_DEFAULT);
    }

    public BaseTask(String name, LocalDate deadline, LocalDate dateOpen) {
//        restTime = deadline - dateOpen;
        this(name, deadline, FILL_DATE_DEFAULT, dateOpen);
    }

    public BaseTask(String name, LocalDate deadline, /*LocalDate restTime,*/LocalDate dateClose, LocalDate dateOpen) {

        this(name, deadline, /*restTime,*/ FILL_DATE_DEFAULT, dateOpen, 0);

    }

    public BaseTask(String name, LocalDate deadline, /*LocalDate restTime,*/ LocalDate dateClose, LocalDate dateOpen, int status) {

        this(name, deadline, /*restTime, */dateClose, dateOpen, status, FILL_DEFAULT);
    }

//    public BaseTask(String name, LocalDate deadline,/* LocalDate restTime,*/ LocalDate dateClose, LocalDate dateOpen, int status, String description) {
//
//        this(name, deadline, /*restTime, */dateClose, dateOpen, status, description, 0);
//    }

    public BaseTask(String name, LocalDate deadline, /*LocalDate restTime,*/ LocalDate dateOpen, LocalDate dateClose, int status, String description/*, int progressBar*/) {

        this.name = name;

        this.deadline = deadline;

        /* this.restTime = restTime; //дней до дедлайна*/

        this.dateClose = dateClose;

        this.dateOpen = dateOpen;

        this.status = status;

        this.description = description;

//        this.progressBar = progressBar;

        this.subTasks = new ArrayList<>();
    }

    public void addSubTask(BaseTask task, int index) {
        this.subTasks.add(index, task);
    }

    public void addSubTask(BaseTask task) {
        this.subTasks.add(task);
    }

    public BaseTask getSubTask(int index) {
        return this.subTasks.get(index);
    }

    public List<BaseTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTask(BaseTask task, int index) {
        this.subTasks.set(index, task);
    }

    public void removeSubTask(int index) {
        this.subTasks.remove(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTask baseTask = (BaseTask) o;
        return status == baseTask.status &&
                Objects.equals(subTasks, baseTask.subTasks) &&
                Objects.equals(name, baseTask.name) &&
                Objects.equals(deadline, baseTask.deadline) &&
                Objects.equals(dateClose, baseTask.dateClose) &&
                Objects.equals(dateOpen, baseTask.dateOpen) &&
                Objects.equals(description, baseTask.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subTasks, name, deadline, dateClose, dateOpen, status, description);
    }
}
