package com.team22.project_team_22_2018.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "GOALSSTAGES")
public class GoalStages implements Serializable {

    @Id
    @Column(name = "IDGSTAG")
    private String uuid;

    @Column(name = "GSNAME")
    private String name;

    @Column(name = "GSCOMPLETED")
    private String completed;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "GOALSGOALSTAGES",
            joinColumns = @JoinColumn(name = "IDGSTAG"),
            inverseJoinColumns = @JoinColumn(name = "IDGOAL")
    )
    private Goals goal;

    public GoalStages() {
    }

    public GoalStages(String name, String completed) {
        this.name = name;
        this.completed = completed;
    }

    public GoalStages(String uuid, String name, String completed) {
        this.uuid = uuid;
        this.name = name;
        this.completed = completed;
    }

    public GoalStages(UUID uuid, String name, String completed, Goals idGoal) {
        this.uuid = uuid.toString();
        this.name = name;
        this.completed = completed;
        this.goal = idGoal;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid.toString();
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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Goals getGoal() {
        return goal;
    }

    public void setGoal(Goals idGoal) {
        this.goal = idGoal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalStages that = (GoalStages) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(completed, that.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, completed);
    }

    @Override
    public String toString() {
        return "GoalStagesDAO{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }
}
