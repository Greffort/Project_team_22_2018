package com.team22.Project_team_22_2018.models;

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

@Getter
@Setter
@Log4j
public class Purpose {
    private static final String FILL_DEFAULT = "not specified";
    private static final LocalDate FILL_DATE_DEFAULT = LocalDate.of(1970, 01, 01);

    private List<PurposeStage> purposeStages;

    private String name;

    private String criterionCompleted;

    private String description;

    private String status;


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
    }

    public Purpose(List<PurposeStage> purposeStages, String name, String criterionCompleted, String description, String status, LocalDate deadline, LocalDate dateOpen) {
        this.purposeStages = purposeStages;
        this.name = name;
        this.criterionCompleted = criterionCompleted;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.dateOpen = dateOpen;
    }


    public void addPurposeStage(PurposeStage purposeStage) {
        this.purposeStages.add(purposeStage);
    }


    public PurposeStage getPurposeStage(int index) {
        return this.purposeStages.get(index);
    }

    public void setPurposeStage(int index, PurposeStage purposeStage) {
        this.purposeStages.set(index, purposeStage);
    }

    public void removePurposeStage(int index) {
        this.purposeStages.remove(index);
    }
}
