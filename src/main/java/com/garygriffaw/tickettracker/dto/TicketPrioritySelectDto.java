package com.garygriffaw.tickettracker.dto;

public class TicketPrioritySelectDto {
    private String priorityValue;

    private String displayValue;

    private int sortOrder;

    // Getters and Setters
    public String getPriorityValue() {
        return priorityValue;
    }

    public void setPriorityValue(String priorityValue) {
        this.priorityValue = priorityValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
