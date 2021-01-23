package com.garygriffaw.tickettracker.entities;

import com.garygriffaw.tickettracker.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class UserAccount implements Comparable<UserAccount> {

    @Id
    @NotBlank(message = "Must provide a Username")
    @Size(min=2, max=20, message = "Username must be between {min} and {max} characters long")
    @Column(unique=true)
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

    private String password;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Must provide a Role")
    private Role role = Role.ROLE_USER;

    private boolean enabled = true;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(name = "useraccount_queue",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "queue_id"))
    private Set<Queue> queues;

    public UserAccount() {
    }

    public String getUserDisplayValue() {
        return lastName + ", " + firstName + " (" + userName + ")";
    }

    public String getRoleDisplayValue() {
        return role.getDisplayValue();
    }

    public void addQueue(Queue queue) {
        queues.add(queue);
        queue.getUserAccounts().add(this);
    }

    public void removeQueue(Queue queue) {
        queues.remove(queue);
        queue.getUserAccounts().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;
        return userName != null && userName.equals(((UserAccount) o).getUserName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public int compareTo(UserAccount o) {
        return this.getUserDisplayValue().compareTo(o.getUserDisplayValue());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    // Generated Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Queue> getQueues() {
        return queues;
    }

    public void setQueues(Set<Queue> queues) {
        this.queues = queues;
    }
}

