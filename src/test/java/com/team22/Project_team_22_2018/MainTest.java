package com.team22.Project_team_22_2018;

import com.team22.Project_team_22_2018.models.ManagerTask;
import com.team22.Project_team_22_2018.models.BaseTask;
import com.team22.Project_team_22_2018.controller.Converter;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author Greffort
 */
public class MainTest {

    public void testConverter() {
        final ManagerTask obj = new ManagerTask();
        obj.addTask(new BaseTask());
        BaseTask task3 = new BaseTask("ЗАДАНИЕ  11111", LocalDate.of(2018, 12, 12), LocalDate.of(2017, 12, 12));
        obj.addTask(task3);
        ManagerTask managerTask;
        try {
            final String s = Converter.toJSON(obj);
            System.out.println(s);
            managerTask = Converter.toJavaObject(s,ManagerTask.class);

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


}