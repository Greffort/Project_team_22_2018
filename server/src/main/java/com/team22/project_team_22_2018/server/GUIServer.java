package com.team22.project_team_22_2018.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.val;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j
public class GUIServer {

    private long CLIENT_TIMEOUT = 10_000;
    @Getter
    @Setter
    private int MAX_COUNT_USERS = 100;

    private ExecutorService executeIt;

    @Getter
    private ObservableList<MonoThreadClientHandler> clientsO = FXCollections.observableArrayList();

    private ServerSocket server;

    public GUIServer() {
        try {
            this.server = new ServerSocket(22222);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void startServer() {
        if (this.server == null || this.server.isClosed()) {
            try {
                this.executeIt = Executors.newFixedThreadPool(MAX_COUNT_USERS);
                this.server = new ServerSocket(22222);
            } catch (IOException e) {
                log.error(e);
            }
        }
        if (executeIt == null || executeIt.isShutdown()) {
            this.executeIt = Executors.newFixedThreadPool(MAX_COUNT_USERS);
        }

        Thread thread = new Thread(() -> {
            try {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    final MonoThreadClientHandler client = new MonoThreadClientHandler(socket, this);
                    executeIt.execute(client);
                    clientsO.add(client);
                    log.info(clientsO.size());
                }
                executeIt.shutdown();
            } catch (IOException e) {
                log.error(e);
            }
        });
        thread.start();

        final Thread thread1 = new Thread(() -> {
            while (true) {
                val timestamp = System.currentTimeMillis();
                for (int i = 0; i < clientsO.size(); i++) {
                    val clientTimestamp = MonoThreadClientHandler.getLastPingTimestamp();
                    if (timestamp - clientTimestamp < CLIENT_TIMEOUT) {
                        continue;
                    } else {
                        clientsO.remove(i);
                        try {
                            removeClient(i);
                        } catch (Exception e) {
                            log.error(String.format("Клиент номер - %s недоступен", i));
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        });
        thread1.setDaemon(true);
        thread1.start();
    }

    public ObservableList<String> getClientz() {
        ObservableList<String> myClientsO = FXCollections.observableArrayList();
        for (MonoThreadClientHandler clientHandler : clientsO) {
            myClientsO.add(clientHandler.toString());
        }
        return myClientsO;
    }

    public void removeClient(int index) {
        final MonoThreadClientHandler monoThreadClientHandler = clientsO.get(index);
        monoThreadClientHandler.stop();
    }

    public void stopServer() {
        try {
            for (int i = 0; i < clientsO.size(); i++) {
                removeClient(i);
            }
            server.close();
            executeIt.shutdownNow();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
