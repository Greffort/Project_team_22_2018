package com.team22.project_team_22_2018.client.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team22.project_team_22_2018.client.view.Observer;
import com.team22.project_team_22_2018.client.view.util.model.MyModel;
import com.team22.project_team_22_2018.client.view.util.model.MyObject;
import com.team22.project_team_22_2018.client.view.util.model.MySubObject;
import com.team22.project_team_22_2018.client.view.util.model.Observable;
import com.team22.project_team_22_2018.client.view.util.tableview.TableViewData;
import com.team22.project_team_22_2018.util.Converter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import lombok.extern.log4j.Log4j;
import lombok.val;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Log4j
public class ControllerView implements Observable {

    @JsonIgnore
    private List<Observer> observers = new ArrayList<>();

    private MyModel myModel;

    //перевел создание сокета в конструктор по совету idea, проверить работоспособность здесь
    private DataOutputStream outD;
    private DataInputStream inD;

    public ControllerView() {
        try {
            log.info("Инициализация клиента");
            Socket socket = new Socket(InetAddress.getLocalHost(), 22222);
            outD = new DataOutputStream(socket.getOutputStream());
            inD = new DataInputStream(socket.getInputStream());
            myModel = getModel();
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void updateModel() {
        try {
            outD.writeUTF("getModel");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                String s = inD.readUTF();
                log.info("Обновление данных на клиенте " + s);
                myModel = Converter.toJavaObject(s, myModel.getClass());
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    //region GET methods
    public MyModel getModel() {
        try {
            log.info("Загрузка данных с сервера");
            outD.writeUTF("getModel");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                return Converter.toJavaObject(inD.readUTF(), MyModel.class);
            }
        } catch (IOException e) {
            log.error(e);
        }
        return null;
    }

    public ObservableList<String> getMyObjects() {
        return myModel.getPurposes();
    }

    public String getNamePurpose(final int index) {
        return myModel.getPurpose(index).getName();
    }

    public String getCriterionCompleted(final int index) {
        return myModel.getPurpose(index).getCriterionCompleted();
    }

    public String getDescription(final int index) {
        return this.myModel.getPurpose(index).getDescription();
    }

    public String getCreateDate(final int index) {
        return this.myModel.getPurpose(index).getDateOpen();
    }

    public String getCloseDate(final int index) {
        if (this.myModel.getPurpose(index).getDateClose() == null) {
            return null;
        } else {
            return this.myModel.getPurpose(index).getDateClose();
        }
    }

    public String getDeadlineDate(final int index) {
        return this.myModel.getPurpose(index).getDeadline();
    }

    public String getStatus(final int index) {
        return this.myModel.getPurpose(index).getStatus();
    }

    public String getDateOpen(final int index) {
        return this.myModel.getPurpose(index).getDateOpen();
    }

    public String getCriticalTime(final int index) {
        return this.myModel.getPurpose(index).getCriticalTime();
    }


    public ObservableList<String> getStageNames(final int index) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<MySubObject> list = this.myModel.getPurpose(index).getPurposeStages();
        for (MySubObject purposeStage : list) {
            observableList.add(purposeStage.getName());
        }
        return observableList;
    }

    public ObservableList<String> getStageStatuses(final int index) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<MySubObject> list = this.myModel.getPurpose(index).getPurposeStages();
        for (MySubObject purposeStage : list) {
            observableList.add(purposeStage.getCompleted());
        }
        return observableList;
    }
    //endregion

    //region SET methods
    public void setPurpose(
            final int index,
            final ObservableList<TableViewData> purposeStages,
            final String name,
            final String criterionCompleted,
            final String description,
            final String status,
            final String deadline,
            final String dateOpen,
            final String criticalTime
    ) {
        log.info("Изменение цели. Имя цели: " + name);
        try {
            outD.writeUTF("setPurpose");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(index));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                ArrayList<MySubObject> list = new ArrayList<>();
                for (TableViewData aTableViewData : purposeStages) {
                    list.add(new MySubObject(aTableViewData.getStage(), aTableViewData.getStatus()));
                }
                MyObject myObject = new MyObject(
                        list, name,
                        criterionCompleted, description,
                        status, deadline,
                        dateOpen, criticalTime
                );
                outD.writeUTF(Converter.toJson(myObject));
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void setPurposeStages(final int index, final ObservableList<TableViewData> purposesStage) {
        setPurpose(
                index,
                purposesStage,
                getNamePurpose(index),
                getCriterionCompleted(index),
                getDescription(index),
                getStatus(index),
                getDeadlineDate(index),
                getDateOpen(index),
                getCriticalTime(index)
        );
    }

    public void setPurposeDateClose(final int index, final String dateClose) {
        try {
            outD.writeUTF("setPurposeDateClose");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(index));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(dateClose);
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
        }
    }
    public void setPurposeDateCloseNull(final int index) {
        try {
            outD.writeUTF("setPurposeDateCloseNull");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(index));
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void setStageName(final int indexPurpose, final int indexPurposeStage, final String name) {
        try {
            outD.writeUTF("setStageName");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(indexPurpose));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(indexPurposeStage));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(name);
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
        }
    }
    //endregion

