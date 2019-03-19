package com.team22.project_team_22_2018.server.controller;

import com.team22.project_team_22_2018.server.models.Account;
import com.team22.project_team_22_2018.server.models.Purpose;
import com.team22.project_team_22_2018.server.models.PurposeStage;
import com.team22.project_team_22_2018.util.Converter;
import lombok.extern.log4j.Log4j;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Log4j
public class ControllerModel {

    private Account account;

    public ControllerModel() {
//        this.account = ServerRuntimeHolder.getModelHolder();
        this.account = new Account();
    }

    //region GET methods
    public Account getModel() {
        return this.account;
    }

    public ArrayList<String> getPurposes() {
        List<Purpose> list = this.account.getPurposes();
        ArrayList<String> arrayList = new ArrayList<>();
        for (Purpose purpose : list) {
            arrayList.add(purpose.getName());
        }
        return arrayList;
    }
    //endregion

    //region SET methods

    /**
     * Перезаписывает значение конкретного объекта Purpose
     */
    public void setPurpose(final String uuid, final Purpose purpose) {
        this.account.setPurpose(UUID.fromString(uuid), purpose);
    }

    /**
     * Перезаписывает весь список Purpose(Целей)
     */
    public void setPurposes(final List<Purpose> list) {
        this.account.setPurposes(list);
    }

//    /**
//     * Перезаписывает значение даты закрытия цели
//     */
//    public void setPurposeDateClose(final int index, final String dateClose) {
//        account.getPurpose(index).setDateClose(LocalDate.parse(dateClose));
//    }
//
//    /**
//     * Перезаписывает значение даты закрытия цели
//     */
//    public void setPurposeDateClose(final int index) {
//        account.getPurpose(index).setDateClose(LocalDate.of(1970, 1, 1));
//    }
//
//    /**
//     * Перезаписывает значение имени этапа цели
//     */
//    public void setStageName(final int indexPurpose, final int indexPurposeStage, final String name) {
//        this.account.getPurpose(indexPurpose).getPurposeStage(indexPurposeStage).setName(name);
//    }

    /**
     * Перезаписывает значение даты закрытия цели
     */
    public void setPurposeDateClose(final String uuid, final String dateClose) {

        account.getPurpose(UUID.fromString(uuid)).setDateClose(LocalDate.parse(dateClose));
    }

    /**
     * Перезаписывает значение даты закрытия цели
     */
    public void setPurposeDateClose(final String uuid) {
        account.getPurpose(UUID.fromString(uuid)).setDateClose(LocalDate.of(1970, 1, 1));
    }

    /**
     * Перезаписывает значение имени этапа цели
     */
    public void setStageName(final String uuidPurpose, final String uuidPurposeStage, final String name) {
        this.account.getPurpose(UUID.fromString(uuidPurpose)).getPurposeStage(UUID.fromString(uuidPurposeStage)).setName(name);
    }

    public void setStatus(final String uuidPurpose, final String status) {
        this.account.setStatus(UUID.fromString(uuidPurpose), status);
    }
    //endregion

    //region ADD methods

    /**
     * Добавляет объект Purpose (Цель) в список Account
     */
    public void addPurpose(
            final ArrayList arrayList, final String name,
            final String criterionCompleted, final String description,
            final String status, final String deadline,
            final String dateOpen, final String criticalTime, String uuid) {
        this.account.addPurpose(new Purpose(
                arrayList,
                name,
                criterionCompleted,
                description,
                status,
                LocalDate.parse(deadline),
                LocalDate.parse(dateOpen),
//             criticalTime
                LocalDate.parse(deadline).minusDays(Long.parseLong(criticalTime)),
                UUID.fromString(uuid)
        ));
    }

    public void addPurpose(String line) {
        try {
            log.info("Добавление задачи");
            log.info(line);
            this.account.addPurpose(Converter.toJavaObject(line, Purpose.class));
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Добавляет объект PurposeStage (Этап выполнения цели) в список Account
     */
//    public void addPurposeStage(final int indexPurpose, final String nameStage, final String status, String uuid) {
//        this.account.getPurpose(indexPurpose).addPurposeStage(new PurposeStage(nameStage, status,UUID.fromString(uuid)));
//    }
    public void addPurposeStage(String uuidPurpose, final String nameStage, final String status, String uuid) {
        UUID uuid1 = UUID.fromString(uuid);
        UUID uuid2 = (UUID.fromString(uuidPurpose));

        this.account.getPurpose(uuid2).addPurposeStage(new PurposeStage(nameStage, status, uuid1));
    }
    //endregion

    //region REMOVE methods

    /**
     * Удаляет объект Purpose (Цель) из списка Account
     */
    public void removePurpose(final String uuid) {
//        if (index >= 0 && index < account.getPurposes().size()) {
//            this.account.removePurpose(index);
//        }
        try {
            this.account.removePurpose(UUID.fromString(uuid));
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Удаляет объект PurposeStage (Этап выполнения цели) из списка Account
     */
    public void removePurposeStage(final String uuidPurpose, final String uuidPurposeStage) {
        this.account.getPurpose(UUID.fromString(uuidPurpose)).removePurposeStage(UUID.fromString(uuidPurposeStage));
    }

    public boolean checkLogin(String login, String password) {
        //получили логин  пароль, берем файл, считываем из него данные и сверяем.
        //Если совпадение найдено, то берем строку ниже, и смотрим совпадает ли пароль, если да то возвращаем true,
        try {
            @NotNull Scanner scanner = new Scanner(new FileReader("login&password"));
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals(login)) {
                    if (scanner.nextLine().equals(password)) {
                        try {
                            Scanner scanner1 = new Scanner(new FileReader(login));
                            String s = scanner1.nextLine();
//                            System.out.println(s);
//                            System.out.println(account.toString());
                            this.account = Converter.toJavaObject(s, Account.class);
//                            System.out.println(account.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return true;
                    }
                }
            }
            return false;
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registration(String login, String password) {
        if (checkLogin(login, password)) {
            return false;
        } else {
            try {

//            @NotNull PrintStream printStream = new PrintStream(new FileOutputStream("login&password"));
                @NotNull FileWriter fileWriter = new FileWriter("login&password", true);
                BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
                bufferWriter.write(login + "\n");
                bufferWriter.write(password + "\n");
                bufferWriter.flush();
                return true;
            } catch (FileNotFoundException e) {
                log.error(e);
                return false;
            } catch (IOException e) {
                log.error(e);
                return false;
            }
        }
    }

    public void save(String login) {
        try {
            Converter.toJsonAs(new File(login), account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //endregion
}
