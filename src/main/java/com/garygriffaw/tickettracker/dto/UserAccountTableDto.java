package com.garygriffaw.tickettracker.dto;

public class UserAccountTableDto {
    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String roleDisplayValue;

    private boolean enabled;

    // Getters and Setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleDisplayValue() {
        return roleDisplayValue;
    }

    public void setRoleDisplayValue(String roleDisplayValue) {
        this.roleDisplayValue = roleDisplayValue;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
