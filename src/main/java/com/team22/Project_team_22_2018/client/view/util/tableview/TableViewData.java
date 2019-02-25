package com.team22.project_team_22_2018.client.view.util.tableview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableViewData {

    private String stage;
    private String status;

    public TableViewData(final String stage, final String status) {
        this.stage = stage;
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(final String stage) {
        this.stage = stage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TableViewData{" +
                "stage='" + stage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
