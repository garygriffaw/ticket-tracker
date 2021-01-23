package com.garygriffaw.tickettracker.services;

import com.garygriffaw.tickettracker.entities.TicketComment;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.repositories.TicketCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketCommentService {

    @Autowired
    TicketCommentRepository ticketCommentRepository;

    @Autowired
    UserAccountService userAccountService;

    public TicketComment save(TicketComment comment) {
        return ticketCommentRepository.save(comment);
    }

    public List<TicketComment> getAll() {
        return (List<TicketComment>) ticketCommentRepository.findAll();
    }

    public TicketComment setNewValues(TicketComment comment, Principal principal) {
        UserAccount currentLoggedInUser = setCurrentLoggedInUser(principal);

        comment.setCreatedBy(currentLoggedInUser);
        comment.setCreatedDateTime(LocalDateTime.now());

        return comment;
    }

    private UserAccount setCurrentLoggedInUser(Principal principal) {
        return userAccountService.findById(principal.getName());
    }
}
