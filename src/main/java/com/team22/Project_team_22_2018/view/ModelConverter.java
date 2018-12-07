package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.models.task.ITask;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author Greffort
 * <p>
 * Содержит значение определенной задачи
 */


public final class ModelConverter implements Serializable  {

   //работает с конкретной задачей, получает ITask и возвращает ITask





//    private SimpleListProperty<ITask> subTasks;

    private SimpleBooleanProperty selected;

//    private SimpleStringProperty name;

//    private SimpleStringProperty description;

//    private SimpleObjectProperty<LocalDate> deadline;

//    private SimpleObjectProperty<LocalDate> restTime;

//    private SimpleObjectProperty<LocalDate> dateClose;

//    private SimpleObjectProperty<LocalDate> dateOpen;

//    private SimpleIntegerProperty status;

//    private SimpleIntegerProperty progressBar;


    public ModelConverter() {
//        this.subTasks = new SimpleListProperty<>();
        this.selected = new SimpleBooleanProperty();
//        this.name = new SimpleStringProperty();
//        this.description = new SimpleStringProperty();
//        this.deadline = new SimpleObjectProperty<>();
//        this.restTime = new SimpleObjectProperty<>();
//        this.dateClose = new SimpleObjectProperty<>();
//        this.dateOpen = new SimpleObjectProperty<>();
//        this.status = new SimpleIntegerProperty();
//        this.progressBar = new SimpleIntegerProperty();
    }

    public ModelConverter(final ITask task) {
        this(/*task.getSubTasks(),*//* task.getName(), task.getDescription(),task.getDeadline(),task.getRestTime(),task.getDateClose(),task.getDateOpen(),task.getStatus(),task.getProgressBar()*/);
    }

//    public ModelConverter(
//                          final String name,
//                          final String description,
//                          final LocalDate deadline,
//                          final LocalDate restTime,
//                          final LocalDate dateClose,
//                          final LocalDate dateOpen,
//                          final int status,
//                          final int progressBar) {
////        this.subTasks = new SimpleListProperty<>((ObservableList<ITask>) subTasks);
//        this.selected = new SimpleBooleanProperty();
//        this.name = new SimpleStringProperty(name);
//        this.description = new SimpleStringProperty(description);
//        this.deadline = new SimpleObjectProperty<>(deadline);
//        this.restTime = new SimpleObjectProperty<>(restTime);
//        this.dateClose = new SimpleObjectProperty<>(dateClose);
//        this.dateOpen = new SimpleObjectProperty<>(dateOpen);
//        this.status = new SimpleIntegerProperty(status);
//        this.progressBar = new SimpleIntegerProperty(progressBar);
//    }

//    public ITask toTask(){
//
//    }


    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelConverter that = (ModelConverter) o;
        return Objects.equals(selected, that.selected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selected);
    }
}
