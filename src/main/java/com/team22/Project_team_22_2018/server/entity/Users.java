package com.team22.project_team_22_2018.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Log4j
@Entity
@Table(name = "USERS")
public class Users implements Serializable {

    @Column(name = "UNAME")
    private String name;

    @Id
    @Column(name = "IDUSER", nullable = false)
    private String uuid = " ";

    @JsonSerialize
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Goals> goals;

    public Users() {

    }

    public Users(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid.toString();
        this.goals = new ArrayList<>();
    }

    public Users(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
        this.goals = new ArrayList<>();
    }

    public Users(List<Goals> goals, String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
        this.goals = goals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Goals> getGoals() {
        return goals;
    }

    public void setGoals(List<Goals> goals) {
        this.goals = goals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(name, users.name) &&
                Objects.equals(uuid, users.uuid) &&
                Objects.equals(goals, users.goals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uuid, goals);
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", goals=" + goals +
                '}';
    }
}
