package com.team22.project_team_22_2018.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.Objects;

@Getter
@Setter
@Log4j
public class PurposeStage {

    private String name;

    private String completed;

    public PurposeStage() {
        this.completed = "not specified completed";
        this.name = "not specified name";
    }

    public PurposeStage(final String name, final String completed) {
        this.name = name;
        this.completed = completed;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurposeStage that = (PurposeStage) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(completed, that.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, completed);
    }
}
