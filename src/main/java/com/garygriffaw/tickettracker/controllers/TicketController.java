package com.garygriffaw.tickettracker.controllers;

import com.garygriffaw.tickettracker.comparators.TicketCreatedDateTimeComparator;
import com.garygriffaw.tickettracker.converters.*;
import com.garygriffaw.tickettracker.dto.*;
import com.garygriffaw.tickettracker.entities.Ticket;
import com.garygriffaw.tickettracker.entities.TicketComment;
import com.garygriffaw.tickettracker.entities.TicketPrevValues;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.enums.TicketPriority;
import com.garygriffaw.tickettracker.enums.TicketStatus;
import com.garygriffaw.tickettracker.services.QueueService;
import com.garygriffaw.tickettracker.services.TicketCommentService;
import com.garygriffaw.tickettracker.services.TicketService;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    // ---------------------------------------------------------------------------------------------------------------
    // Converters
    // ---------------------------------------------------------------------------------------------------------------
    @Autowired
    TicketCommentConverter ticketCommentConverter;

    @Autowired
    TicketConverter ticketConverter;

    @Autowired
    TicketPriorityConverter ticketPriorityConverter;

    @Autowired
    QueueConverter ticketQueueConverter;

    @Autowired
    TicketStatusConverter ticketStatusConverter;

    @Autowired
    UserAccountConverter userAccountConverter;

    // ---------------------------------------------------------------------------------------------------------------
    // Services
    // ---------------------------------------------------------------------------------------------------------------
    @Autowired
    TicketService ticketService;

    @Autowired
    TicketCommentService ticketCommentService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    QueueService queueService;

    // ---------------------------------------------------------------------------------------------------------------
    // Controllers
    // ---------------------------------------------------------------------------------------------------------------
    @Autowired
    HomeController homeController;

    @Autowired
    QueueController queueController;


    // ---------------------------------------------------------------------------------------------------------------
    // Create new ticket
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/new")
    public String processNewTicketUrl(Model model, Principal principal) {
        TicketCreateDto ticketCreateDto = new TicketCreateDto();

        return displayNewTicketForm(model, ticketCreateDto);
    }

    @PostMapping(value="/new-save", params={"save=Save"})
        public String createTicket(Model model, @Valid TicketCreateDto ticketCreateDto, Errors errors,
                                   Principal principal) {
        if(errors.hasErrors())
            return displayNewTicketForm(model, ticketCreateDto);

        Ticket ticket = ticketConverter.ticketCreateDtoToEntity(ticketCreateDto);
        ticket = ticketService.setNewValues(ticket, principal);
        ticketService.save(ticket);

        return "redirect:/";
    }

    @PostMapping(value="/new-save", params={"cancel=Cancel"})
    public String cancelCreateTicket(Model model, Principal principal) {
        return homeController.displayHomeList(model, principal);
    }

    public String displayNewTicketForm(Model model, TicketCreateDto ticketCreateDto) {
        ticketCreateDto.setQueueSelectListDto(queueController.getQueueSelectListDto());
        ticketCreateDto.setTicketPrioritySelectListDto(getTicketPrioritySelectListDto());

        model.addAttribute("ticketCreateDto", ticketCreateDto);

        return "tickets/new-ticket";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Update ticket
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/update")
    public String processTicketUpdateUrl(@RequestParam("id") long id, Model model, Principal principal) {
        Ticket updateTicket = ticketService.findById(id);

        TicketUpdateDto ticketUpdateDto = ticketConverter.entityToTicketUpdateDto(updateTicket);

        return displayUpdateTicketForm(model, ticketUpdateDto);
    }

    @PostMapping(value="/update-save", params={"save=Save"})
    public String updateTicket(Model model, @Valid TicketUpdateDto ticketUpdateDto, Errors errors,
                               Principal principal) {
        if(errors.hasErrors()) {
            return displayUpdateTicketForm(model, ticketUpdateDto);
        }

        TicketPrevValues ticketPrevValues = ticketService.setTicketPrevValues(ticketUpdateDto.getTicketId());

        Ticket updateTicket = ticketConverter.ticketUpdateDtoToEntity(ticketUpdateDto);

        updateTicket = ticketService.setClosedByValues(ticketPrevValues, updateTicket, principal);

        // System generated comment based on updated fields
        String updateCommentText = ticketService.createUpdateComment(ticketPrevValues, updateTicket, principal);
        if(updateCommentText != null && !updateCommentText.equals("")) {
            TicketComment updateComment = new TicketComment();
            updateComment = ticketCommentService.setNewValues(updateComment, principal);
            updateComment.setCommentText(updateCommentText);
            updateTicket.addComment(updateComment);
        }

        ticketService.save(updateTicket);

        return "redirect:/";
    }

    @PostMapping(value="/update-save", params={"cancel=Cancel"})
    public String cancelUpdateTicket(Model model, Principal principal) {
        return homeController.displayHomeList(model, principal);
    }

    public String displayUpdateTicketForm(Model model, TicketUpdateDto ticketUpdateDto) {
        List<UserAccountSelectDto> userAccountSelectDtoList = getUserAccountToSelectDtoList();

        ticketUpdateDto.setOwnedBySelectListDto(userAccountSelectDtoList);
        ticketUpdateDto.setAssignedToSelectListDto(userAccountSelectDtoList);
        ticketUpdateDto.setTicketStatusSelectListDto(getTicketStatusSelectListDto());
        ticketUpdateDto.setQueueSelectListDto(queueController.getQueueSelectListDto());
        ticketUpdateDto.setTicketPrioritySelectListDto(getTicketPrioritySelectListDto());
        ticketUpdateDto.setComments(getTicketCommentListDto(ticketUpdateDto.getTicketId()));

        model.addAttribute("ticketUpdateDto", ticketUpdateDto);

        return "tickets/update-ticket";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Add a comment to a ticket
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/new-comment")
    public String processNewCommentUrl(@RequestParam("id") long id, Model model) {
        TicketCommentDto ticketCommentDto = new TicketCommentDto();
        ticketCommentDto.setTicketId(id);
        model.addAttribute("ticketCommentDto", ticketCommentDto);

        return "tickets/new-comment";
    }

    @PostMapping(value="/save-comment", params={"save=Save"})
    public String createComment(Model model, TicketCommentDto commentDto, Principal principal) {
        TicketComment comment = ticketCommentConverter.ticketCommentDtoToEntity(commentDto);
        comment = ticketCommentService.setNewValues(comment, principal);

        Ticket updateTicket = ticketService.findById(commentDto.getTicketId());
        updateTicket.addComment(comment);

        ticketService.save(updateTicket);

        return "redirect:/tickets/update?id=" + updateTicket.getTicketId();
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Display closed tickets
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/myclosed")
    public String processMyClosedUrl(Model model, Principal principal) {
        UserAccount myUserAccount = userAccountService.findById(principal.getName());

        Comparator comparator = new TicketCreatedDateTimeComparator();

        List<Ticket> myClosedTickets = ticketService.getMyClosedTickets(myUserAccount);
        Collections.sort(myClosedTickets, comparator);
        List<TicketTableDto> myClosedTicketsDto = ticketConverter.entityListToTicketTableDtoList(myClosedTickets);
        model.addAttribute("myClosedTickets", myClosedTicketsDto);

        return "tickets/myclosed-tickets";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // View a ticket
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/view")
    public String processTicketViewUrl(@RequestParam("id") long id, Model model) {
        Ticket ticket = ticketService.findById(id);

        TicketViewDto ticketViewDto = ticketConverter.entityToTicketViewDto(ticket);
        ticketViewDto.setComments(getTicketCommentListDto(ticketViewDto.getTicketId()));

        model.addAttribute("ticketViewDto", ticketViewDto);

        return "tickets/view-ticket";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // General methods
    // ---------------------------------------------------------------------------------------------------------------
    private List<UserAccountSelectDto> getUserAccountToSelectDtoList() {
        List<UserAccount> userAccounts = userAccountService.getAll();
        Collections.sort(userAccounts);
        return userAccountConverter.entityListToUserAccountSelectDtoList(userAccounts);
    }

    private List<TicketPrioritySelectDto> getTicketPrioritySelectListDto() {
        return ticketPriorityConverter.entityListToTicketPrioritySelectListDto(Arrays.asList(TicketPriority.values()));
    }

    private List<TicketStatusSelectDto> getTicketStatusSelectListDto() {
        return ticketStatusConverter.entityListToTicketStatusSelectListDto(Arrays.asList(TicketStatus.values()));
    }

    private List<TicketCommentDto> getTicketCommentListDto(long ticketId) {
        List<TicketComment> commentList = ticketService.findById(ticketId).getComments();
        Collections.sort(commentList, Collections.reverseOrder());
        return ticketCommentConverter.entityListToTicketCommentListDto(commentList);
    }
}
