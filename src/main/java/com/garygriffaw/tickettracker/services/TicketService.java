package com.garygriffaw.tickettracker.services;

import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.entities.Ticket;
import com.garygriffaw.tickettracker.entities.TicketPrevValues;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.enums.TicketStatus;
import com.garygriffaw.tickettracker.helpers.StringBuilderPlus;
import com.garygriffaw.tickettracker.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    QueueService queueService;

    @Autowired
    UserAccountService userAccountService;

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket setNewValues(Ticket ticket, Principal principal) {
        UserAccount currentLoggedInUser = setCurrentLoggedInUser(principal);

        ticket.setCreatedBy(currentLoggedInUser);
        ticket.setOwnedBy(currentLoggedInUser);

        return ticket;
    }

    public TicketPrevValues setTicketPrevValues(long id) {
        // These values are used in createUpdateComment
        Ticket oldTicket = findById(id);

        TicketPrevValues ticketPrevValues = new TicketPrevValues();
        ticketPrevValues.setTicketStatus(oldTicket.getTicketStatus());
        ticketPrevValues.setPriority(oldTicket.getPriority());
        ticketPrevValues.setOwnedBy(oldTicket.getOwnedBy());
        ticketPrevValues.setAssignedTo(oldTicket.getAssignedTo());
        ticketPrevValues.setQueue(oldTicket.getQueue());

        return ticketPrevValues;
    }

    public Ticket setClosedByValues(TicketPrevValues prevValues, Ticket updateTicket, Principal principal) {
        UserAccount currentLoggedInUser = setCurrentLoggedInUser(principal);

        if(prevValues.getTicketStatus().equals(TicketStatus.COMPLETE) ||
                prevValues.getTicketStatus().equals(TicketStatus.CANCELLED)) {
            if(!updateTicket.getTicketStatus().equals(TicketStatus.COMPLETE) &&
                    !updateTicket.getTicketStatus().equals(TicketStatus.CANCELLED)) {
                updateTicket.setClosedBy(null);
                updateTicket.setClosedDateTime(null);
            }
        } else {
            if(updateTicket.getTicketStatus().equals(TicketStatus.COMPLETE) ||
                    updateTicket.getTicketStatus().equals(TicketStatus.CANCELLED)) {
                updateTicket.setClosedBy(currentLoggedInUser);
                updateTicket.setClosedDateTime(LocalDateTime.now());
            }
        }

        return updateTicket;
    }

    public String createUpdateComment(TicketPrevValues prevValues, Ticket updateTicket, Principal principal) {
        StringBuilderPlus commentText = new StringBuilderPlus();

        commentText = appendCommentText(commentText, "Status",
                prevValues.getTicketStatusDisplayValue(), updateTicket.getTicketStatusDisplayValue());
        commentText = appendCommentText(commentText, "Priority",
                prevValues.getTicketPriorityDisplayValue(), updateTicket.getTicketPriorityDisplayValue());
        commentText = appendCommentText(commentText, "Owned By",
                getUser(prevValues.getOwnedBy()), getUser(updateTicket.getOwnedBy()));
        commentText = appendCommentText(commentText, "Assigned To",
                getUser(prevValues.getAssignedTo()), getUser(updateTicket.getAssignedTo()));
        commentText = appendCommentText(commentText, "Queue",
                prevValues.getQueueDisplayValue(), updateTicket.getQueueDisplayValue());

        return commentText.toString();
    }

    private StringBuilderPlus appendCommentText(StringBuilderPlus commentText,
                                                String fieldName, String oldValue, String newValue) {
        if(!newValue.equals(oldValue)) {
            String changedMsg = fieldName + " changed from " + oldValue + " to " + newValue + ".";
            commentText.appendLine(changedMsg);
        }

        return commentText;
    }

    public List<Ticket> getAll() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    public List<Ticket> getAllByTicketStatus(String ticketStatus) {
        return(List<Ticket>) ticketRepository.findAllByTicketStatus(ticketStatus);
    }

    public List<Ticket> getMyOpenCreatedOrOwnedTickets(UserAccount myUserAccount) {
        return(List<Ticket>) ticketRepository.findMyOpenCreatedOrOwnedTickets(myUserAccount);
    }

    public List<Ticket> getMyOpenAssignedToTickets(UserAccount myUserAccount) {
        return(List<Ticket>) ticketRepository.findMyOpenAssignedToTickets(myUserAccount);
    }

    public List<Ticket> getOpenUnassignedTicketsNotMine(UserAccount myUserAccount, Set<Queue> queues) {
        return(List<Ticket>) ticketRepository.findOpenUnassignedTicketsNotMine(myUserAccount, queues);
    }

    public List<Ticket> getMyClosedTickets(UserAccount myUserAccount) {
        return(List<Ticket>) ticketRepository.findMyClosedTickets(myUserAccount);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).get();
    }

    private UserAccount setCurrentLoggedInUser(Principal principal) {
        return userAccountService.findById(principal.getName());
    }

    private String getUser(UserAccount userAccount) {
        if(userAccount == null || userAccount.getUserName().isEmpty())
            return "blank";

        return userAccount.getUserDisplayValue();
    }
}
