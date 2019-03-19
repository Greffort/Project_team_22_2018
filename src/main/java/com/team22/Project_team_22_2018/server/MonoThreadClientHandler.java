package com.team22.project_team_22_2018.server;

import com.team22.project_team_22_2018.server.controller.ControllerModel;
import com.team22.project_team_22_2018.server.models.Account;
import com.team22.project_team_22_2018.server.models.Purpose;
import com.team22.project_team_22_2018.server.util.ServerRuntimeHolder;
import com.team22.project_team_22_2018.util.Converter;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Log4j
public class MonoThreadClientHandler implements Runnable {

    private static ControllerModel controllerModel = new ControllerModel();
    private static Socket clientDialog;

    @Getter
    private static long lastPingTimestamp;

    MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(clientDialog.getInputStream());
             DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream())) {
            log.info("Клиент подключился");

            while (true) {
                if (clientDialog.isClosed()) {
                    break;
                }

                String type = in.readUTF();
                if (type.equals("stop") || clientDialog.isClosed()) {
                    log.info("Клиент отключился");
                    break;
                }
                //region GET methods
                if (type.equals("getModel")) {
                    out.writeUTF("ok");
                    out.flush();
                    String s = Converter.toJson(controllerModel.getModel());
                    log.info("Отправка модели " + s);
                    out.writeUTF(s);
                    out.flush();
                }
                //endregion

                //region SET methods
                if (type.equals("setPurpose")) {

                    out.writeUTF("ok");
                    out.flush();
                    String uuid = in.readUTF();
                    out.writeUTF("ok");
                    out.flush();
                    Purpose purpose = Converter.toJavaObject(in.readUTF(), Purpose.class);
                    controllerModel.setPurpose(uuid, purpose);
                    continue;
                }
                if (type.equals("setPurposeDateClose")) {

                    out.writeUTF("ok");
                    out.flush();
                    String uuid = in.readUTF();
//                        int index = Integer.parseInt(in.readUTF());
                    out.writeUTF("ok");
                    out.flush();
                    String dateClose = in.readUTF();
                    controllerModel.setPurposeDateClose(uuid, dateClose);
                }
                if (type.equals("setPurposeStatus")) {

                    out.writeUTF("ok");
                    out.flush();
                    String uuid = in.readUTF();
//                        int index = Integer.parseInt(in.readUTF());
                    out.writeUTF("ok");
                    out.flush();
                    String status = in.readUTF();
                    controllerModel.setStatus(uuid, status);
                }
                if (type.equals("setPurposeDateCloseNull")) {

                    out.writeUTF("ok");
                    out.flush();
//                        int index = Integer.parseInt(in.readUTF());

                    String uuid = in.readUTF();
                    controllerModel.setPurposeDateClose(uuid);
                }
                if (type.equals("setStageName")) {

                    out.writeUTF("ok");
                    out.flush();
//                        int indexPurpose = Integer.parseInt(in.readUTF());

                    String uuidPurpose = in.readUTF();
                    out.writeUTF("ok");
                    out.flush();
                    out.writeUTF("ok");
                    out.flush();

                    String uuidPurposeStage = in.readUTF();
                    String name = in.readUTF();
                    controllerModel.setStageName(uuidPurpose, uuidPurposeStage, name);
                }
                if (type.equals("setPurposes")) {

                    out.writeUTF("ok");
                    out.flush();
                    controllerModel.setPurposes(Converter.toJavaObject(in.readUTF(), Account.class).getPurposes());
                }
                //endregion

                //region ADD methods
                if (type.equals("addPurpose")) {

                    out.writeUTF("ok");
                    out.flush();
                    addPurpose(in, out);
                }
                if (type.equals("addPurposeStage")) {

                    out.writeUTF("ok");
                    out.flush();
                    addPurposeStage(in, out);
                }
                //endregion

                //region REMOVE methods
                if (type.equals("removePurpose")) {

                    out.writeUTF("ok");
                    out.flush();
                    controllerModel.removePurpose(in.readUTF());
                }
                if (type.equals("removePurposeStage")) {

                    out.writeUTF("ok");
                    out.flush();

                    String uuidPurpose = in.readUTF();
                    out.writeUTF("ok");
                    out.flush();

                    String uuidPurposeStage = in.readUTF();

                    controllerModel.removePurposeStage(uuidPurpose, uuidPurposeStage);
                }
                if (type.equals("ping")) {
                    out.writeUTF("pong");
                    out.flush();

                    lastPingTimestamp = System.currentTimeMillis();
                }

                //endregion

                //region LOGIN&REGISTRATION
                if (type.equals("login")) {
                    out.writeUTF("ok");
                    out.flush();
                    String login = in.readUTF();
                    out.writeUTF("ok");
                    out.flush();
                    String password = in.readUTF();

                    if (controllerModel.checkLogin(login, password)) {
                        out.writeUTF("true");
                        out.flush();
                    } else {
                        out.writeUTF("false");
                        out.flush();
                    }
                }

                if (type.equals("registr")) {
                    out.writeUTF("ok");
                    out.flush();
                    String login = in.readUTF();
                    out.writeUTF("ok");
                    out.flush();
                    String password = in.readUTF();

                    if (controllerModel.registration(login, password)) {
                        out.writeUTF("true");
                        out.flush();
                    } else {
                        out.writeUTF("false");
                        out.flush();
                    }
                }

                //endregion

                //region SAVE
                if (type.equals("save")) {
                    out.writeUTF("ok");
                    out.flush();
                    controllerModel.save(in.readUTF());
                }


                //endregion
            }
        } catch (IOException x) {
            log.error(x);
        }
    }

    private static void addPurpose(DataInputStream in,
                                   DataOutputStream out) {
        try {
            controllerModel.addPurpose(in.readUTF());
        } catch (IOException x) {
            log.error(x);
        }
    }

    private static void addPurposeStage(DataInputStream in,
                                        DataOutputStream out) {
        int indexPurpose;
        String nameStage;
        String status;
        String uuid;
        try {
//            indexPurpose = Integer.parseInt(in.readUTF());
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

            controllerModel.addPurposeStage(uuidPurpose, nameStage, status, uuid);
        } catch (IOException x) {
            log.error(x);
        }
    }


}
