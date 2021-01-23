package com.garygriffaw.tickettracker.enums;

public enum TicketStatus {
    UNASSIGNED("Unassigned"),
    ASSIGNED("Assigned"),
    INWORK("In Work"),
    COMPLETE("Complete"),
    CANCELLED("Cancelled");

    private final String displayValue;

    private TicketStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
