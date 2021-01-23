package com.garygriffaw.tickettracker.controllers;

import com.garygriffaw.tickettracker.converters.QueueConverter;
import com.garygriffaw.tickettracker.converters.RoleConverter;
import com.garygriffaw.tickettracker.converters.UserAccountConverter;
import com.garygriffaw.tickettracker.dto.QueueTableDto;
import com.garygriffaw.tickettracker.dto.UserAccountQueueDto;
import com.garygriffaw.tickettracker.dto.UserAccountTableDto;
import com.garygriffaw.tickettracker.dto.UserAccountUpdateDto;
import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.enums.Role;
import com.garygriffaw.tickettracker.services.QueueService;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserAccountController {

    // ---------------------------------------------------------------------------------------------------------------
    // Converters
    // ---------------------------------------------------------------------------------------------------------------
    @Autowired
    QueueConverter queueConverter;

    @Autowired
    RoleConverter roleConverter;

    @Autowired
    UserAccountConverter userAccountConverter;

    // ---------------------------------------------------------------------------------------------------------------
    // Services
    // ---------------------------------------------------------------------------------------------------------------
    @Autowired
    QueueService queueService;

    @Autowired
    UserAccountService userAccountService;

    // ---------------------------------------------------------------------------------------------------------------
    // Misc
    // ---------------------------------------------------------------------------------------------------------------
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    // ---------------------------------------------------------------------------------------------------------------
    // Display user list
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping
    public String processUsersUrl(Model model) {
        List<UserAccount> userAccounts = userAccountService.getAll();
        List<UserAccountTableDto> usersTable = userAccountConverter.entityListToUserAccountTableListDto(userAccounts);
        model.addAttribute("users", usersTable);

        return "users/list";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Update a user
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/update")
    public String processUserUpdateUrl(@RequestParam("id") String id, Model model) {
        UserAccount userAccount = userAccountService.findById(id);
        UserAccountUpdateDto userAccountUpdateDto = userAccountConverter.entityToUserAccountUpdateDto(userAccount);

        return displayUpdateUserAccountForm(model, userAccountUpdateDto);
    }

    @PostMapping(value="/save", params={"save=Save"})
    public String saveUser(Model model, @Valid UserAccountUpdateDto userAccountUpdateDto, Errors errors) {
        if(errors.hasErrors())
            return displayUpdateUserAccountForm(model, userAccountUpdateDto);

        UserAccount updateUserAccount = userAccountConverter.userAccountUpdateDtoToEntity(userAccountUpdateDto);
        userAccountService.save(updateUserAccount);

        return "redirect:/users";
    }

    @PostMapping(value="/save", params={"cancel=Cancel"})
    public String cancelSaveUser(Model model) {
        return processUsersUrl(model);
    }

    private String displayUpdateUserAccountForm(Model model, UserAccountUpdateDto userAccountUpdateDto) {
        userAccountUpdateDto.setRoleSelectListDto(roleConverter.entityListToRoleSelectListDto(Arrays.asList(Role.values())));
        userAccountUpdateDto.setQueues(getUserQueueListDto(userAccountUpdateDto.getUserName()));
        model.addAttribute("userAccountUpdateDto", userAccountUpdateDto);

        return "users/update-user";
    }

    private List<QueueTableDto> getUserQueueListDto(String userName) {
        List<Queue> queueList = new ArrayList<>();

        for(Queue queue : userAccountService.findById(userName).getQueues())
            queueList.add(queue);

        Collections.sort(queueList);

        return queueConverter.entityListToQueueTableDtoList(queueList);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Add a queue to a user
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/add-queue")
    public String processAddQueueUrl(@RequestParam("id") String userName, Model model) {
        UserAccount userAccount = userAccountService.findById(userName);

        List<Long> assignedQueueIds = new ArrayList<>();
        for(Queue assignedQueue : userAccount.getQueues()) {
            assignedQueueIds.add(assignedQueue.getQueueId());
        }

        List<Queue> queues = userAccountService.getUnassignedQueues(assignedQueueIds);
        Collections.sort(queues);

        UserAccountQueueDto userAccountQueueDto = userAccountConverter.entityToUserAccountQueueDto(userAccount);
        userAccountQueueDto.setQueueSelectDtoList(queueConverter.entityListToQueueSelectDtoList(queues));
        model.addAttribute("userAccountQueueDto", userAccountQueueDto);

        return "users/add-queue";
    }

    @PostMapping(value="save-queue", params={"save=Save"})
    public String addQueue(Model model, UserAccount userAccount, Queue queue) {
        UserAccount updateUserAccount = userAccountService.findById(userAccount.getUserName());

        if(queue.getQueueId() != null) {
            Queue addQueue = queueService.findById(queue.getQueueId());
            updateUserAccount.addQueue(addQueue);
            userAccountService.save(updateUserAccount);
        }

        return "redirect:/users/update?id=" + updateUserAccount.getUserName();
    }

    @PostMapping(value="save-queue", params={"cancel=Cancel"})
    public String cancelAddQueue(Model model, UserAccount userAccount) {
        UserAccount updateUserAccount = userAccountService.findById(userAccount.getUserName());
        UserAccountUpdateDto userAccountUpdateDto = userAccountConverter.entityToUserAccountUpdateDto(updateUserAccount);

        return displayUpdateUserAccountForm(model, userAccountUpdateDto);
    }
}
