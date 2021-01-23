package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.TicketCreateDto;
import com.garygriffaw.tickettracker.dto.TicketTableDto;
import com.garygriffaw.tickettracker.dto.TicketUpdateDto;
import com.garygriffaw.tickettracker.dto.TicketViewDto;
import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.entities.Ticket;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.enums.TicketPriority;
import com.garygriffaw.tickettracker.enums.TicketStatus;
import com.garygriffaw.tickettracker.services.QueueService;
import com.garygriffaw.tickettracker.services.TicketService;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketConverter {

    @Autowired
    QueueService queueService;

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketCommentConverter ticketCommentConverter;

    @Autowired
    UserAccountService userAccountService;

    // ---------------------------------------------------------------------------------------------------------------
    // TicketCreateDto - Used when creating a new ticket
    // ---------------------------------------------------------------------------------------------------------------
    public Ticket ticketCreateDtoToEntity(TicketCreateDto dto) {
        Ticket ticket = new Ticket();

        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setQueue(queueService.findById(dto.getQueueId()));
        ticket.setPriority(TicketPriority.valueOf(dto.getPriority()));

        return ticket;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // TicketTableDto - Used to display a list of tickets
    // ---------------------------------------------------------------------------------------------------------------
    public TicketTableDto entityToTicketTableDto(Ticket ticket) {
        TicketTableDto dto = new TicketTableDto();

        dto.setTicketId(ticket.getTicketId());
        dto.setTitle(ticket.getTitle());
        dto.setCreatedDateTime(ticket.getCreatedDateTime());
        dto.setTicketStatusDisplayValue(ticket.getTicketStatusDisplayValue());
        dto.setPriorityDisplayValue(ticket.getTicketPriorityDisplayValue());

        return dto;
    }

    public List<TicketTableDto> entityListToTicketTableDtoList(List<Ticket> tickets) {
        return tickets.stream().map(x -> entityToTicketTableDto(x)).collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------------------------------------------------
    // TicketUpdateDto - Used to update a ticket
    // ---------------------------------------------------------------------------------------------------------------
    public TicketUpdateDto entityToTicketUpdateDto(Ticket ticket) {
        TicketUpdateDto dto = new TicketUpdateDto();

        dto.setTicketId(ticket.getTicketId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setCreatedDateTime(ticket.getCreatedDateTime());
        dto.setCreatedByUserName(getUserName(ticket.getCreatedBy()));
        dto.setCreatedByUserDisplayValue(getUserDisplayValue(ticket.getCreatedBy()));
        dto.setOwnedByUserName(getUserName(ticket.getOwnedBy()));
        dto.setAssignedToUserName(getUserName(ticket.getAssignedTo()));
        dto.setTicketStatus(ticket.getTicketStatus().name());
        dto.setQueueId(getQueueId(ticket.getQueue()));
        dto.setPriority(ticket.getPriority().name());
        dto.setClosureComment(ticket.getClosureComment());
        dto.setComments(ticketCommentConverter.entityListToTicketCommentListDto(ticket.getComments()));

        return dto;
    }

    public Ticket ticketUpdateDtoToEntity(TicketUpdateDto dto) {
        Ticket ticket = ticketService.findById(dto.getTicketId());

        ticket.setOwnedBy(userAccountService.findById(dto.getOwnedByUserName()));
        ticket.setAssignedTo(getUserAccount(dto.getAssignedToUserName()));
        ticket.setTicketStatus(TicketStatus.valueOf(dto.getTicketStatus()));
        ticket.setQueue(getQueue(dto.getQueueId()));
        ticket.setPriority(TicketPriority.valueOf(dto.getPriority()));
        ticket.setClosureComment(dto.getClosureComment());

        return ticket;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // TicketViewDto - Used to view a ticket
    // ---------------------------------------------------------------------------------------------------------------
    public TicketViewDto entityToTicketViewDto(Ticket ticket) {
        TicketViewDto dto = new TicketViewDto();

        dto.setTicketId(ticket.getTicketId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setCreatedDateTime(ticket.getCreatedDateTime());
        dto.setCreatedByUserDisplayValue(getUserDisplayValue(ticket.getCreatedBy()));
        dto.setOwnedByUserDisplayValue(getUserDisplayValue(ticket.getOwnedBy()));
        dto.setAssignedToUserDisplayValue(getUserDisplayValue(ticket.getAssignedTo()));
        dto.setTicketStatusDisplayValue(ticket.getTicketStatusDisplayValue());
        dto.setQueueDisplayValue(getQueueDisplayValue(ticket.getQueue()));
        dto.setPriorityDisplayValue(ticket.getTicketPriorityDisplayValue());
        dto.setClosedDateTime(ticket.getClosedDateTime());
        dto.setClosedByUserDisplayValue(getUserDisplayValue(ticket.getClosedBy()));
        dto.setClosureComment(ticket.getClosureComment());

        return dto;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // General methods
    // ---------------------------------------------------------------------------------------------------------------
    private UserAccount getUserAccount(String userName) {
        if(userName.isEmpty() || userName == null)
            return null;
        else
            return userAccountService.findById(userName);
    }

    private String getUserName(UserAccount userAccount) {
        if(userAccount != null)
            return userAccount.getUserName();
        else
            return "";
    }

    private String getUserDisplayValue(UserAccount userAccount) {
        if(userAccount != null)
            return userAccount.getUserDisplayValue();
        else
            return "";
    }

    private Queue getQueue(Long id) {
        if(id == null)
            return null;
        else
            return queueService.findById(id);
    }

    private Long getQueueId(Queue queue) {
        if(queue != null)
            return queue.getQueueId();
        else
            return 0L;
    }

    private String getQueueDisplayValue(Queue queue) {
        if(queue != null)
            return queue.getQueueName();
        else
            return "";
    }
}
