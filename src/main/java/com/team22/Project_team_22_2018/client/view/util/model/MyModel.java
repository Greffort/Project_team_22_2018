package com.team22.project_team_22_2018.client.view.util.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Log4j
public class MyModel implements Serializable {

    @JsonSerialize
    private List<MyObject> purposes;

    private String name;

    private String uuid;

    public MyModel() {
        this(new ArrayList<>(), "not specified", UUID.randomUUID().toString());
    }

    public MyModel(List<MyObject> purposes, String name, String uuid) {
        this.purposes = purposes;
        this.name = name;
        this.uuid = uuid;
    }

    public MyObject getPurposeI(final int index) {
        if (purposes.isEmpty()) {
            return null;
        }
        return purposes.get(index);
    }

    public MyObject getPurpose(final String uuid) {
        if (purposes.isEmpty()) {
            return null;
        }
        for (@NotNull val purpose : purposes) {
            String s = purpose.getUuid();
            if (s.equals(uuid)) {
                return purpose;
            }
        }
        return null;
    }

    public ObservableList<String> getPurposes() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (@NotNull val purpose : this.purposes) {
            observableList.add(purpose.getName());
        }
        return observableList;
    }

    public ObservableList<MyObject> getPurposes(String s) {
        List<MyObject> list = this.purposes;
        ObservableList<MyObject> observableList = FXCollections.observableArrayList();
        observableList.addAll(list);
        return observableList;
    }

    @Override
    public String toString() {
        return "MyModel{" +
                "uuid='" + uuid + '\'' +
                ", purposes=" + purposes +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyModel myModel = (MyModel) o;
        return Objects.equals(uuid, myModel.uuid) &&
                Objects.equals(purposes, myModel.purposes) &&
                Objects.equals(name, myModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, purposes, name);
    }
}
