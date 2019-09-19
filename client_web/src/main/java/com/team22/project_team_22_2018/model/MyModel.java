package com.team22.project_team_22_2018.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    private String name;

    private String uuid;

    @JsonSerialize
    private List<MyObject> goals;

    public MyModel() {
        this(new ArrayList<>(), "not specified", UUID.randomUUID().toString());
    }

    public MyModel(List<MyObject> goals, String name, String uuid) {
        this.goals = goals;
        this.name = name;
        this.uuid = uuid;
    }

    public MyObject getGoalI(final int index) {
        if (goals.isEmpty()) {
            return null;
        }
        return goals.get(index);
    }

    public MyObject getPurpose(final String uuid) {
        if (goals.isEmpty()) {
            return null;
        }
        for (@NotNull val purpose : goals) {
            String s = purpose.getUuid();
            if (s.equals(uuid)) {
                return purpose;
            }
        }
        return null;
    }

    public List<String> getGoals() {
        List<String> observableList = new ArrayList<>();
        for (@NotNull val purpose : this.goals) {
            observableList.add(purpose.getName());
        }
        return observableList;
    }

    public List<MyObject> getGoals(String s) {
        List<MyObject> list = this.goals;
        List<MyObject> observableList = new ArrayList<MyObject>();
        observableList.addAll(list);
        return observableList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyModel myModel = (MyModel) o;
        return Objects.equals(name, myModel.name) &&
                Objects.equals(uuid, myModel.uuid) &&
                Objects.equals(goals, myModel.goals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uuid, goals);
    }

    @Override
    public String toString() {
        return "MyModel{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", goals=" + goals +
                '}';
    }

}
