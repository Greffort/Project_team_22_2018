package com.team22.project_team_22_2018.server.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Log4j
public class PurposeStage {

    private UUID uuid;

    private String name;

    private String completed;

    public PurposeStage() {
        this("not specified completed", "not specified name", UUID.randomUUID());
    }

    public PurposeStage(final String name, final String completed, UUID uuid) {
        this.name = name;
        this.completed = completed;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "PurposeStage{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurposeStage that = (PurposeStage) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(completed, that.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, completed);
    }
}
