package com.garygriffaw.tickettracker.dto;

import java.time.LocalDateTime;

public class TicketTableDto {
    private long ticketId;

    private String title;

    private LocalDateTime createdDateTime;

    private String ticketStatusDisplayValue;

    private String priorityDisplayValue;

    // Getters and Setters

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getTicketStatusDisplayValue() {
        return ticketStatusDisplayValue;
    }

    public void setTicketStatusDisplayValue(String ticketStatusDisplayValue) {
        this.ticketStatusDisplayValue = ticketStatusDisplayValue;
    }

    public String getPriorityDisplayValue() {
        return priorityDisplayValue;
    }

    public void setPriorityDisplayValue(String priorityDisplayValue) {
        this.priorityDisplayValue = priorityDisplayValue;
    }
}
