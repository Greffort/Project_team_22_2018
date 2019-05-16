package com.team22.project_team_22_2018.client.controller;

import com.team22.project_team_22_2018.client.view.Observer;
import com.team22.project_team_22_2018.client.view.util.model.MyModel;
import com.team22.project_team_22_2018.client.view.util.model.MyObject;
import com.team22.project_team_22_2018.client.view.util.model.MySubObject;
import com.team22.project_team_22_2018.client.view.util.model.Observable;
import com.team22.project_team_22_2018.client.view.util.tableview.TableViewData;
import com.team22.project_team_22_2018.util.Converter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import lombok.extern.log4j.Log4j;
import lombok.val;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@Log4j
public class ControllerView implements Observable, Runnable, AutoCloseable {

    @JsonIgnore
    private List<Observer> observers = new ArrayList<>();

    private MyModel myModel;

    private Socket socket;
    private DataOutputStream outD;
    private DataInputStream inD;

    public ControllerView() {
        log.info("Инициализация ControllerView");
        if (!connect()) {
            while (socket == null) {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void updateModel() {
        try {
            outD.writeUTF("getModel");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                String s = inD.readUTF();
                log.info("Обновление данных на клиенте "/* + s*/);
                myModel = Converter.toJavaObject(s, MyModel.class);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    //region GET methods
    public synchronized MyModel getMyModel() {
        return this.myModel;
    }

    public synchronized MyModel getModel() {
        try {
            outD.writeUTF("getModel");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                return Converter.toJavaObject(inD.readUTF(), MyModel.class);
            }
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
        return null;
    }

    public ObservableList<String> getMyObjectsName() {
        return this.myModel.getGoals();
    }

    public ObservableList<MyObject> getMyObjects() {
        return this.myModel.getGoals("костыль");
    }

    public String getNamePurpose(final int index) {
        return this.myModel.getPurpose(getUUIDMyObject(index)).getName();
    }

    public String getCriterionCompleted(final int index) {
        return this.myModel.getPurpose(getUUIDMyObject(index)).getCriterionCompleted();
    }

    public String getDescription(final int index) {
        return this.myModel.getPurpose(getUUIDMyObject(index)).getDescription();
    }

    public String getCreateDate(final int index) {
        return this.myModel.getPurpose(getUUIDMyObject(index)).getDateOpen();
    }

    public String getCloseDate(final int index) {
        if (this.myModel.getPurpose(getUUIDMyObject(index)).getDateClose() == null) {
            return null;
        } else {
            return this.myModel.getPurpose(getUUIDMyObject(index)).getDateClose();
        }
    }

    public String getDeadlineDate(final int index) {
        return this.myModel.getPurpose(getUUIDMyObject(index)).getDeadline();
    }

    public String getStatus(final int index) {
        return this.myModel.getPurpose(getUUIDMyObject(index)).getStatus();
    }

    public String getDateOpen(final int index) {
        return this.myModel.getPurpose(getUUIDMyObject(index)).getDateOpen();
    }

    public String getCriticalTime(final int index) {
        return String.valueOf(DAYS.between(
                LocalDate.parse(this.myModel.getPurpose(getUUIDMyObject(index)).getCriticalTime()),
                LocalDate.parse(this.myModel.getPurpose(getUUIDMyObject(index)).getDeadline())
        ));
    }

    public String getUUIDMyObject(final int index) {
        return this.myModel.getGoalI(index).getUuid();
    }

    public String getUUIDMySubObject(final int indexGoal, final int indexSubObject) {
        return this.myModel.getGoalI(indexGoal).getGoalStageI(indexSubObject).getUuid();
    }

    public ObservableList<String> getStageNames(final int index) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<MySubObject> list = this.myModel.getPurpose(getUUIDMyObject(index)).getGoalStages();
        for (MySubObject purposeStage : list) {
            observableList.add(purposeStage.getName());
        }
        return observableList;
    }

    public ObservableList<String> getStageStatuses(final int index) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<MySubObject> list = this.myModel.getPurpose(getUUIDMyObject(index)).getGoalStages();
        for (MySubObject purposeStage : list) {
            observableList.add(purposeStage.getCompleted());
        }
        return observableList;
    }
    //endregion

    //region SET methods
    public synchronized void setGoal(
            final int index,
            final ObservableList<TableViewData> purposeStages,
            final String name,
            final String criterionCompleted,
            final String description,
            final String status,
            final String deadline,
            final String dateOpen,
            final String criticalTime,
            final boolean flag
    ) {
        synchronized (this) {
            String critical = String.valueOf(LocalDate.parse(deadline).minusDays(Long.parseLong(criticalTime)));

            log.info("Изменение цели. Имя цели: " + name);
            try {
                outD.writeUTF("setGoal");
                outD.flush();
                if (inD.readUTF().equals("ok")) {
                    outD.writeUTF(getUUIDMyObject(index));
                    outD.flush();
                } else return;
                if (inD.readUTF().equals("ok")) {
                    ArrayList<MySubObject> list = new ArrayList<>();
                    for (TableViewData aTableViewData : purposeStages) {
                        list.add(new MySubObject(aTableViewData.getStage(), aTableViewData.getStatus(), aTableViewData.getUuid()));
                    }
                    int a;
                    if (flag) {
                        a = 1;
                    } else {
                        a = 0;
                    }
                    MyObject myObject = new MyObject(
                            list, name,
                            criterionCompleted, description,
                            status, deadline,
                            dateOpen, critical, getUUIDMyObject(index),
                            a
                    );
                    outD.writeUTF(Converter.toJson(myObject));
                    outD.flush();
                } else return;
                notifyAllObservers();
            } catch (IOException e) {
                log.error(e);
                getAlertReconnect();
            }
        }
    }

    public synchronized void setGoal(
            final String uuid,
            final List<MySubObject> purposeStages,
            final String name,
            final String criterionCompleted,
            final String description,
            final String status,
            final String deadline,
            final String dateOpen,
            final String criticalTime,
            final boolean flag
    ) {
        String critical = String.valueOf(LocalDate.parse(deadline).minusDays(Long.parseLong(criticalTime)));
        log.info("Изменение цели. Имя цели: " + name);
        try {
            outD.writeUTF("setGoal");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(uuid);
                outD.flush();
            } else return;

            if (inD.readUTF().equals("ok")) {
                int a;
                if (flag) {
                    a = 1;
                } else {
                    a = 0;
                }
                MyObject myObject = new MyObject(
                        purposeStages, name,
                        criterionCompleted, description,
                        status, deadline,
                        dateOpen, critical, uuid,
                        a
                );
                outD.writeUTF(Converter.toJson(myObject));
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }

    public synchronized void setPurposeDateClose(final int index, final String dateClose) {
        try {
            outD.writeUTF("setGoalDateClose");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(getUUIDMyObject(index)));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(dateClose);
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }

    public synchronized void setPurposeDateClose(final String uuid, final String dateClose) {
        try {
            outD.writeUTF("setGoalDateClose");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(uuid);
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(dateClose);
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }

    public synchronized void setPurposeDateCloseNull(final int index) {
        try {
            outD.writeUTF("setPurposeDateCloseNull");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(getUUIDMyObject(index));
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }

    public synchronized void setStageName(final int indexPurpose, final int indexPurposeStage, final String name) {
        try {
            outD.writeUTF("setStageName");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(getUUIDMyObject(indexPurpose));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(getUUIDMySubObject(indexPurpose, indexPurposeStage));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(name);
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }

    public synchronized void setPurposeStatus(final int index, final String status) {
        try {
            outD.writeUTF("setPurposeStatus");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(getUUIDMyObject(index)));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(status);
                outD.flush();
            } else return;
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }

    public synchronized void setPurposeStatus(final String uuid, final String status) {
        try {
            outD.writeUTF("setPurposeStatus");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(uuid);
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(status);
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }

    public synchronized void setCheck(final boolean bool, MyObject myObject) {
        setGoal(
                myObject.getUuid(),
                myModel.getPurpose(myObject.getUuid()).getGoalStages(),
                myModel.getPurpose(myObject.getUuid()).getName(),
                myModel.getPurpose(myObject.getUuid()).getCriterionCompleted(),
                myModel.getPurpose(myObject.getUuid()).getDescription(),
                myModel.getPurpose(myObject.getUuid()).getStatus(),
                myModel.getPurpose(myObject.getUuid()).getDeadline(),
                myModel.getPurpose(myObject.getUuid()).getDateOpen(),
                String.valueOf(DAYS.between(
                        LocalDate.parse(myModel.getPurpose(myObject.getUuid()).getCriticalTime()),
                        LocalDate.parse(myModel.getPurpose(myObject.getUuid()).getDeadline())
                )),
                bool
        );
    }

    public synchronized void addGoal(
            final ObservableList<TableViewData> purposeStages, final String name,
            final String criterionCompleted, final String description,
            final String status, final String deadline,
            final String dateOpen, final String criticalTime) {
        try {
            log.info("Добавление новой цели");
            outD.writeUTF("addGoalDB");
            outD.flush();

            if (inD.readUTF().equals("ok")) {
                ObservableList<MySubObject> mySubObjects = FXCollections.observableArrayList();
                for (TableViewData tableViewData : purposeStages) {
                    mySubObjects.add(new MySubObject(tableViewData.getStage(), tableViewData.getStatus(), UUID.randomUUID().toString()));
                }
                String critical = String.valueOf(LocalDate.parse(deadline).minusDays(Long.parseLong(criticalTime)));
                MyObject myObject = new MyObject(
                        mySubObjects, name, criterionCompleted, description,
                        status, deadline, dateOpen, critical, UUID.randomUUID().toString(),
                        0
                );
                outD.writeUTF(myModel.getUuid());
                outD.flush();
                inD.readUTF().equals("ok");

                outD.writeUTF(Converter.toJson(myObject));
                outD.flush();
            }
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }
    //endregion methods

    //region REMOVE methods
    public synchronized void removePurpose(final int index) {
        try {
            log.info("Удаление задачи");
            outD.writeUTF("removeGoal");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(getUUIDMyObject(index));
                outD.flush();
            }
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }

    public synchronized void removePurposeStage(final int indexPurpose, final int indexPurposeStage) {
        try {
            log.info("Удаление подзадачи");
            outD.writeUTF("removeGoalStage");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(getUUIDMyObject(indexPurpose));
                outD.flush();
                if (inD.readUTF().equals("ok")) {
                    outD.writeUTF(getUUIDMySubObject(indexPurpose, indexPurposeStage));
                    outD.flush();
                }
            }
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();
        }
    }
    //endregion

    //region SAVE methods
    public void saveAs() {
        try {
            log.info("СОхранение в файл на клиенте");
            val fileChooser = new FileChooser();
            val file = fileChooser.showSaveDialog(null);
            if (file != null) {
                FileWriter writer = new FileWriter(file);
                outD.writeUTF("getModel");
                outD.flush();
                if (inD.readUTF().equals("ok")) {
                    writer.write(inD.readUTF());
                    writer.flush();
                }
            }
        } catch (IOException e) {
            log.error(e);
            getAlertReconnect();

        }
    }
    //endregion

    //region UTIL
    public int getLargerNumber(MyObject myObject) {
        if (myObject.getStatus().equals("Просроченная")) {
            return 5;
        }
        if (myObject.getStatus().equals("Горящая")) {
            return 4;
        }
        if (myObject.getStatus().equals("Обычная")) {
            return 3;
        }
        if (myObject.getStatus().equals("В ожидании")) {
            return 2;
        } else {
            return 1;
        }
    }

    public void sortByStatus() {
        ObservableList<MyObject> observableList = getMyObjects();
        for (int i = observableList.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (getLargerNumber(observableList.get(j + 1)) > getLargerNumber(observableList.get(j))) {
                    val tmp = observableList.get(j + 1);
                    observableList.set(j + 1, observableList.get(j));
                    observableList.set(j, tmp);
                }
            }
        }
        myModel.setGoals(observableList);
    }
    //endregion

    //region CONNECT
    public synchronized boolean login(String login, String password) {
        //отправляем на сервер логин и пароль, поочереди
        synchronized (this) {
            if (socket != null) {
                try {
                    outD.writeUTF("login");
                    outD.flush();
                    if (inD.readUTF().equals("ok")) {
                        outD.writeUTF(login);
                        outD.flush();
                    }
                    if (inD.readUTF().equals("ok")) {
                        outD.writeUTF(password);
                        outD.flush();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Project team 22");
                        alert.setHeaderText("Неверный логин или пароль");
                        alert.show();
                    }
                    if (inD.readUTF().equals("true")) {
                        return true;
                    }
                } catch (IOException e) {
                    log.error(e);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Project team 22");
                    alert.setHeaderText("Отсутствует подключение к серверу.");
                    alert.show();
                    return false;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Project team 22");
                alert.setHeaderText("Отсутствует подключение к серверу.");
                alert.show();
            }
            return false;
        }
        //возвращаем true если подключение удалось
    }

    public synchronized boolean registration(String login, String password) {
        synchronized (this) {
            if (socket != null) {
                try {
                    outD.writeUTF("registr");
                    outD.flush();
                    if (inD.readUTF().equals("ok")) {
                        outD.writeUTF(login);
                        outD.flush();
                    }
                    if (inD.readUTF().equals("ok")) {
                        outD.writeUTF(password);
                        outD.flush();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Project team 22");
                        alert.setHeaderText("Важно сообщение.");
                        alert.show();
                    }
                    if (inD.readUTF().equals("true")) {
                        return true;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Project team 22");
                        alert.setHeaderText("Данный логин занят. Попробуйте другой.");
                        alert.show();
                    }
                } catch (IOException e) {
                    log.error(e);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Project team 22");
                    alert.setHeaderText("Отсутствует подключение к серверу.");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Project team 22");
                alert.setHeaderText("Отсутствует подключение к серверу.");
                alert.show();
            }
            return false;
        }
    }

    public synchronized boolean connect() {
        try {
            log.info("Подключение к серверу - выполняется");
            socket = new Socket(InetAddress.getLocalHost(), 22222);
            outD = new DataOutputStream(socket.getOutputStream());
            inD = new DataInputStream(socket.getInputStream());
            log.info("Подключение к серверу - успешно");
            return true;
        } catch (IOException e) {
            log.error("Подключение к серверу - ошибка. " + e);
            getAlertReconnect();
            return false;
        }
    }

    private synchronized void reconnect() {
        final Thread thread = new Thread(() -> {
            int counter = 0;
            while (true) {
                counter++;
                log.info("Сервер недоступен. Попытка подключения - " + counter);
                try {
                    log.info("Подключение к серверу - выполняется");
                    socket = new Socket(InetAddress.getLocalHost(), 22222);
                    outD = new DataOutputStream(socket.getOutputStream());
                    inD = new DataInputStream(socket.getInputStream());
                    log.info("Подключение к серверу - успешно");

                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Project team 22");
                        alert.setHeaderText("Подключение установленно");
                        alert.show();
                    });
                    break;
                } catch (IOException e) {
                    log.error("Подключение к серверу - ошибка. " + e);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void getAlertReconnect() {
        try {
            close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Project team 22");
            alert.setHeaderText("Соединение с сервером отсутствует. Востановить соедиенние?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    log.info("Попытка переподключения");
                    reconnect();
                } else if (rs == ButtonType.CANCEL) {
                    System.exit(1);
                }
            });
        } catch (IllegalStateException ignored) {
        }

    }

    public void close() {
        if (socket != null) {
            if (!socket.isConnected()) {
                try {
                    outD.writeUTF("stop");
                    outD.flush();
                } catch (IOException e) {
                    log.error(e);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
        if (outD != null) {
            if (!socket.isConnected()) {
                try {
                    outD.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
        if (inD != null) {
            if (!socket.isConnected()) {
                try {
                    inD.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
        log.info("Соединение с сервером отсутствует");
    }
    //endregion

    @Override
    public void run() {
        log.info("PING_PONG_START");
        if (socket != null) {
            while (!socket.isClosed()) {
                try {
                    Thread.sleep(500000000);
                    synchronized (this) {
                        outD.writeUTF("ping");
                        outD.flush();
                        log.info("PING");
                        if (inD.readUTF().equals("pong")) {
                            log.info("_PONG");
                        } else {
                            new IOException();
                        }
                    }
                } catch (InterruptedException ignored) {
                } catch (IOException e) {
                    log.error("PING_PONG" + e);
                    Platform.runLater(this::getAlertReconnect);
                    break;
                }
            }
        }
    }

    @Override
    public void registerObserver(final Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        updateModel();
        for (Observer observer : observers) {
            observer.handleEvent();
        }
    }
}
