package com.team22.project_team_22_2018.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Log4j
public class Purpose {
    private static final String FILL_DEFAULT = "not specified";
    private static final LocalDate FILL_DATE_DEFAULT = LocalDate.of(1970, 1, 1);

    private List<PurposeStage> purposeStages;

    private String name;

    private String criterionCompleted;

    private String description;

    private String status;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate criticalTime;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateClose;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOpen;

    public Purpose() {
        this.purposeStages = new ArrayList<>();
        this.name = FILL_DEFAULT;
        this.criterionCompleted = FILL_DEFAULT;
        this.description = FILL_DEFAULT;
        this.status = FILL_DEFAULT;
        this.deadline = FILL_DATE_DEFAULT;
        this.dateOpen = FILL_DATE_DEFAULT;
        this.criticalTime = FILL_DATE_DEFAULT;
    }

    public Purpose(final List<PurposeStage> purposeStages,
                   final String name, final String criterionCompleted,
                   final String description,
                   final String status,
                   final LocalDate deadline,
                   final LocalDate dateOpen,
                   final LocalDate criticalTime) {
        this.purposeStages = purposeStages;
        this.name = name;
        this.criterionCompleted = criterionCompleted;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.dateOpen = dateOpen;
        this.criticalTime = criticalTime;
    }

    public void addPurposeStage(final PurposeStage purposeStage) {
        this.purposeStages.add(purposeStage);
    }

    public PurposeStage getPurposeStage(final int index) {
        return this.purposeStages.get(index);
    }

    public void setPurposeStage(final int index, final PurposeStage purposeStage) {
        this.purposeStages.set(index, purposeStage);
    }

    public void removePurposeStage(final int index) {
        this.purposeStages.remove(index);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purpose purpose = (Purpose) o;
        return Objects.equals(purposeStages, purpose.purposeStages) &&
                Objects.equals(name, purpose.name) &&
                Objects.equals(criterionCompleted, purpose.criterionCompleted) &&
                Objects.equals(description, purpose.description) &&
                Objects.equals(status, purpose.status) &&
                Objects.equals(criticalTime, purpose.criticalTime) &&
                Objects.equals(deadline, purpose.deadline) &&
                Objects.equals(dateClose, purpose.dateClose) &&
                Objects.equals(dateOpen, purpose.dateOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purposeStages, name, criterionCompleted, description, status, criticalTime, deadline, dateClose, dateOpen);
    }
}
