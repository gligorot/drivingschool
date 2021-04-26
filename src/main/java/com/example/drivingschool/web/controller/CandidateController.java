package com.example.drivingschool.web.controller;

import com.example.drivingschool.model.*;
import com.example.drivingschool.model.dto.TrainingDTO;
import com.example.drivingschool.model.dto.TransitionDTO;
import com.example.drivingschool.model.enumerations.TrainingStateName;
import com.example.drivingschool.service.RequestService;
import com.example.drivingschool.service.RoleService;
import com.example.drivingschool.service.TrainingService;
import com.example.drivingschool.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/candidate")
public class CandidateController {

    private final UserService userService;
    private final RoleService roleService;
    private final TrainingService trainingService;
    private final RequestService requestService;

    public CandidateController(UserService userService, RoleService roleService, TrainingService trainingService, RequestService requestService) {
        this.userService = userService;
        this.roleService = roleService;
        this.trainingService = trainingService;
        this.requestService = requestService;
    }

    @GetMapping
    public String getStudentDefaultPage() {
        return "redirect:/candidate/training";
    }

    @GetMapping("/training")
    public String getTrainingPage(
            HttpServletRequest req,
            Model model
    ) {
        String username = req.getRemoteUser();
        User user = (User) userService.loadUserByUsername(username);
        Candidate candidate = roleService.getCandidateForUser(user);

        System.out.println(candidate);

        List<TrainingDTO> trainings = trainingService.getAllForCandidateWithDetails(candidate);


        if (trainings.isEmpty()) {
            model.addAttribute("bodyContent", "no-training-available");
            return "master-template";
        }

        TrainingDTO activeTraining = determineActiveTrainingFrom(trainings);
        model.addAttribute("training", activeTraining);

        List<TrainingStateTransition> transitions = activeTraining.getTrainingDetailsDTO().getStateTransitions();
        List<TransitionDTO> transitionDTOs = generateTransitionDTOs(transitions);
        model.addAttribute("transitions", transitionDTOs);

        model.addAttribute("minDateTime", LocalDateTime.now().plusHours(2));
        model.addAttribute("maxDateTime", LocalDateTime.now().plusHours(2).plusWeeks(2));
        model.addAttribute("bodyContent", "training");
        return "master-template";
    }

    private TrainingDTO determineActiveTrainingFrom(List<TrainingDTO> trainings) {
        TrainingDTO activeTraining;
        if (trainings.size() == 1) {
            activeTraining = trainings.get(0);
        } else {
            activeTraining = trainings.stream()
                    .filter(
                            trainingDTO -> trainingDTO.getTrainingDetailsDTO()
                                    .getCurrentState() != TrainingStateName.DONE
                    )
                    .findFirst().orElseThrow();
        }
        return activeTraining;
    }

    private List<TransitionDTO> generateTransitionDTOs(List<TrainingStateTransition> transitions) {
        List<TransitionDTO> transitionDTOs = new ArrayList<>();

        int lastTransitionIndex = transitions.size() - 1;
        // fill for all but current transition
        for (int i = 0; i < lastTransitionIndex; i++) {
            transitionDTOs.add(new TransitionDTO(transitions.get(i)));
        }
        // for last real transition
        TransitionDTO lastTransitionDTO = new TransitionDTO(transitions.get(lastTransitionIndex));
        lastTransitionDTO.setState("active");
        transitionDTOs.add(lastTransitionDTO);

        // FILLER TRANSITIONS
        List<TrainingStateName> transitionStateNames = generateTransitionNames();
        int MAX_TRANSITION_COUNT = 5;
        for (int i = 0; i < MAX_TRANSITION_COUNT - transitions.size(); i++) {
            transitionDTOs.add(new TransitionDTO(transitionStateNames.get(transitions.size() + i)));
        }

        return transitionDTOs;
    }

    private List<TrainingStateName> generateTransitionNames() {
        List<TrainingStateName> transitionStateNames = new ArrayList<>();

        transitionStateNames.add(TrainingStateName.THEORY);
        transitionStateNames.add(TrainingStateName.PRACTICE);
        transitionStateNames.add(TrainingStateName.THEORY_TEST);
        transitionStateNames.add(TrainingStateName.RANGE_TEST);
        transitionStateNames.add(TrainingStateName.CITY_TEST);

        return transitionStateNames;
    }

    @PostMapping("/request")
    public String makeNewRequest(
            HttpServletRequest req,
            @RequestParam Long trainingId,
            @RequestParam(name = "trainingStateName") TrainingStateName currentTrainingStateName,
            @RequestParam(name = "trainingStateCurrentCount") Integer trainingStateCurrentCount,
            @RequestParam(name = "request-datetime") String requestLocalDateTimeString
    ) {
        LocalDateTime requestLocalDateTime = LocalDateTime.parse(requestLocalDateTimeString);
        Training training = trainingService.getById(trainingId);
        Instructor instructor = training.getInstructor();

        requestService.create(training, instructor, currentTrainingStateName, trainingStateCurrentCount, requestLocalDateTime);

        return "redirect:/candidate";
    }

}
