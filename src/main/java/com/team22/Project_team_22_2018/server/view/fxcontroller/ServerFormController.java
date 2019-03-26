package com.team22.project_team_22_2018.server.view.fxcontroller;

import com.team22.project_team_22_2018.server.GUIServer;
import com.team22.project_team_22_2018.server.MonoThreadClientHandler;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
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
    private ListView<String> listViewUsers;
    @FXML
    private Label labelMaxCountUsers;
    @FXML
    private Label labelCountUsers;
    @FXML
    private Label labelIPServer;
    @FXML
    private Label labelStatus;
    @FXML
    private Button buttonStartServer;
    @FXML
    private Button buttonStopServer;
    @FXML
    private Button buttonReboot;
    @FXML
    private Button buttonDeleteUser;
    @FXML
    private Button buttonEditMaxCountUsers;

    @FXML
    private TextField textFieldCountUsers;
    @FXML
    private TextField textFieldMaxCountUsers;

    private Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");

    private GUIServer server;

    @FXML
    private void initialize() {
        buttonStopServer.setDisable(true);
        buttonReboot.setDisable(true);

        textFieldCountUsers.setEditable(false);
        labelStatus.setText("Статус - Выключен");

        textFieldMaxCountUsers.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                textFieldMaxCountUsers.setText(oldValue);
            }
        });

        try {
            labelIPServer.setText("IP сервера - " + InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            log.error(e);
        }
    }

    @FXML
    private void buttonDeleteUser() {
        int number = listViewUsers.getSelectionModel().getSelectedIndex();
        if (number == -1) {
            return;
        } else {
            server.removeClient(number);
        }
    }

    @FXML
    private void buttonStartServer() {
        buttonStopServer.setDisable(false);
        buttonStartServer.setDisable(true);
        buttonReboot.setDisable(false);
        server = new GUIServer();
        server.startServer();
        labelStatus.setText("Статус - Запущен");

        textFieldCountUsers.setText(String.valueOf(server.getClientsO().size()));

        textFieldMaxCountUsers.setText(String.valueOf(server.getMAX_COUNT_USERS()));

        server.getClientsO().addListener((ListChangeListener<MonoThreadClientHandler>) c -> {
            textFieldCountUsers.setText(String.valueOf(server.getClientsO().size()));
            Platform.runLater(() -> listViewUsers.setItems(server.getClientz()));
        });
    }

    @FXML
    private void buttonStopServer() {
        buttonStopServer.setDisable(true);
        buttonStartServer.setDisable(false);
        buttonReboot.setDisable(true);
        server.stopServer();
        textFieldCountUsers.setText("");
        textFieldMaxCountUsers.setText("");
        labelStatus.setText("Статус - Выключен");
    }

    public void buttonEditMaxCountUsers() {
        if (textFieldMaxCountUsers.getText() == null || textFieldMaxCountUsers.getText().equals("")) {
            server.setMAX_COUNT_USERS(1);
            textFieldMaxCountUsers.setText("1");
        } else {
            server.setMAX_COUNT_USERS(Integer.parseInt(textFieldMaxCountUsers.getText()));
            textFieldMaxCountUsers.setText(textFieldMaxCountUsers.getText());
        }
    }

    public void buttonReboot() {
        buttonStopServer();
        labelStatus.setText("Статус - Выключен");
        buttonStartServer();
        labelStatus.setText("Статус - Запущен");
    }
}
