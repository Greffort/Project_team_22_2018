package com.team22.Project_team_22_2018.ui.controllers;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author ControllerLoginFormTest
 */
public class ControllerLoginFormTest/* extends Assert */{


    //Sum_2Plus5_7Returned
    @Test
    public void buttonAddTask() {

    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void testMalformedURLException() throws MalformedURLException {
//        URL ur0 = Paths.get("src/main/resources/view/CreateTaskForm.fxml").toUri().toURL();
//        URL url = Paths.get("").toUri().toURL();
    }

    @Test(expectedExceptions = IOException.class)
    public void testIOException() {

    }

    @Test
    public void buttonRemoteTask() {
        System.out.println("buttonRemoteTask.test1");
    }

    @Test
    public void buttonEditTask() {

    }
}