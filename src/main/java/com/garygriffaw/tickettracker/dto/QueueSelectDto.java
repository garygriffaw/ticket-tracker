package com.garygriffaw.tickettracker.dto;

public class QueueSelectDto {
    private Long queueId;

    private String queueName;

    // Getters and Setters

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
}
