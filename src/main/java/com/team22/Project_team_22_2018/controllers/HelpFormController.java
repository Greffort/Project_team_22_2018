package com.team22.Project_team_22_2018.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Greffort
 */
public class HelpFormController {

    @FXML
    private Label helpLable;

    @FXML
    private void initialize() {

        helpLable.setText(readHelp());


    }

    public String readHelp() {
        String s = "";
        try {
            String path = getClass().getResource("").getPath() + "src/main/resources/help";
            File file = new File("C:/Users/Aleks/IdeaProjects/Project_team_22_2018/src/main/resources/file/help.txt");

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                    s+=line;
            }
            br.close();
            fr.close();

            return s;
        } catch (IOException e) {
            System.out.println("dfdfdsfafsegv");
        }

        return "dd";
    }
}

