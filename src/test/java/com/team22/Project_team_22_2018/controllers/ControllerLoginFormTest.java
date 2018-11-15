package com.team22.Project_team_22_2018.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
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
    public void testMalformedURLException() throws MalformedURLException{
        URL ur0 = Paths.get("src/main/resources/view/CreateTaskForm.fxml").toUri().toURL();
        URL url = Paths.get("").toUri().toURL();
        URL url2 = Paths.get(null).toUri().toURL();
    }

    @Test(expectedExceptions = IOException.class)
    public void testIOException() {
    }

    @Test
    public void buttonRemoteTask() {

    }

    @Test
    public void buttonEditTask() {

    }
}