package com.team22.project_team_22_2018.controllers;

import com.team22.project_team_22_2018.model.MyObject;
import com.team22.project_team_22_2018.model.MySubObject;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * created by Greffort 18.05.2019
 */
@Log4j
@Controller
public class ControllerWeb {
    private ControllerView controllerView = ControllerView.getControllerView();

    //region "/"
    @GetMapping("/")
    public String main(Map<String, Object> model) {
        if (controllerView.getMyModel() == null) {
            model.put("user", "guest");
        } else {
            model.put("user", controllerView.getMyModel().getName());
        }
        return "main";
    }
    //endregion

    //region userpage Page
    @GetMapping("/userPage")
    public String userPage(Model model) {
        if (controllerView.getMyModel() == null) {
            return "redirect:/login";
        } else {

            controllerView.updateModel();
            Iterable<MyObject> myObjects = controllerView.getMyObjects();
            if (((List<MyObject>) myObjects).size() == 0) {
                return "userPage";
            } else {
                String checkGoal = "d";
                model.addAttribute("checkGoal", checkGoal);
                model.addAttribute("goals", myObjects);
                return "userPage";
            }
        }
    }


    @PostMapping(path = "/getAddGoalPage")
    public String getAddGoalPage(Map<String, Object> model) {
        return "redirect:/addGoal";
    }
    //endregion

    //region addGoal Page
    @GetMapping("/addGoal")
    public String addGoal(Map<String, Object> model) {
        if (controllerView.getMyModel() == null) {
            return "redirect:/login";
        } else {
            Iterable<MySubObject> mySubObjects = controllerView.getGoalStagesLocal();
            model.put("goalsStages", mySubObjects);
//            model.keySet().stream().map(Map)
            return "/addGoal";
        }
    }

    @PostMapping("/addGoal")
    public String addGoal(
            @RequestParam String goalname,
            @RequestParam String criterComp,
            @RequestParam String description,
            @RequestParam String deadline,
            @RequestParam String status,
            @RequestParam String criticalTime,
            Map<String, Object> model) {
        controllerView.addGoal(goalname, criterComp, description, status, deadline, LocalDate.now().toString(), criticalTime);
        return "redirect:/userPage";
    }

    @PostMapping(path = "/delete_goal")
    public String removeGoal(@RequestParam String uuid, Map<String, Object> model) {
        controllerView.removePurpose(uuid);
        return "redirect:/userPage";
    }

    @PostMapping(path = "/cancel")
    public String cancel(Map<String, Object> model) {
        controllerView.setGoalStagesLocal(new ArrayList<>());
        return "redirect:/userPage";
    }
    //endregion

    @ResponseBody
    @GetMapping("/addGoalStage")
    public String addGoalStage(@RequestParam("name") final String name,
                               @RequestParam("completed") final String completed) {
        return controllerView.addGoalStageLocal(name, completed);
    }

    @GetMapping("/delete_goalStage")
    public String removeGoalStage(@RequestParam("uuid") final String uuid) {
        controllerView.removeGoalStagesLocal(uuid);
        return "/addGoal";
    }
    //endregion

    //region login && registration Page
    @PostMapping("/login")
    public String checkLogin(@RequestParam String username, @RequestParam String password, Map<String, Object> model) {

        if (controllerView.login(username, password)) {
            controllerView.updateModel();
            return "redirect:/userPage";
        } else {
            model.put("message", "Пользователя не существует");
            return "/login";
        }
    }

    @GetMapping("/login")
    public String login() {
        if (controllerView.getMyModel() == null) {
            return "/login";
        } else {
            return "redirect:/userPage";
        }
    }

    @GetMapping("/registration")
    public String registration() {
        return "/registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String password, Map<String, Object> model) {
        if (controllerView.login(username, password)) {
            model.put("message", "User existal");
            return "/registration";
        }
        controllerView.registration(username, password);
        return "redirect:/login";
    }

    @PostMapping(path = "/logout")
    public String logout(Map<String, Object> model) {
        controllerView.setMyModel(null);
        return "redirect:/login";
    }
    //endregion

    private String uuidlocal;

    @GetMapping("/setGoal")
    public String setGoal(@RequestParam("uuid") String uuid, Map<String, Object> model) {
        if (controllerView.getMyModel() == null) {
            return "redirect:/login";
        } else {
            uuidlocal = uuid;
            final MyObject goal = controllerView.getMyModel().getPurpose(uuid);
            final String dateClose = goal.getDateClose();
            final String s = LocalDate.of(1970, 1, 1).toString();

            if (!dateClose.equals(s)) {
                model.put("dateClose", goal.getDateClose());
            }
            model.put("goalname", goal.getName());
            model.put("criterComp", goal.getCriterionCompleted());
            model.put("description", goal.getDescription());
            model.put("deadline", goal.getDeadline());
            model.put("status", goal.getStatus());
            model.put("criticalTime", DAYS.between(LocalDate.parse(goal.getDeadline()), LocalDate.parse(goal.getCriticalTime())));

            controllerView.setGoalStagesLocal(controllerView.getMySubObject(uuid));

            model.put("goalsStages", controllerView.getGoalStagesLocal());

            return "/setGoal";
        }
    }

    @PostMapping("/saveEdit")
    public String postSetGoalPage(@RequestParam String goalname,
                                  @RequestParam String criterComp,
                                  @RequestParam String description,
                                  @RequestParam String deadline,
                                  @RequestParam String status,
                                  @RequestParam String criticalTime,
                                  Map<String, Object> model) {

        controllerView.setGoal(uuidlocal,
                controllerView.getGoalStagesLocal(),
                goalname,
                criterComp,
                description,
                status,
                deadline,
                controllerView.getDateOpen(uuidlocal),
                criticalTime,
                false
        );
        controllerView.setGoalStagesLocal(new ArrayList<>());

        return "redirect:/userPage";
    }

    @PostMapping("/closeGoal")
    public String closeGoal(Map<String, Object> model) {
        controllerView.setPurposeDateClose(uuidlocal, LocalDate.now().toString());
//        setGoal(uuidlocal,model);
        return setGoal(uuidlocal, model);
    }

    @PostMapping("/openGoal")
    public String openGoal(Map<String, Object> model) {
//        String uuid = uuidlocal;
        controllerView.setPurposeDateCloseNull(uuidlocal);
//        setGoal(uuidlocal,model);
        return setGoal(uuidlocal, model);
    }
}
