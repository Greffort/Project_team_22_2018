package com.team22.project_team_22_2018.server.view;

import com.team22.project_team_22_2018.server.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;


@Log4j
public class ServerFormController {

    @FXML
    private ListView<?> listViewUsers;
    @FXML
    private Label labelMaxCountUsers;
    @FXML
    private Label labelCountUsers;
    @FXML
    private Label labelIPserver;
    @FXML
    private Button buttonStartServer;
    @FXML
    private Button buttonStopServer;
    @FXML
    private Button buttonDeleteUser;
    @FXML
    private Button buttonEditMaxCountUsers;
    @FXML
    private TextField textFieldCountUsers;
    @FXML
    private TextField textFieldMaxCountUsers;

    @FXML
    private void initialize() {
        Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
        textFieldCountUsers.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                textFieldCountUsers.setText(oldValue);
            }
        });
        textFieldMaxCountUsers.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                textFieldMaxCountUsers.setText(oldValue);
            }
        });

        try {
            labelIPserver.setText("IP сервера - " + InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void buttonDeleteUser() {

    }

    @FXML
    void buttonStartServer() {
//        String[] s = new String[]{};
//        Thread thread = new Thread(Server::main);
//        thread.start();

//        Server.main();
    }

    @FXML
    void buttonStopServer() {
//        Server.setFLAG_STOP(true);
    }

    public void buttonEditMaxCountUsers() {
        if (textFieldMaxCountUsers.getText() == null) {
            Server.setMAX_COUNT_USERS(1);
        } else {
            Server.setMAX_COUNT_USERS(Integer.parseInt(textFieldMaxCountUsers.getText()));
        }

    }
}
