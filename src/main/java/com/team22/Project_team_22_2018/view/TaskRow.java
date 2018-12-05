package com.team22.Project_team_22_2018.view;

//import delete_OldTask;
import com.team22.Project_team_22_2018.models.task.BaseTask;
import com.team22.Project_team_22_2018.models.task.ITask;
import com.team22.Project_team_22_2018.models.task.decorator.SubTaskTask;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Greffort
 * <p>
 * Содержит значение определенной задачи
 */


public final class TaskRow implements Serializable {

    private SimpleBooleanProperty selected;

    private SimpleStringProperty name;

    private SimpleObjectProperty<LocalDate> deadline;

    private SimpleObjectProperty<LocalDate> restTime;

    private SimpleIntegerProperty status;

    private SimpleStringProperty description;

    public TaskRow() {
        this.selected = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty();
        this.deadline = new SimpleObjectProperty<>();
        this.restTime = new SimpleObjectProperty<>();
        this.status = new SimpleIntegerProperty();
        this.description = new SimpleStringProperty();
    }

    public TaskRow(final ITask task) {
        this(task.getName(), task.getDeadline(), task.getRestTime(), task.getStatus(), task.getDescription());
    }

    public TaskRow(final String name,
                   final LocalDate deadline,
                   final LocalDate restTime,
                   final int status,
                   final String description) {
        this.selected = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty(name);
        this.deadline = new SimpleObjectProperty<>(deadline);
        this.restTime = new SimpleObjectProperty<>(restTime);
        this.status = new SimpleIntegerProperty(status);
        this.description = new SimpleStringProperty(description);
    }

    public ITask toTask() {
        return new SubTaskTask(new BaseTask(getName(), getDeadline(), getRestTime()));
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

    public LocalDate getDeadline() {
        return deadline.get();
    }

    public SimpleObjectProperty<LocalDate> deadlineProperty() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline.set(deadline);
    }

    public LocalDate getRestTime() {
        return restTime.get();
    }

    public SimpleObjectProperty<LocalDate> restTimeProperty() {
        return restTime;
    }

    public void setRestTime(LocalDate restTime) {
        this.restTime.set(restTime);
    }

    public int getStatus() {
        return status.get();
    }

    public SimpleIntegerProperty statusProperty() {
        return status;
    }

    public void setStatus(int status) {
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
