package com.garygriffaw.tickettracker.dto;

public class TicketStatusSelectDto {
    private String statusValue;

    private String displayValue;

    // Getters and Setters
    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}
