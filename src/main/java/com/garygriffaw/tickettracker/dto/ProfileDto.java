package com.garygriffaw.tickettracker.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProfileDto {
    @NotBlank(message = "Must provide a Username")
    @Size(min=2, max=20, message = "Username must be between {min} and {max} characters long")
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
}
