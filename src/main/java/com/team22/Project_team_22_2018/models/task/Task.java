package com.team22.Project_team_22_2018.models.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Greffort
 * <p>
 * Содержит значение определенной задачи
 */
@Setter
@Getter
@AllArgsConstructor
public final class Task implements Serializable {

    private static final String FILL_DEFAULT = "not specified";

    private String name;

    private String deadline; //пока написал string, нужно будет подумать в чем удобнее хранить, что бы было проще работать

    private String restTime; //пока написал string, нужно будет подумать в чем удобнее хранить, что бы было проще работать

    private String status;

    private String description;

}
