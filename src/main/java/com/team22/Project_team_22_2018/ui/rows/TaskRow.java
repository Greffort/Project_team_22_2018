package com.team22.Project_team_22_2018.ui.rows;

import com.team22.Project_team_22_2018.models.task.Task;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Greffort
 * <p>
 * Содержит значение определенной задачи
 */


public final class TaskRow implements Serializable {

    private static final String FILL_DEFAULT = "not specified";

//    columnDaysBeforeDeadline;
//    @FXML
//    private TableColumn<TaskRow, String> columnStatus;

    private SimpleBooleanProperty selected;

    private SimpleStringProperty name;

    private SimpleStringProperty deadline; //пока написал string, нужно будет подумать в чем удобнее хранить, что бы было проще работать

    private SimpleStringProperty restTime; //пока написал string, нужно будет подумать в чем удобнее хранить, что бы было проще работать

    private SimpleStringProperty status;

    private SimpleStringProperty description;

    public TaskRow() {
        this.selected = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty();
        this.deadline = new SimpleStringProperty();
        this.restTime = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    public TaskRow(final Task task) {
        this(task.getName(), task.getDeadline(), task.getRestTime(), task.getStatus(), task.getDescription());
    }

    public TaskRow(final String name,
                   final String deadline,
                   final String restTime,
                   final String status,
                   final String description) {
        this.selected = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty(name);
        this.deadline = new SimpleStringProperty(deadline);
        this.restTime = new SimpleStringProperty(restTime);
        this.status = new SimpleStringProperty(status);
        this.description = new SimpleStringProperty(description);
    }

    public Task toTask() {
        return new Task(getName(), getDeadline(), getRestTime(), getStatus(), getDescription());
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

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRow task = (TaskRow) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(deadline, task.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, deadline);
    }
}
