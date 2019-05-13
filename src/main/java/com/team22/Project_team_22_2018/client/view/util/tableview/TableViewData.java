package com.team22.project_team_22_2018.client.view.util.tableview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableViewData {

    private String stage;
    private String status;
    private String uuid;

    public TableViewData(final String stage, final String status) {
        this.stage = stage;
        this.status = status;
    }

    public TableViewData(final String stage, final String status, final String uuid) {
        this.stage = stage;
        this.status = status;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "TableViewData{" +
                "stage='" + stage + '\'' +
                ", status='" + status + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
