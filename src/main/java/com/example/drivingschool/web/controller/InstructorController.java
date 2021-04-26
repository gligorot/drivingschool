package com.example.drivingschool.web.controller;

import com.example.drivingschool.model.*;
import com.example.drivingschool.model.dto.RequestDTO;
import com.example.drivingschool.model.dto.TrainingDTO;
import com.example.drivingschool.model.enumerations.TrainingCategoryName;
import com.example.drivingschool.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value = "/instructor")
public class InstructorController {

    private final UserService userService;
    private final RoleService roleService;
    private final RequestService requestService;
    private final TrainingService trainingService;
    private final TrainingCategoryService categoryService;
    private final CandidateService candidateService;
    private final TrainingCategoryService trainingCategoryService;
    private final TrainingStateTransitionService trainingStateTransitionService;

    public InstructorController(UserService userService, RoleService roleService, RequestService requestService, TrainingService trainingService, TrainingCategoryService categoryService, CandidateService candidateService, TrainingCategoryService trainingCategoryService, TrainingStateTransitionService trainingStateTransitionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.requestService = requestService;
        this.trainingService = trainingService;
        this.categoryService = categoryService;
        this.candidateService = candidateService;
        this.trainingCategoryService = trainingCategoryService;
        this.trainingStateTransitionService = trainingStateTransitionService;
    }

    @GetMapping
    public String getInstructorDefaultPage() {
        return "redirect:/instructor/trainings";
    }

    @GetMapping("/trainings")
    public String getTrainingsPage(
            HttpServletRequest req,
//            Authentication authentication,
            Model model
    ) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = (User) userDetails; // results in class cast exception??? (previuosly worked)
        String username = req.getRemoteUser();
        User user = (User) userService.loadUserByUsername(username);
        Instructor instructor = roleService.getInstructorForUser(user);
        List<TrainingDTO> trainingDTOs = trainingService.getAllForInstructorWithDetails(instructor);

        model.addAttribute("trainings", trainingDTOs);
        model.addAttribute("bodyContent", "trainings");

        return "master-template";
    }

    @GetMapping("/trainings/new")
    public String getNewTrainingPage(
            Model model
    ) {
        model.addAttribute("categories", trainingCategoryService.getAll());
        model.addAttribute("candidates", candidateService.getAllWithoutActiveTraining());
        model.addAttribute("bodyContent", "new_training");

        return "master-template";
    }

    @PostMapping("/trainings/new")
    public String addNewTraining(
            HttpServletRequest req,
            @RequestParam Long candidateId,
            @RequestParam String categoryName
    ) {
        String username = req.getRemoteUser();
        User user = (User) userService.loadUserByUsername(username);
        Instructor instructor = roleService.getInstructorForUser(user);

        TrainingCategoryName.valueOf(categoryName);
        TrainingCategoryName trainingCategoryName = TrainingCategoryName.valueOf(categoryName);
        TrainingCategory category = categoryService.getByName(trainingCategoryName);

        Candidate candidate = candidateService.getById(candidateId);

        // TODO more checks can be added such as - does the candidate have an active training etc.
        trainingService.createNewTraining(instructor, category, candidate);

        return "redirect:/instructor";
    }

    @PostMapping("/trainings/promote")
    public String promoteTraining(
            @RequestParam Long trainingId
    ) {
        // TODO more checks can be added, such as - is this training with this instructor etc.
        Training training = trainingService.getById(trainingId);
        try {
            trainingStateTransitionService.promoteTrainingToNextState(training);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/instructor";
    }

    @GetMapping("/requests")
    public String getRequestsPage(
            HttpServletRequest req,
            Model model
    ) {
        String username = req.getRemoteUser();
        User user = (User) userService.loadUserByUsername(username);
        Instructor instructor = roleService.getInstructorForUser(user);

        List<RequestDTO> pendingRequests =
                requestService.getAllPendingRequestsForInstructor(instructor);
        pendingRequests.sort(Comparator.comparing(RequestDTO::getTimestamp));
        List<RequestDTO> activeAcceptedRequests =
                requestService.getAllActiveAcceptedRequestsForInstructor(instructor);
        activeAcceptedRequests.sort(Comparator.comparing(RequestDTO::getTimestamp));

        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("activeAcceptedRequests", activeAcceptedRequests);
        model.addAttribute("bodyContent", "requests");

        return "master-template";
    }

    @PostMapping("/requests/approve")
    public String approveRequest(
            @RequestParam Long requestId
    ) {
        Request request = requestService.getById(requestId);
        requestService.approve(request);

        return "redirect:/instructor/requests";
    }

    @PostMapping("/requests/refuse")
    public String refuseRequest(
            @RequestParam Long requestId
    ) {
        Request request = requestService.getById(requestId);
        requestService.refuse(request);

        return "redirect:/instructor/requests";
    }
}
