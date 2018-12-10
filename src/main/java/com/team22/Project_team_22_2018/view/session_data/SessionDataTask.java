package com.team22.Project_team_22_2018.view.session_data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team22.Project_team_22_2018.controller.Converter;
import com.team22.Project_team_22_2018.models.BaseTask;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SessionDataTask
 */

@Getter
@Setter
public class SessionDataTask {

    private List<SessionDataTask> subTasks;
    @JsonIgnore
    private SimpleBooleanProperty selected;

    private SimpleStringProperty name;

    private SimpleStringProperty description;

    private SimpleStringProperty deadline;
    @JsonIgnore
    private SimpleStringProperty restTime;

    private SimpleStringProperty dateClose;

    private SimpleStringProperty dateOpen;

    private SimpleStringProperty status;
    @JsonIgnore
    private SimpleStringProperty progressBar;
    @JsonIgnore
    private SimpleIntegerProperty numbering;


    public SessionDataTask() {
        this.selected = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.deadline = new SimpleStringProperty();
        this.restTime = new SimpleStringProperty();
        this.dateClose = new SimpleStringProperty();
        this.dateOpen = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.progressBar = new SimpleStringProperty();
        this.subTasks = new ArrayList<>();
        this.numbering = new SimpleIntegerProperty(0);
    }

    public SessionDataTask(String s) {
        try {
            SessionDataTask baseTaskConverter = Converter.toJavaObject(s, SessionDataTask.class);
            this.selected = new SimpleBooleanProperty();
            this.name = new SimpleStringProperty(baseTaskConverter.getName());
            this.description = new SimpleStringProperty(baseTaskConverter.getDescription());
            this.deadline = new SimpleStringProperty(baseTaskConverter.getDeadline());
            this.restTime = new SimpleStringProperty(baseTaskConverter.getRestTime());
            this.dateClose = new SimpleStringProperty(baseTaskConverter.getDateClose());
            this.dateOpen = new SimpleStringProperty(baseTaskConverter.getDateOpen());
            this.status = new SimpleStringProperty(baseTaskConverter.getStatus());
            this.progressBar = new SimpleStringProperty(baseTaskConverter.getProgressBar());
            this.numbering = new SimpleIntegerProperty(0);
        } catch (IOException e) {
            //запись в лог
        }
    }

    public SessionDataTask(final String name,
                           final String description,
                           final String deadline,
                           final String restTime,
                           final String dateClose,
                           final String dateOpen,
                           final String status,
                           final String progressBar,
                           final List subTasks) {
        this.selected = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.deadline = new SimpleStringProperty(deadline);
        this.restTime = new SimpleStringProperty(restTime);
        this.dateClose = new SimpleStringProperty(dateClose);
        this.dateOpen = new SimpleStringProperty(dateOpen);
        this.status = new SimpleStringProperty(status);
        this.progressBar = new SimpleStringProperty(progressBar);
        this.subTasks = subTasks;
        this.numbering = new SimpleIntegerProperty(0);
    }

    public List<SessionDataTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SessionDataTask> subTasks) {
        this.subTasks = subTasks;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getDeadline() {
        return deadline.get();
    }

    public SimpleStringProperty deadlineProperty() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline.set(deadline);
    }

    public String getRestTime() {
        return restTime.get();
    }

    public SimpleStringProperty restTimeProperty() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime.set(restTime);
    }

    public String getDateClose() {
        return dateClose.get();
    }

    public SimpleStringProperty dateCloseProperty() {
        return dateClose;
    }

    public void setDateClose(String dateClose) {
        this.dateClose.set(dateClose);
    }

    public String getDateOpen() {
        return dateOpen.get();
    }

    public SimpleStringProperty dateOpenProperty() {
        return dateOpen;
    }

    public void setDateOpen(String dateOpen) {
        this.dateOpen.set(dateOpen);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getProgressBar() {
        return progressBar.get();
    }

    public SimpleStringProperty progressBarProperty() {
        return progressBar;
    }

    public void setProgressBar(String progressBar) {
        this.progressBar.set(progressBar);
    }

    public int getNumbering() {
        return numbering.get();
    }

    public SimpleIntegerProperty numberingProperty() {
        return numbering;
    }

    public void setNumbering(int numbering) {
        this.numbering.set(numbering);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDataTask that = (SessionDataTask) o;
        return Objects.equals(selected, that.selected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selected);
    }
}
