package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.TicketStatusSelectDto;
import com.garygriffaw.tickettracker.enums.TicketStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketStatusConverter {

    // ---------------------------------------------------------------------------------------------------------------
    // TicketStatusSelectDto - Used to display the dropdown list of statuses to select from
    // ---------------------------------------------------------------------------------------------------------------
    public TicketStatusSelectDto entityToTicketStatusSelectDto(TicketStatus ticketStatus) {
        TicketStatusSelectDto dto = new TicketStatusSelectDto();

        dto.setStatusValue(ticketStatus.name());
        dto.setDisplayValue(ticketStatus.getDisplayValue());

        return dto;
    }

    public List<TicketStatusSelectDto> entityListToTicketStatusSelectListDto(List<TicketStatus> ticketStatuses) {
        return ticketStatuses.stream().map(x -> entityToTicketStatusSelectDto(x)).collect(Collectors.toList());
    }
}
