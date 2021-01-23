package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.TicketPrioritySelectDto;
import com.garygriffaw.tickettracker.enums.TicketPriority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketPriorityConverter {

    // ---------------------------------------------------------------------------------------------------------------
    // TicketPrioritySelectDto - Used to display the dropdown list of priorities to select from
    // ---------------------------------------------------------------------------------------------------------------
    public TicketPrioritySelectDto entityToTicketPrioritySelectDto(TicketPriority ticketPriority) {
        TicketPrioritySelectDto dto = new TicketPrioritySelectDto();

        dto.setPriorityValue(ticketPriority.name());
        dto.setDisplayValue(ticketPriority.getDisplayValue());
        dto.setSortOrder(ticketPriority.getSortOrder());

        return dto;
    }

    public List<TicketPrioritySelectDto> entityListToTicketPrioritySelectListDto(List<TicketPriority> ticketPriorities) {
        return ticketPriorities.stream().map(x -> entityToTicketPrioritySelectDto(x)).collect(Collectors.toList());
    }
}
