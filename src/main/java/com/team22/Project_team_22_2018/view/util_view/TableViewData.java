package com.team22.Project_team_22_2018.view.util_view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableViewData {

    private String stage;
    private String status;

    public TableViewData(String stage, String status) {
        this.stage = stage;
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
