package com.team22.project_team_22_2018.server;

import com.team22.project_team_22_2018.server.controller.ControllerDB;
import com.team22.project_team_22_2018.server.entity.Goals;
import com.team22.project_team_22_2018.util.Converter;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Log4j
public class MonoThreadClientHandler implements Runnable {

    private static ControllerDB controllerDB = new ControllerDB();
    private static Socket clientDialog;

    @Getter
    private static long lastPingTimestamp;

    private GUIServer server;

    MonoThreadClientHandler(Socket client, GUIServer guiServer) {
        MonoThreadClientHandler.clientDialog = client;
        this.server = guiServer;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(clientDialog.getInputStream());
             DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream())) {
            log.info("Клиент подключился");
            label:
            while (true) {
                if (clientDialog.isClosed()) {
                    server.getClientsO().remove(this);
                    log.info("Клиент отключился");
                    break;
                }
                String type = in.readUTF();
                switch (type) {
                    //паттерн команд
                    case "stop":
                        server.getClientsO().remove(this);
                        log.info("Клиент отключился");
                        break label;
                    case "ping":
                        out.writeUTF("pong");
                        out.flush();
                        lastPingTimestamp = System.currentTimeMillis();
                        log.info("pong");
                        break;
                    case "getModel":
                        out.writeUTF("ok");
                        out.flush();
                        String s = Converter.toJson(controllerDB.getModel());
                        log.info("Отправка модели " /*+ s*/);
                        out.writeUTF(s);
                        out.flush();
                        break;
                    case "setGoal": {
                        out.writeUTF("ok");
                        out.flush();
                        String goalID = in.readUTF();
                        out.writeUTF("ok");
                        out.flush();
                        Goals goal = Converter.toJavaObject(in.readUTF(), Goals.class);
                        controllerDB.setGoal(goalID, goal);
                        log.info("SET MODEL");
                        continue;
                    }
                    case "setGoalDateClose": {
                        out.writeUTF("ok");
                        out.flush();
                        String goalID = in.readUTF();
                        out.writeUTF("ok");
                        out.flush();
                        String dateClose = in.readUTF();
                        controllerDB.setGoalDateClose(goalID, dateClose);
                        break;
                    }
                    case "setPurposeStatus": {
                        out.writeUTF("ok");
                        out.flush();
                        String goalID = in.readUTF();
                        out.writeUTF("ok");
                        out.flush();
                        String status = in.readUTF();
                        controllerDB.setStatus(goalID, status);
                        controllerDB.setCheck(false, goalID);
                        break;
                    }
                    case "setPurposeDateCloseNull": {
                        out.writeUTF("ok");
                        out.flush();
                        String goalID = in.readUTF();
                        controllerDB.setCheck(false, goalID);
                        controllerDB.setGoalDateClose(goalID);
                        break;
                    }
                    case "setStageName": {
                        out.writeUTF("ok");
                        out.flush();
                        String goalID = in.readUTF();
                        out.writeUTF("ok");
                        out.flush();
                        out.writeUTF("ok");
                        out.flush();
                        String goalStageID = in.readUTF();
                        String name = in.readUTF();
                        controllerDB.setStageName(goalID, goalStageID, name);
                        break;
                    }
                    case "addGoalDB":
                        out.writeUTF("ok");
                        out.flush();
                        String userID1 = in.readUTF();
                        out.writeUTF("ok");
                        out.flush();
                        addGoal(in, userID1);
                        log.info("ADD GOAL");
                        break;
                    case "addGoalStage":
                        out.writeUTF("ok");
                        out.flush();
                        addGoalStage(in, out);
                        break;
                    case "removeGoal":
                        out.writeUTF("ok");
                        out.flush();
                        controllerDB.removeGoal(in.readUTF());
                        log.info("REMOVE GOAL");
                        break;
                    case "removeGoalStage": {
                        out.writeUTF("ok");
                        out.flush();
                        String uuidPurpose = in.readUTF();
                        out.writeUTF("ok");
                        out.flush();
                        String uuidPurposeStage = in.readUTF();
                        controllerDB.removeGoalStage(uuidPurposeStage);
                        break;
                    }
                    case "login": {
                        out.writeUTF("ok");
                        out.flush();
                        String login = in.readUTF();
                        out.writeUTF("ok");
                        out.flush();
                        String password = in.readUTF();
                        if (controllerDB.checkLogin(login, password)) {
                            out.writeUTF("true");
                            out.flush();
                        } else {
                            out.writeUTF("false");
                            out.flush();
                        }
                        break;
                    }
                    case "registr": {
                        out.writeUTF("ok");
                        out.flush();
                        String login = in.readUTF();
                        out.writeUTF("ok");
                        out.flush();
                        String password = in.readUTF();
                        if (controllerDB.registration(login, password)) {
                            out.writeUTF("true");
                            out.flush();
                        } else {
                            out.writeUTF("false");
                            out.flush();
                        }
                        log.info("Регистрация нового пользователя");
                        break;
                    }
                }
                //endregion
            }
        } catch (IOException x) {
            server.getClientsO().remove(this);
            log.error(x);
        }
    }

    private static void addGoal(DataInputStream in, String userID) {
        try {
            controllerDB.addGoal(in.readUTF(), userID);
        } catch (IOException x) {
            log.error(x);
        }
    }

    private static void addGoalStage(DataInputStream in, DataOutputStream out) {
        String nameStage;
        String status;
        String uuid;
        try {
            String uuidPurpose = in.readUTF();
            out.writeUTF("ok");
            out.flush();
            nameStage = in.readUTF();
            out.writeUTF("ok");
            out.flush();
            status = in.readUTF();
            out.writeUTF("ok");
            out.flush();
            uuid = in.readUTF();

            controllerDB.addGoalStage(uuidPurpose, nameStage, status, uuid);
        } catch (IOException x) {
            log.error(x);
        }
    }

    public void stop() {
        try {
            clientDialog.close();
        } catch (IOException e) {
            log.error(e);
        }

    }

    @Override
    public String toString() {
        return String.valueOf(clientDialog.getInetAddress());
    }
}
