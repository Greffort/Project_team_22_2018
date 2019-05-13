package com.team22.project_team_22_2018.server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "PASS")
public class Pass implements Serializable {

    @Id
    @Column(name = "ID")
    private String number;

    @Column(name = "PPASS")
    private String pPass;

    @OneToOne(cascade = CascadeType.ALL)
    private Users users;

    public Pass() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getpPass() {
        return pPass;
    }

    public void setpPass(String pPass) {
        this.pPass = pPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pass pass = (Pass) o;
        return Objects.equals(number, pass.number) &&
                Objects.equals(pPass, pass.pPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, pPass);
    }

    @Override
    public String toString() {
        return "Pass{" +
                "number='" + number + '\'' +
                ", pPass='" + pPass + '\'' +
                '}';
    }
}
