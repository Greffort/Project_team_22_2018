package com.team22.project_team_22_2018.server.controller;

import com.team22.project_team_22_2018.server.entity.GoalStages;
import com.team22.project_team_22_2018.server.entity.Goals;
import com.team22.project_team_22_2018.server.entity.Pass;
import com.team22.project_team_22_2018.server.entity.Users;
import com.team22.project_team_22_2018.server.repository.GoalStagesRepository;
import com.team22.project_team_22_2018.server.repository.GoalsRepository;
import com.team22.project_team_22_2018.server.repository.PassRepository;
import com.team22.project_team_22_2018.server.repository.UsersRepository;
import com.team22.project_team_22_2018.util.Converter;
import lombok.extern.log4j.Log4j;
import lombok.val;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * created by Greffort 10.05.2019
 */
@Log4j
public class ControllerDB {

    private String userName;

    private GenericXmlApplicationContext context;
    private UsersRepository usersRepository;
    private GoalsRepository goalsRepository;
    private GoalStagesRepository goalStagesRepository;
    private PassRepository passRepository;

    public ControllerDB() {
        log.info("Создается контекст");
        context = new GenericXmlApplicationContext();
        context.load("classpath:spring-config.xml");
        context.refresh();

        usersRepository = context.getBean(UsersRepository.class);
        goalsRepository = context.getBean(GoalsRepository.class);
        goalStagesRepository = context.getBean(GoalStagesRepository.class);
        passRepository = context.getBean(PassRepository.class);
        log.info("Контекст создан");
    }

    //region GET methods
    public Users getModel() {
        return usersRepository.findByName(this.userName);
    }
    //endregion

    //region SET methods

    /**
     * Перезаписывает значение конкретного объекта Purpose
     * <p>
     * Получили объект и его ID, делаем сет в БД.
     */
    public void setGoal(final String goalID, final Goals newGoal) {
        final Optional<Goals> optionalGoal = goalsRepository.findById(goalID);
        if (optionalGoal.isPresent()) {
            Goals goal = optionalGoal.get();
            final Users user = goal.getUser();

            final List<GoalStages> goalStages = newGoal.getGoalStages();

            newGoal.setUser(user);
            newGoal.setGoalStages(null);
            goalsRepository.save(newGoal);
            goalsRepository.flush();

            for (GoalStages goalStage : goalStages) {

                addGoalStage(goalID, goalStage.getName(), goalStage.getCompleted(), goalStage.getUuid());
            }
            newGoal.setGoalStages(goalStages);
            if (!goal.getDateClose().equals("1970-01-01")) {
                newGoal.setDateClose(goal.getDateClose());
            }
            goalsRepository.save(newGoal);
            goalsRepository.flush();

            for (int i = 0; i < user.getGoals().size(); i++) {
                if (user.getGoals().get(i).getUuid().equals(newGoal.getUuid())) {
                    user.getGoals().set(i, newGoal);
                }
            }

            usersRepository.save(user);
            usersRepository.flush();
        }
    }

    /**
     * Перезаписывает значение даты закрытия цели
     * <p>
     * Получаем значения и ID цели, меняем в целе поле, перезаписываем.
     */
    public void setGoalDateClose(final String goalID, final String dateClose) {
        final Optional<Goals> optionalGoal = goalsRepository.findById(goalID);
        if (optionalGoal.isPresent()) {
            Goals goal = optionalGoal.get();
            goal.setDateClose(LocalDate.parse(dateClose));
            goalsRepository.save(goal);
            goalsRepository.flush();
        }
    }

    /**
     * Перезаписывает значение даты закрытия цели
     * <p>
     * Получаем значения и ID цели, меняем в целе поле, перезаписываем.
     */
    public void setGoalDateClose(final String uuid) {
        setGoalDateClose(uuid, LocalDate.of(1970, 1, 1).toString());
    }

    /**
     * Перезаписывает значение имени этапа цели
     * <p>
     * Получаем значения и ID цели, меняем в целе поле, перезаписываем.
     */
    public void setStageName(final String goalID, final String goalStageID, final String newNameGoalStage) {
        final Optional<GoalStages> optionalGoalStages = goalStagesRepository.findById(goalStageID);
        if (optionalGoalStages.isPresent()) {
            GoalStages goalStage = optionalGoalStages.get();

            goalStage.setGoal(goalsRepository.findById(goalID).get());
            goalStage.setName(newNameGoalStage);
            goalStagesRepository.save(goalStage);
        }
    }

    public void setStatus(final String goalID, final String status) {
        final Optional<Goals> optionalGoal = goalsRepository.findById(goalID);
        if (optionalGoal.isPresent()) {
            Goals goal = optionalGoal.get();
            goal.setStatus(status);
            goalsRepository.save(goal);
            goalsRepository.flush();
        }
    }

