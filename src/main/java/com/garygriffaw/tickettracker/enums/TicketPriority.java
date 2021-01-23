package com.garygriffaw.tickettracker.enums;

public enum TicketPriority {
    LOW(0,"Low"),
    MEDIUM(1,"Medium"),
    HIGH(2,"High"),
    URGENT(3,"Urgent");

    private final int sortOrder;
    private final String displayValue;

    private TicketPriority(int sortOrder, String displayValue) {
        this.sortOrder = sortOrder;
        this.displayValue = displayValue;
    }

    public int getSortOrder() { return sortOrder; }

    public String getDisplayValue() {
        return displayValue;
    }

}
