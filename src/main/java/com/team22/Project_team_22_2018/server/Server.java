package com.team22.project_team_22_2018.server;

import com.team22.project_team_22_2018.server.controller.ControllerModel;
import com.team22.project_team_22_2018.server.models.Account;
import com.team22.project_team_22_2018.server.models.Purpose;
import com.team22.project_team_22_2018.server.util.ServerRuntimeHolder;
import com.team22.project_team_22_2018.util.Converter;
import lombok.extern.log4j.Log4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Log4j
public class Server {
    private static ControllerModel controllerModel = ServerRuntimeHolder.getControllerModelHolder();

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(22222);
             Socket socket = serverSocket.accept();
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            log.info("Клиент подключился");

            int counter = 0;
            while (true) {
                if (counter > 1_000_000) {
                    counter = 0;
                    log.info("Время ожидания запроса превышено");
                    break;
                }
                counter++;

                String type = in.readUTF();
                if (type.equals("exit") || socket.isClosed()) {
                    counter = 0;
                    log.info("Клиент отключился");
                    break;
                }
                //region GET methods
                if (type.equals("getModel")) {

                    counter = 0;
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
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();
                    int index = Integer.parseInt(in.readUTF());
                    out.writeUTF("ok");
                    out.flush();
                    Purpose purpose = Converter.toJavaObject(in.readUTF(), Purpose.class);
                    controllerModel.setPurpose(index, purpose);
                }
                if (type.equals("setPurposeDateClose")) {
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();
                    int index = Integer.parseInt(in.readUTF());
                    out.writeUTF("ok");
                    out.flush();
                    String dateClose = in.readUTF();
                    controllerModel.setPurposeDateClose(index, dateClose);
                }
                if (type.equals("setPurposeDateCloseNull")) {
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();
                    int index = Integer.parseInt(in.readUTF());
                    controllerModel.setPurposeDateClose(index);
                }
                if (type.equals("setStageName")) {
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();
                    int indexPurpose = Integer.parseInt(in.readUTF());
                    out.writeUTF("ok");
                    out.flush();
                    int indexPurposeStage = Integer.parseInt(in.readUTF());
                    out.writeUTF("ok");
                    out.flush();
                    String name = in.readUTF();
                    controllerModel.setStageName(indexPurpose, indexPurposeStage, name);
                }
                if (type.equals("setPurposes")) {
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();
                    controllerModel.setPurposes(Converter.toJavaObject(in.readUTF(), Account.class).getPurposes());
                }
                //endregion

                //region ADD methods
                if (type.equals("addPurpose")) {
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();
                    addPurpose(in, out);
                }
                if (type.equals("addPurposeStage")) {
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();
                    addPurposeStage(in, out);
                }
                //endregion

                //region REMOVE methods
                if (type.equals("removePurpose")) {
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();
                    controllerModel.removePurpose(Integer.parseInt(in.readUTF()));
                }
                if (type.equals("removePurposeStage")) {
                    counter = 0;
                    out.writeUTF("ok");
                    out.flush();

                    int indexPurpose = Integer.parseInt(in.readUTF());
                    out.writeUTF("ok");
                    out.flush();

                    int indexPurposeStage = Integer.parseInt(in.readUTF());
                    controllerModel.removePurposeStage(indexPurpose, indexPurposeStage);
                }
                //endregion
            }
        } catch (Exception x) {
            log.error(x);
        }
    }

    private static void addPurpose(DataInputStream in,
                                   DataOutputStream out) {
        try {
            controllerModel.addPurpose(in.readUTF());
        } catch (Exception x) {
            log.error(x);
        }
    }

    private static void addPurposeStage(DataInputStream in,
                                        DataOutputStream out) {
        int indexPurpose;
        String nameStage;
        String status;
        try {
            indexPurpose = Integer.parseInt(in.readUTF());
            out.writeUTF("ok");
            out.flush();
            nameStage = in.readUTF();
            out.writeUTF("ok");
            out.flush();
            status = in.readUTF();
            controllerModel.addPurposeStage(indexPurpose, nameStage, status);
        } catch (Exception x) {
            log.error(x);
        }
    }
}

