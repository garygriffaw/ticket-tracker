package com.garygriffaw.tickettracker.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserAccountUpdateDto {
    private String userName;

    @NotBlank(message = "Must provide a First Name")
    @Size(min=2, max=50, message = "First Name must be between {min} and {max} characters long")
    private String firstName;

    @NotBlank(message = "Must provide a Last Name")
    @Size(min=1, max=50, message = "Last Name must be between {min} and {max} characters long")
    private String lastName;

    @NotBlank(message = "Must provide an Email")
    @Email(message = "Email must have a valid email format")
    private String email;

    @NotNull(message = "Must provide a Role")
    private String role;

    private List<RoleSelectDto> roleSelectListDto;

    private boolean enabled;

    private List<QueueTableDto> queues;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<RoleSelectDto> getRoleSelectListDto() {
        return roleSelectListDto;
    }

    public void setRoleSelectListDto(List<RoleSelectDto> roleSelectListDto) {
        this.roleSelectListDto = roleSelectListDto;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<QueueTableDto> getQueues() {
        return queues;
    }

    public void setQueues(List<QueueTableDto> queues) {
        this.queues = queues;
    }
}
