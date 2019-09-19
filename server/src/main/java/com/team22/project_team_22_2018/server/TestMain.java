package com.team22.project_team_22_2018.server;

import com.team22.project_team_22_2018.server.controller.ControllerDB;
import com.team22.project_team_22_2018.server.entity.GoalStages;
import com.team22.project_team_22_2018.server.entity.Goals;
import com.team22.project_team_22_2018.server.entity.Pass;
import com.team22.project_team_22_2018.server.entity.Users;
import com.team22.project_team_22_2018.server.repository.GoalStagesRepository;
import com.team22.project_team_22_2018.server.repository.GoalsRepository;
import com.team22.project_team_22_2018.server.repository.PassRepository;
import com.team22.project_team_22_2018.server.repository.UsersRepository;
import org.hibernate.Session;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

//@EnableJpaRepositories(
//        basePackages = "com.team22.project_team_22_2018.server.repository", repositoryImplementationPostfix = "usersRepository")
public class TestMain {
    private static final LocalDate FILL_DATE_DEFAULT = LocalDate.of(1970, 1, 1);

    public static void main(String[] args) throws IllegalArgumentException {
        TestMain testMain = new TestMain();
        testMain.hibernate();
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:spring-config.xml"); //move from src.main.java to src.main.resources
        ctx.refresh();

        UsersRepository usersRepository = ctx.getBean(UsersRepository.class);
        GoalsRepository goalsRepository = ctx.getBean(GoalsRepository.class);
        GoalStagesRepository goalStagesRepository = ctx.getBean(GoalStagesRepository.class);
        PassRepository passRepository = ctx.getBean(PassRepository.class);
//
//
////        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app.xml");
//
//
        Users user = new Users("USER2", "1");
        Pass pass = new Pass();
        Goals goal = new Goals(
                "1", new ArrayList<>(),
                "GOAL21", "GOAL21",
                "GOAL21", "GOAL21",
                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
                0);
//        Goals goal2 = new Goals(
//                UUID.randomUUID(), new ArrayList<>(),
//                "GOAL22", "GOAL2",
//                "GOAL2", "GOAL2",
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                false);
//        Goals goal3 = new Goals(
//                UUID.randomUUID(), new ArrayList<>(),
//                "GOAL3", "GOAL3",
//                "GOAL3", "GOAL3",
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
//                false);
        GoalStages goalStage = new GoalStages("1", "GOALSTAGES21", "GOALSTAGES21");

        ArrayList<GoalStages> goalStages = new ArrayList<>();
        goalStages.add(goalStage);

        ArrayList<Goals> goals = new ArrayList<>();
        goals.add(goal);
//        goals.add(goal2);


        goal.setUser(user);
        goal.setGoalStages(goalStages);

        goalStage.setGoal(goal);

        goals.add(goal);
        user.setGoals(goals);
        pass.setpPass("PASS2");
        pass.setNumber(UUID.randomUUID().toString());
        pass.setUsers(user);


        goalStagesRepository.save(goalStage);
        goalsRepository.save(goal);
        usersRepository.save(user);


//        passRepository.save(pass);

//        usersRepository.save();


//        System.out.println("*******************************");
//        List<Users> usersList = usersRepository.findAll();
//        for (Users users : usersList) {
//            System.out.println(users.toString());
//        }
//
//        System.out.println("*******************************");
//        Users users = usersRepository.findByName("USER2");

        ControllerDB controllerDB = new ControllerDB();


        Users users1 = new Users();
        users1.setName("dfd");
        users1.setUuid("1");
        users1.setGoals(new ArrayList<>());


        controllerDB.checkLogin("USER21", "PASS22");
        controllerDB.registration("REGUSER2", "REGPASS2");

        controllerDB.removeGoal("1");

        controllerDB.removeGoalStage("d2f2fdc7-ab12-42fa-aa2e-8b5d1fdb9a30");


        Users users2 = usersRepository.findById("01082bfb-0353-4d8f-9e7b-3285cec270c1").orElseThrow(() -> new NullPointerException("1082bfb-0353-4d8f-9e7b-3285cec270c1"));
//        System.out.println("**************************************************************************  "+controllerDB.checkLogin("USER2","PASS2")+"  **************************************************************************");
        System.out.println(usersRepository.findById("01082bfb-0353-4d8f-9e7b-3285cec270c1"));

        //region OLD Hibernate
//

//endregion
    }

    private void hibernate() {
        //        создать объекты.

        Users user = new Users("USER1", "01082bfb-0353-4d8f-9e7b-3285cec270c1");
        Pass pass = new Pass();
        Goals goal = new Goals(
                UUID.randomUUID().toString(), new ArrayList<>(),
                "GOAL1", "GOAL1",
                "GOAL1", "GOAL1",
                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
                0);
        Goals goal2 = new Goals(
                UUID.randomUUID().toString(), new ArrayList<>(),
                "GOAL2", "GOAL2",
                "GOAL2", "GOAL2",
                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
                0);
        Goals goal3 = new Goals(
                UUID.randomUUID().toString(), new ArrayList<>(),
                "GOAL3", "GOAL3",
                "GOAL3", "GOAL3",
                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
                FILL_DATE_DEFAULT, FILL_DATE_DEFAULT,
                0);
        GoalStages goalStage = new GoalStages(UUID.randomUUID().toString(), "GOALSTAGES1", "GOALSTAGES1");

        ArrayList<GoalStages> goalStages = new ArrayList<>();
        goalStages.add(goalStage);

        ArrayList<Goals> goals = new ArrayList<>();
        goals.add(goal);
        goals.add(goal2);

        user.setGoals(goals);
        goal.setGoalStages(goalStages);

        goal.setUser(user);
        goal2.setUser(user);
        goal3.setUser(user);
        goals.add(goal);
        goals.add(goal2);
        goals.add(goal3);

        user.setGoals(goals);
        goalStage.setGoal(goal);
        goal.setGoalStages(goalStages);

        pass.setpPass("PASS2");
        pass.setNumber(UUID.randomUUID().toString());
        pass.setUsers(user);

    }

//    private boolean login(String login, String password){
//        Optional<Users> users = usersRepository.findByName(login);
//        if (users.isPresent()) {
//            String uuid = users.get().getUuid();
//            if(passRepository.findByUsers(users.get()).get().getpPass() == password){
//                return true;
//            }
//            else return false;
//        }else return false;
//    }
}

