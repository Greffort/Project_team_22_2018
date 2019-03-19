package com.team22.project_team_22_2018.server.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Log4j
public class Account implements Serializable {

    @JsonSerialize
    private List<Purpose> purposes;

    private String name;

    private UUID uuid;

    public Account() {
        this(new ArrayList<>(), "not specified");
    }

    public Account(List<Purpose> purposes, String name) {
        this(purposes, name, UUID.randomUUID());
    }

    public Account(List<Purpose> purposes, String name, UUID uuid) {
        this.purposes = purposes;
        this.name = name;
        this.uuid = uuid;
    }

    public void addPurpose(final Purpose purpose) {
        purposes.add(purpose);
    }

    public void removePurpose(final UUID uuid) {
        for (int i = 0; i < purposes.size(); i++) {
            if (purposes.get(i).getUuid().equals(uuid)) {
                purposes.remove(i);
            }
        }
    }

    public Purpose getPurpose(final UUID uuid) {
        log.info("Поиск объекта: " + uuid);
        for (int i = 0; i < purposes.size(); i++) {
            if (purposes.get(i).getUuid().equals(uuid)) {
                return purposes.get(i);
            }
        }
        log.info("Объект: " + uuid + " не найден");
        return null;
    }

    public void setPurpose(final UUID uuid, final Purpose purpose) {
        for (int i = 0; i < purposes.size(); i++) {
            if (purposes.get(i).getUuid().equals(uuid)) {
                purposes.set(i, purpose);
            }
        }
    }

    public void setPurposes(final List<Purpose> purposes) {
        this.purposes = purposes;
    }

    public void clearPurposes() {
        purposes = new ArrayList<>();
    }

    public void setStatus(final UUID uuid, final String status) {
        for (int i = 0; i < purposes.size(); i++) {
            if (purposes.get(i).getUuid().equals(uuid)) {
                purposes.get(i).setStatus(status);
            }
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "purposes=" + purposes +
                ", name='" + name + '\'' +
                ", uuid=" + uuid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(purposes, account.purposes) &&
                Objects.equals(name, account.name) &&
                Objects.equals(uuid, account.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purposes, name, uuid);
    }
}
