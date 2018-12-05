package com.team22.Project_team_22_2018.models.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ConcreteTask
 */
@Setter
@Getter
public class BaseTask implements ITask {

    private static final String FILL_DEFAULT = "not specified";
    private static final LocalDate FILL_DATE_DEFAULT = LocalDate.of(1, 1, 1);

    private String name;

    private LocalDate deadline;

    private LocalDate restTime; //дней до дедлайна

    private LocalDate dateClose;

    private LocalDate dateOpen;

    private int status;

    private String description;

    private int progressBar;

    public BaseTask() {
        this(FILL_DEFAULT, FILL_DATE_DEFAULT, FILL_DATE_DEFAULT);
    }

    public BaseTask(String name, LocalDate deadline, LocalDate dateOpen) {
        this(name, deadline, FILL_DATE_DEFAULT, dateOpen);
    }

    public BaseTask(String name, LocalDate deadline, LocalDate restTime, LocalDate dateOpen) {

        this(name, deadline, restTime, FILL_DATE_DEFAULT, dateOpen, 0);

    }

    public BaseTask(String name, LocalDate deadline, LocalDate restTime, LocalDate dateClose, LocalDate dateOpen, int status) {

        this(name, deadline, restTime, dateClose, dateOpen, status, FILL_DEFAULT);
    }

    public BaseTask(String name, LocalDate deadline, LocalDate restTime, LocalDate dateClose, LocalDate dateOpen, int status, String description) {

        this(name, deadline, restTime, dateClose, dateOpen, status, description, 0);
    }

    public BaseTask(String name, LocalDate deadline, LocalDate restTime, LocalDate dateOpen, LocalDate dateClose, int status, String description, int progressBar) {

        this.name = name;

        this.deadline = deadline;

        this.restTime = restTime; //дней до дедлайна

        this.dateClose = dateClose;

        this.dateOpen = dateOpen;

        this.status = status;

        this.description = description;

        this.progressBar = progressBar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTask baseTask = (BaseTask) o;
        return status == baseTask.status &&
                progressBar == baseTask.progressBar &&
//                Objects.equals(subTasks, baseTask.subTasks) &&
                Objects.equals(name, baseTask.name) &&
                Objects.equals(deadline, baseTask.deadline) &&
                Objects.equals(restTime, baseTask.restTime) &&
                Objects.equals(dateClose, baseTask.dateClose) &&
                Objects.equals(dateOpen, baseTask.dateOpen) &&
                Objects.equals(description, baseTask.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, deadline, restTime, dateClose, dateOpen, status, description, progressBar);
    }
}
