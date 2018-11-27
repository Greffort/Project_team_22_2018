package com.team22.Project_team_22_2018.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Greffort
 * <p>
 * Содержит значение определенной задачи
 */
@Setter
@Getter
@AllArgsConstructor
public final class Task implements Serializable {

    private String name;

    private String description;

    private String deadLine; //пока написал string, нужно будет подумать в чем удобнее хранить, что бы было проще работать

    private static final String FILL_DEFAULT = "not specified";


    public Task() {
        this(FILL_DEFAULT);
        //String content = IOUtils.toString(getClass().getResourceAsStream("constants"));
    }

    public Task(final String nameTask) {
        this(nameTask, FILL_DEFAULT);
    }

    public Task(final String nameTask, final String descriptionTask) {
        this(nameTask, descriptionTask, FILL_DEFAULT);
    }

    @Override
    public String toString() {
        return name + deadLine + deadLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(deadLine, task.deadLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, deadLine);
    }
}
