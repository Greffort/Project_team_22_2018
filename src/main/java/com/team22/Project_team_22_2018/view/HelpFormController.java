package com.team22.Project_team_22_2018.view;

import com.team22.Project_team_22_2018.util.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Greffort
 */
@Log4j
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

    public void close(){
        Stage stage = (Stage) helpLable.getScene().getWindow();
        stage.close();
    }
}