    public void setCheck(boolean bool, String goalID) {
        int a = 0;
        if (bool) {
            a = 1;
        }
        final Optional<Goals> optionalGoal = goalsRepository.findById(goalID);
        if (optionalGoal.isPresent()) {
            Goals goal = optionalGoal.get();
            goal.setCheck(a);
            goalsRepository.save(goal);
            goalsRepository.flush();
        }
    }
    //endregion

    //region ADD methods

    /**
     * Добавляет объект Purpose (Цель) в список Users
     */
    public void addGoal(String line, String userID) {
        try {
            Goals goal = Converter.toJavaObject(line, Goals.class);
            final Optional<Users> optionalUsers = usersRepository.findById(userID);
            if (optionalUsers.isPresent()) {
                Users user = optionalUsers.get();

                goal.setUser(user);

                if (goal.getGoalStages().size() > 0) {
                    List<GoalStages> goalStages = goal.getGoalStages();
                    goal.setGoalStages(null);

                    goalsRepository.save(goal);
                    goalsRepository.flush();
                    for (GoalStages goalStage : goalStages) {
                        goalStage.setGoal(goal);
                        goalStagesRepository.save(goalStage);
                        goalStagesRepository.flush();
                    }
                    goal.setGoalStages(goalStages);
                }

                goalsRepository.save(goal);
                goalsRepository.flush();

                List<Goals> goals = user.getGoals();
                goals.add(goal);
                usersRepository.save(user);
                usersRepository.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e);
        }
    }

    /**
     * Добавляет объект PurposeStage (Этап выполнения цели) в список Users
     */
    public void addGoalStage(String goalID, final String nameStage, final String status, String uuid) {
        final Optional<Goals> optionalGoal = goalsRepository.findById(goalID);

        if (optionalGoal.isPresent()) {
            Goals goal = optionalGoal.get();

            GoalStages goalStage = new GoalStages();
            goalStage.setName(nameStage);
            goalStage.setGoal(goal);
            goalStage.setCompleted(status);
            goalStage.setUuid(uuid);

            goalStagesRepository.save(goalStage);
            goalStagesRepository.flush();

            goalsRepository.save(goal);
            goalsRepository.flush();
        }
    }
    //endregion

    //region REMOVE methods

    /**
     * Удаляет объект Purpose (Цель) из списка Users
     */
    public void removeGoal(final String uuid) {
        Optional<Goals> optionalGoal = goalsRepository.findById(uuid);
        if (optionalGoal.isPresent()) {
            Goals goal = optionalGoal.get();

            Users users = goal.getUser();
            List<Goals> goals = users.getGoals();
            for (int i = 0; i < goals.size(); i++) {
                if (goals.get(i).getUuid().equals(uuid)) {
                    goals.remove(i);
                }
            }

            usersRepository.save(users);
            usersRepository.flush();

            for (int i = 0; i < goal.getGoalStages().size(); i++) {
                removeGoalStage(goal.getGoalStages().get(i).getUuid());
            }

            goal.setGoalStages(null);
            goal.setUser(null);
            goalsRepository.save(goal);
            goalsRepository.flush();
            goalsRepository.deleteById(uuid);
            goalsRepository.flush();
        }
    }

    /**
     * Удаляет объект PurposeStage (Этап выполнения цели) из списка Users
     */
    public void removeGoalStage(final String uuidGoalStage) {
        Optional<GoalStages> optionalGoalStage = goalStagesRepository.findById(uuidGoalStage);
        if (optionalGoalStage.isPresent()) {
            GoalStages goalStage = optionalGoalStage.get();
            Goals goal = goalStage.getGoal();
            final List<GoalStages> goalStages = goal.getGoalStages();
            for (int i = 0; i < goalStages.size(); i++) {
                if (goalStages.get(i).getUuid().equals(uuidGoalStage)) {
                    goalStages.remove(i);
                }
            }
            goal.setGoalStages(goalStages);
            goalsRepository.save(goal);
            goalsRepository.flush();
            goalStage.setGoal(null);
            goalStagesRepository.save(goalStage);
            goalStagesRepository.flush();
            goalStagesRepository.deleteById(uuidGoalStage);
            goalStagesRepository.flush();
        }
    }
    //endregion

    //region LogReg
    public boolean checkLogin(String login, String password) {
        Users users = usersRepository.findByName(login);
        if (users != null) {
            val pass = passRepository.findByUsers(users);
            if (pass.getpPass().equals(password)) {
                this.userName = login;
                return true;
            } else return false;
        } else return false;
    }

    public boolean registration(String login, String password) {
        if (checkLogin(login, password)) {
            return false;
        } else {
            Users users = new Users(login, UUID.randomUUID());
            Pass pass = new Pass();
            pass.setNumber(UUID.randomUUID().toString());
            pass.setUsers(users);
            pass.setpPass(password);
            passRepository.save(pass);
            return true;
        }
    }
    //endregion
}
