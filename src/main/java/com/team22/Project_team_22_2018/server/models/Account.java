package com.team22.project_team_22_2018.server.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class Account implements Serializable {

    @JsonSerialize
    private List<Purpose> purposes;

    private String name;

    public Account() {
        this.purposes = new ArrayList<>();
        this.name = "not specified";
    }

    public Account(List<Purpose> purposes, String name) {
        this.purposes = purposes;
        this.name = name;
    }

    public void addPurpose(final Purpose purpose) {
        purposes.add(purpose);
    }

    public void removePurpose(final int index) {
        purposes.remove(index);
    }

    public Purpose getPurpose(final int index) {
        return purposes.get(index);
    }

    public void setPurpose(final int index, final Purpose purpose) {
        purposes.set(index, purpose);
    }

    public void setPurposes(final List<Purpose> purposes) {
        this.purposes = purposes;
    }

    public void clearPurposes() {
        purposes = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(purposes, account.purposes) &&
                Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purposes, name);
    }

    @Override
    public String toString() {
        return "Account{" +
                "purposes=" + purposes +
                ", name='" + name + '\'' +
                '}';
    }
}
