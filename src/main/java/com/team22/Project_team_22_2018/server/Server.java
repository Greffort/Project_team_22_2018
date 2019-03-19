package com.team22.project_team_22_2018.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j
public class Server {

    private static long CLIENT_TIMEOUT = 10_000;
    @Getter
    @Setter
    private static int MAX_COUNT_USERS = 100;


    private final static ExecutorService executeIt = Executors.newFixedThreadPool(MAX_COUNT_USERS);
    @Getter
    private final static List<MonoThreadClientHandler> clients = new ArrayList<>();

    public static void main(@NotNull final String[] args) {

        try (ServerSocket server = new ServerSocket(22222);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (!server.isClosed()) {
                if (br.ready()) {
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("stop")) {
                        server.close();
                        break;
                    }
                    if (serverCommand.equalsIgnoreCase("users")) {
                        //даем список всех юзеров
                        server.close();
                        break;
                    }
                }
                Socket socket = server.accept();
                final MonoThreadClientHandler client = new MonoThreadClientHandler(socket);
                executeIt.execute(client);
                clients.add(client);
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Thread thread = new Thread(() -> {
            while (true) {
                val timestamp = System.currentTimeMillis();

                for (MonoThreadClientHandler client : clients) {
                    val clientTimestamp = MonoThreadClientHandler.getLastPingTimestamp();
                    if (timestamp - clientTimestamp < CLIENT_TIMEOUT) {
                        continue;
                    }
                    clients.remove(client);
                    // todo клиент отвалился
                    log.info("клиент отвалился");
                }
                try {
                    Thread.sleep(CLIENT_TIMEOUT);
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}

