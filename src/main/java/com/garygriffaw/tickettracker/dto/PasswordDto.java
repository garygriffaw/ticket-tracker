package com.garygriffaw.tickettracker.dto;

import com.garygriffaw.tickettracker.validators.PasswordMatchesUpdate;
import com.garygriffaw.tickettracker.validators.PasswordValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@PasswordMatchesUpdate
public class PasswordDto {
    @NotBlank(message = "Must provide a Username")
    @Size(min=2, max=20, message = "Username must be between {min} and {max} characters long")
    private String userName;

    @NotBlank(message = "Must provide a Password")
    @PasswordValid
    private String password;
    private String confirmPassword;

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
