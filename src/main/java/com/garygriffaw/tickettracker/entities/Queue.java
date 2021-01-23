package com.garygriffaw.tickettracker.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Queue implements Comparable<Queue> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queueId;

    @NotBlank(message = "Must provide a Queue Name")
    @Size(min=2, max=30, message = "Queue Name must be between {min} and {max} characters long")
    private String queueName;

    @ManyToMany(mappedBy = "queues")
    Set<UserAccount> userAccounts;

    public Queue() {
    }

    @Override
    public int compareTo(Queue o) {
        return this.getQueueName().compareTo(o.getQueueName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Queue)) return false;
        return queueId != null && queueId.equals(((Queue) o).getQueueId());    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Generated Getters and Setters

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }
}
