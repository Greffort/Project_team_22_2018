package com.team22.project_team_22_2018.server.view.fxcontroller;

import com.team22.project_team_22_2018.server.GUIServer;
import com.team22.project_team_22_2018.server.MonoThreadClientHandler;
import com.team22.project_team_22_2018.server.repository.GoalStagesRepository;
import com.team22.project_team_22_2018.server.repository.GoalsRepository;
import com.team22.project_team_22_2018.server.repository.PassRepository;
import com.team22.project_team_22_2018.server.repository.UsersRepository;
import com.team22.project_team_22_2018.server.util.HibernateUtil;
import com.team22.project_team_22_2018.server.util.SessionUtil;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Log4j
public class ServerFormController {

    private static final LocalDate FILL_DATE_DEFAULT = LocalDate.of(1970, 1, 1);

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
    private Button buttonRebuildDB;

    @FXML
    private ProgressIndicator spinner;

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

        spinner.setVisible(false);

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
        spinner.setVisible(true);
        int number = listViewUsers.getSelectionModel().getSelectedIndex();
        if (number == -1) {
            return;
        } else {
            server.removeClient(number);
        }
        spinner.setVisible(false);
    }


    /**
     * здесь мб спринг пускать тоже придется
     */
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
        spinner.setVisible(true);
        buttonStopServer();
        labelStatus.setText("Статус - Выключен");
        buttonStartServer();
        labelStatus.setText("Статус - Запущен");
        spinner.setVisible(false);
    }

    public void buttonRebuildDB(ActionEvent event) {
        spinner.setVisible(true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Project team 22");
        alert.setHeaderText("Все данные будут безвозвратно удалены, вы уверены что хотите продолжить?");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();

                session.flush();
                session.getTransaction().commit();

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Project team 22");
                alert1.setHeaderText("Rebuild data base completed");
                alert1.showAndWait().ifPresent(rs1 -> {
                    if (rs1 == ButtonType.OK) {
                        log.info("Rebuild data base completed");
                    }
                });
            }
            if (rs == ButtonType.CANCEL) {
                event.consume();
            }
        });
        spinner.setVisible(false);
    }

    public void buttonDropDB(ActionEvent event) {
//        spinner.setVisible(true);
//        SessionUtil sessionUtil = new SessionUtil();
//        sessionUtil.openTransactionSession();
//
//        Session session = sessionUtil.getSession();
////        Session session = HibernateUtil.getSessionFactory().openSession();
////        session.beginTransaction();
//
//        try {
//            Scanner scanner = new Scanner(new FileReader("sql/dropTables.sql"));
//            while (scanner.hasNext()) {
//                Query query = session.createSQLQuery(scanner.nextLine());
//                query.executeUpdate();
//                spinner.setVisible(false);
//            }
//        } catch (FileNotFoundException e) {
//            spinner.setVisible(false);
//            log.error(e);
//        }finally {
//            session.close();
////            sessionUtil.closeSession();
////            sessionUtil.closeTransactionSesstion();
//        }
    }

    public void buttonCreateDB() {
//        SessionUtil sessionUtil = new SessionUtil();
//        sessionUtil.openTransactionSession();
//
//        Session session = sessionUtil.getSession();
//        Query query = session.createSQLQuery( "@D://Users//Alex//Documents//GitHub//Project_team_22_2018_client-server//ql//createTables.sql");
//        query.executeUpdate();

//        Users user = new Users("USER1", "01082bfb-0353-4d8f-9e7b-3285cec270c1");
//        Pass pass = new Pass();
//        Goals goal = new Goals(
//                UUID.randomUUID().toString(), new ArrayList<>(),
//                "GOAL1", "GOAL1",
//                "GOAL1", "GOAL1",
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                0);
//        Goals goal2 = new Goals(
//                UUID.randomUUID().toString(), new ArrayList<>(),
//                "GOAL2", "GOAL2",
//                "GOAL2", "GOAL2",
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                0);
//        Goals goal3 = new Goals(
//                UUID.randomUUID().toString(), new ArrayList<>(),
//                "GOAL3", "GOAL3",
//                "GOAL3", "GOAL3",
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                0);
//        GoalStages goalStage = new GoalStages(UUID.randomUUID().toString(), "GOALSTAGES1", "GOALSTAGES1");
//
//        ArrayList<GoalStages> goalStages = new ArrayList<>();
//        goalStages.add(goalStage);
//
//        ArrayList<Goals> goals = new ArrayList<>();
//        goals.add(goal);
//        goals.add(goal2);
//
//        user.setGoals(goals);
//        goal.setGoalStages(goalStages);
//
//        goal.setUser(user);
//        goal2.setUser(user);
//        goal3.setUser(user);
//        goals.add(goal);
//        goals.add(goal2);
//        goals.add(goal3);
//
//        user.setGoals(goals);
//        goalStage.setGoal(goal);
//        goal.setGoalStages(goalStages);
//
//        pass.setpPass("PASS2");
//        pass.setNumber(UUID.randomUUID().toString());
//        pass.setUsers(user);
//
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//
//        session.saveOrUpdate(goal);
//        session.saveOrUpdate(goal2);
//        session.saveOrUpdate(goal3);
//        session.saveOrUpdate(user);
//        session.saveOrUpdate(goalStage);
//
//        session.saveOrUpdate(pass);
//
//        session.flush();
//        session.getTransaction().commit();
//        session.close();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.getTransaction().commit();
        session.close();


//        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//        ctx.load("classpath:spring-config.xml"); //move from src.main.java to src.main.resources
//        ctx.refresh();
//
//        UsersRepository usersRepository = ctx.getBean(UsersRepository.class);
//        GoalsRepository goalsRepository = ctx.getBean(GoalsRepository.class);
//        GoalStagesRepository goalStagesRepository = ctx.getBean(GoalStagesRepository.class);
//        PassRepository passRepository = ctx.getBean(PassRepository.class);
    }
}

