package com.team22.project_team_22_2018.client.view.util.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Log4j
public class MyModel implements Serializable {

    @JsonSerialize
    private List<MyObject> purposes;

    private String name;

    public MyModel() {
        this.purposes = new ArrayList<>();
        this.name = "not specified";
    }

    public MyModel(List<MyObject> purposes, String name) {
        this.purposes = purposes;
        this.name = name;
    }

    public MyObject getPurpose(final int index) {
        int a = purposes.size();
        if (purposes.size() == 0) {
            return null;
        }
        return purposes.get(index);
    }

    public ObservableList<String> getPurposes() {
        List<MyObject> list = this.purposes;
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (MyObject purpose : list) {
            observableList.add(purpose.getName());
        }
        return observableList;
    }

    @Override
    public String toString() {
        return "MyModel{" +
                "purposes=" + purposes +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyModel myModel = (MyModel) o;
        return Objects.equals(purposes, myModel.purposes) &&
                Objects.equals(name, myModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purposes, name);
    }
}
