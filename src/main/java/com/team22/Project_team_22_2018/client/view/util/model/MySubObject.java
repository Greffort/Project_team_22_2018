package com.team22.project_team_22_2018.client.view.util.model;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.Objects;
import java.util.UUID;


@Getter
@Log4j
public class MySubObject {

    private String uuid;

    private String name;

    private String completed;

    public MySubObject() {
        this("not specified completed", "not specified name", UUID.randomUUID().toString());
    }

    public MySubObject(final String name, final String completed, final String uuid) {
        this.name = name;
        this.completed = completed;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "MySubObject{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MySubObject that = (MySubObject) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(completed, that.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, completed);
    }
}