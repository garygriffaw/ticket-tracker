package com.garygriffaw.tickettracker.enums;

public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private final String displayValue;

    private Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
