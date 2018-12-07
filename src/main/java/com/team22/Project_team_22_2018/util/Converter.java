package com.team22.Project_team_22_2018.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team22.Project_team_22_2018.models.IModel;
import com.team22.Project_team_22_2018.models.manager.ManagerTask;
import com.team22.Project_team_22_2018.models.task.ITask;

import java.io.IOException;
import java.util.List;

/**
 * @author Greffort
 */
public final class Converter {
    public static String toJSON(ManagerTask obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public static ManagerTask toJavaObject(String s/*, SubTaskTask myclass*/) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, ManagerTask.class);
    }

    public static IModel toJavaObject(String s, IModel obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, obj.getClass());
    }
}
