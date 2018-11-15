package com.team22.Project_team_22_2018.controllers;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author ControllerLoginFormTest
 */
public class ControllerLoginFormTest extends Assert {


    //Sum_2Plus5_7Returned
    @Test
    public void buttonAddTask() {

    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void testMalformedURLException() {
        ControllerLoginForm controllerLoginForm = new ControllerLoginForm();


    }

    @Test(expectedExceptions = IOException.class)
    public void testIOException() {
        List list = null;
        int size = list.size();
    }

    @Test
    public void buttonRemoteTask() {

    }

    @Test
    public void buttonEditTask() {

    }
}