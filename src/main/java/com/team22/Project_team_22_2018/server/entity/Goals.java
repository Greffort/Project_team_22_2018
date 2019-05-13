package com.team22.project_team_22_2018.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GOALS")
public class Goals implements Serializable {

    private static final String FILL_DEFAULT = "not specified";
    private static final LocalDate FILL_DATE_DEFAULT = LocalDate.of(1970, 1, 1);
    @Id
    @Column(name = "IDGOAL")
    private String uuid;

    @JsonSerialize
    @OneToMany(mappedBy = "goal", fetch = FetchType.EAGER)
    private List<GoalStages> goalStages;

    @Column(name = "GNAME")
    private String name;

    @Column(name = "GCRITERCOMPL")
    private String criterionCompleted;

    @Column(name = "GDESCRIP")
    private String description;

    @Column(name = "GSTATUS")
    private String status;

    @Column(name = "GCRITICTIME")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate criticalTime;

    @Column(name = "GDEADLINE")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @Column(name = "GDATEOPEN")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOpen;

    @Column(name = "GDATECLOSE")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateClose;

    @Column(name = "GCHEKCER")
    private int check = 0;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "USERSGOALS",
            joinColumns = @JoinColumn(name = "IDGOAL"),
            inverseJoinColumns = @JoinColumn(name = "IDUSER")
    )
    private Users user;

    public Goals() {
        uuid = "  ";
    }

    public Goals(String uuid, List<GoalStages> goalStages, String name, String criterionCompleted, String description, String status, LocalDate criticalTime, LocalDate deadline, LocalDate dateOpen, LocalDate dateClose, int check) {
        this.uuid = uuid.toString();
        this.goalStages = goalStages;
        this.name = name;
        this.criterionCompleted = criterionCompleted;
        this.description = description;
        this.status = status;
        this.criticalTime = criticalTime;
        this.deadline = deadline;
        this.dateOpen = dateOpen;
        this.dateClose = dateClose;

        this.check = check;

    }

//    public Goals(UUID uuid, List<GoalStages> goalStages, String name, String criterionCompleted, String description,
//                 String status, LocalDate criticalTime, LocalDate deadline, LocalDate dateOpen, LocalDate dateClose, Boolean check, Users user) {
//        this.uuid = uuid.toString();
//        this.goalStages = goalStages;
//        this.name = name;
//        this.criterionCompleted = criterionCompleted;
//        this.description = description;
//        this.status = status;
//        this.criticalTime = criticalTime;
//        this.deadline = deadline;
//        this.dateOpen = dateOpen;
//        this.dateClose = dateClose;
//        if (check == true) {
//            this.check = 1;
//        } else {
//            this.check = 0;
//        }
//        this.user = user;
//    }

//    public Goals() {
//        this(new ArrayList<>(),
//                FILL_DEFAULT,
//                FILL_DEFAULT,
//                FILL_DEFAULT,
//                FILL_DEFAULT,
//                FILL_DATE_DEFAULT,
//                FILL_DATE_DEFAULT,
//                FILL_DATE_DEFAULT,
//                UUID.randomUUID(),
//                false);
//    }
//
//    public Purpose(final List<GoalStages> purposeStages,
//                   final String name,
//                   final String criterionCompleted,
//                   final String description,
//                   final String status,
//                   final LocalDate deadline,
//                   final LocalDate dateOpen,
//                   final LocalDate criticalTime,
//                   final UUID uuid,
//                   final boolean bool) {
//        this.goalStages = purposeStages;
//        this.name = name;
//        this.criterionCompleted = criterionCompleted;
//        this.description = description;
//        this.status = status;
//        this.deadline = deadline;
//        this.dateOpen = dateOpen;
//        this.dateClose = FILL_DATE_DEFAULT;
//        this.criticalTime = criticalTime;
//        this.uuid = uuid.toString();
//        if (bool == true) {
//            this.check = 1;
//        } else {
//            this.check = 0;
//        }
//    }


    public static String getFillDefault() {
        return FILL_DEFAULT;
    }

    public static LocalDate getFillDateDefault() {
        return FILL_DATE_DEFAULT;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<GoalStages> getGoalStages() {
        return goalStages;
    }

    public void setGoalStages(List<GoalStages> goalStages) {
        this.goalStages = goalStages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCriterionCompleted() {
        return criterionCompleted;
    }

    public void setCriterionCompleted(String criterionCompleted) {
        this.criterionCompleted = criterionCompleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCriticalTime() {
        return criticalTime;
    }

    public void setCriticalTime(LocalDate criticalTime) {
        this.criticalTime = criticalTime;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDate dateClose) {
        this.dateClose = dateClose;
    }

    public LocalDate getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(LocalDate dateOpen) {
        this.dateOpen = dateOpen;
    }

    public int isCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getCheck() {
        return check;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users idUser) {
        this.user = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goals goals = (Goals) o;
        return check == goals.check &&
                Objects.equals(uuid, goals.uuid) &&
                Objects.equals(goalStages, goals.goalStages) &&
                Objects.equals(name, goals.name) &&
                Objects.equals(criterionCompleted, goals.criterionCompleted) &&
                Objects.equals(description, goals.description) &&
                Objects.equals(status, goals.status) &&
                Objects.equals(criticalTime, goals.criticalTime) &&
                Objects.equals(deadline, goals.deadline) &&
                Objects.equals(dateClose, goals.dateClose) &&
                Objects.equals(dateOpen, goals.dateOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, goalStages, name, criterionCompleted, description, status, criticalTime, deadline, dateClose, dateOpen, check);
    }

    @Override
    public String toString() {
        return "Goals{" +
                "uuid=" + uuid +
                ", goalStages=" + goalStages +
                ", name='" + name + '\'' +
                ", criterionCompleted='" + criterionCompleted + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", criticalTime=" + criticalTime +
                ", deadline=" + deadline +
                ", dateClose=" + dateClose +
                ", dateOpen=" + dateOpen +
                ", check=" + check +
                '}';
    }
}
