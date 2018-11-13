package com.team22.Project_team_22_2018.task;

import lombok.*;

/**
 * @author Task
 */
@AllArgsConstructor
public class Task {
    @Setter
    @Getter
    private String nameTask;
    @Setter
    @Getter
    private String descriptionTask;
    @Setter
    @Getter
    private String deadLineTask; //пока написал string, нужно будет подумать в чем удобнее хранить, что бы было проще работать

    private static final String FILL_DEFAULT = "not specified";

    public Task() {
        this(FILL_DEFAULT);
    }

    public Task(String nameTask) {
        this(nameTask, FILL_DEFAULT);
    }

    public Task(String nameTask, String descriptionTask) {
        this(nameTask, descriptionTask, FILL_DEFAULT);
    }

}
