package com.garygriffaw.tickettracker.dto;

import java.util.List;

public class TicketPriorityListDto {
    private List<TicketPrioritySelectDto> ticketPrioritySelectDtos;

    // Getters and Setters
    public List<TicketPrioritySelectDto> getTicketPriorityDtos() {
        return ticketPrioritySelectDtos;
    }

    public void setTicketPriorityDtos(List<TicketPrioritySelectDto> ticketPrioritySelectDtos) {
        this.ticketPrioritySelectDtos = ticketPrioritySelectDtos;
    }
}
