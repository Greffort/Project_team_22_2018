package com.team22.project_team_22_2018.client.view.util.model;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.Objects;


@Getter
@Log4j
public class MySubObject {

    private String name;

    private String completed;

    public MySubObject() {
        this.completed = "not specified completed";
        this.name = "not specified name";
    }

    public MySubObject(final String name, final String completed) {
        this.name = name;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "MySubObject{" +
                "name='" + name + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MySubObject that = (MySubObject) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(completed, that.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, completed);
    }
}