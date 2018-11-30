package com.team22.Project_team_22_2018.ui.controllers;

import com.team22.Project_team_22_2018.util.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    private static String readHelp() {
        @NotNull val file = new File(Resources.HELP_TEXT.getFile());

        try (@NotNull val br = new BufferedReader(new FileReader(file))) {
            String line;
            final StringBuilder s = new StringBuilder();
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

