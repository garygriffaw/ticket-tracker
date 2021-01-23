package com.garygriffaw.tickettracker.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class QueueCreateDto {
    @NotBlank(message = "Must provide a Queue Name")
    @Size(min=2, max=30, message = "Queue Name must be between {min} and {max} characters long")
    private String queueName;

    // Getters and Setters

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