    //region ADD methods
    public void addPurpose(
            final ObservableList<TableViewData> purposeStages, final String name,
            final String criterionCompleted, final String description,
            final String status, final String deadline,
            final String dateOpen, final String criticalTime) {
        try {
            log.info("Добавление новой цели");
            outD.writeUTF("addPurpose");
            outD.flush();

            if (inD.readUTF().equals("ok")) {
                ObservableList<MySubObject> mySubObjects = FXCollections.observableArrayList();

                for (TableViewData tableViewData : purposeStages) {
                    mySubObjects.add(new MySubObject(tableViewData.getStage(), tableViewData.getStatus()));
                }

                MyObject myModel = new MyObject(
                        mySubObjects, name, criterionCompleted, description,
                        status, deadline, dateOpen, criticalTime
                );

                outD.writeUTF(Converter.toJson(myModel));
                outD.flush();
            }
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void addPurposeStage(final int indexPurpose, final String nameStage, final String status) {
        try {
            log.info("Добавление подзадачи");
            outD.writeUTF("addPurposeStage");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(indexPurpose));
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(nameStage);
                outD.flush();
            } else return;
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(status);
                outD.flush();
            } else return;
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
        }
    }
    //endregion methods

    //region REMOVE methods
    public void removePurpose(final int index) {
        try {
            log.info("Удаление задачи");
            outD.writeUTF("removePurpose");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(index));
                outD.flush();
            }
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void removePurposeStage(final int indexPurpose, final int indexPurposeStage) {
        try {
            log.info("Удаление подзадачи");
            outD.writeUTF("removePurposeStage");
            outD.flush();
            if (inD.readUTF().equals("ok")) {
                outD.writeUTF(String.valueOf(indexPurpose));
                outD.flush();
                if (inD.readUTF().equals("ok")) {
                    outD.writeUTF(String.valueOf(indexPurposeStage));
                    outD.flush();
                }
            }
            notifyAllObservers();
        } catch (IOException e) {
            log.error(e);
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
        }
    }
    //endregion

    //region LOAD methods
    public boolean loadAsB() {
        try {
            log.info("Загрузка из файла на клиенте");
            val fileChooser = new FileChooser();
            val file = fileChooser.showOpenDialog(null);
            if (file != null) {
                Scanner scanner = new Scanner(new FileReader(file));
                outD.writeUTF("setPurposes");
                outD.flush();
                if (inD.readUTF().equals("ok")) {
                    outD.writeUTF(scanner.nextLine());
                    outD.flush();
                }
            }
            notifyAllObservers();
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        } catch (NoSuchElementException e){
            log.error(e);
            return false;
        }
    }
    //endregion

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
