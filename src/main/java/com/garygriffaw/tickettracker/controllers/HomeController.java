package com.garygriffaw.tickettracker.controllers;

import com.garygriffaw.tickettracker.comparators.TicketPriorityComparator;
import com.garygriffaw.tickettracker.converters.TicketConverter;
import com.garygriffaw.tickettracker.dto.TicketTableDto;
import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.entities.Ticket;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.services.TicketService;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    TicketConverter ticketConverter;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserAccountService userAccountService;

    // ---------------------------------------------------------------------------------------------------------------
    // Display home page
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/")
    public String processHomeUrl(Model model, Principal principal) {
        return displayHomeList(model, principal);
    }

    public String displayHomeList(Model model, Principal principal) {
        UserAccount myUserAccount = userAccountService.findById(principal.getName());

        Comparator comparator = new TicketPriorityComparator();

        List<Ticket> myOpenCreatedOrOwnedTickets = ticketService.getMyOpenCreatedOrOwnedTickets(myUserAccount);
        Collections.sort(myOpenCreatedOrOwnedTickets, comparator);
        List<TicketTableDto> myOpenCreatedOrOwnedTicketDtos = ticketConverter.entityListToTicketTableDtoList(myOpenCreatedOrOwnedTickets);
        model.addAttribute("myOpenCreatedOrOwnedTickets", myOpenCreatedOrOwnedTicketDtos);

        List<Ticket> myOpenAssignedToTickets = ticketService.getMyOpenAssignedToTickets(myUserAccount);
        Collections.sort(myOpenAssignedToTickets, comparator);
        List<TicketTableDto> myOpenAssignedToTicketDtos = ticketConverter.entityListToTicketTableDtoList(myOpenAssignedToTickets);
        model.addAttribute("myOpenAssignedToTickets", myOpenAssignedToTicketDtos);

        Set<Queue> queues = myUserAccount.getQueues();
        List<Ticket> openUnassignedTicketsNotMine = ticketService.getOpenUnassignedTicketsNotMine(myUserAccount, queues);
        Collections.sort(openUnassignedTicketsNotMine, comparator);
        List<TicketTableDto> openUnassignedTicketsNotMineDtos = ticketConverter.entityListToTicketTableDtoList(openUnassignedTicketsNotMine);
        model.addAttribute("openUnassignedTicketsNotMine", openUnassignedTicketsNotMineDtos);

        return "main/home";
    }
}
