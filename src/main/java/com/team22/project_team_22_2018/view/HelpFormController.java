package com.team22.project_team_22_2018.view;

import com.team22.project_team_22_2018.util.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Log4j
public class HelpFormController {

    @FXML
    private Label helpLabel;

    @FXML
    private void initialize() {
        helpLabel.setText(readHelp());
    }

    private static String readHelp() {
        @NotNull val file = new File(Objects.requireNonNull(Resources.HELP_TEXT).getFile());
        try (@NotNull val br = new BufferedReader(new FileReader(file))) {
            String line;
            final StringBuilder s = new StringBuilder();
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }

    public void close() {
        Stage stage = (Stage) helpLabel.getScene().getWindow();
        stage.close();
    }
}

