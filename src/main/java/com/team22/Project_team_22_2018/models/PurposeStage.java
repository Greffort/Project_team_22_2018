package com.team22.Project_team_22_2018.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author PurposeStage
 */
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

    public PurposeStage(String name, String completed) {
        this.name = name;
        this.completed = completed;
    }
}
