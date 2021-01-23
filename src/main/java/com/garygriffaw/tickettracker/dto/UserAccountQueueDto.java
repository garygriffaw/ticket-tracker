package com.garygriffaw.tickettracker.dto;

import java.util.List;

public class UserAccountQueueDto {
    private String userName;

    private Long queueId;

    private List<QueueSelectDto> queueSelectDtoList;

    // Getters and Setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public List<QueueSelectDto> getQueueSelectDtoList() {
        return queueSelectDtoList;
    }

    public void setQueueSelectDtoList(List<QueueSelectDto> queueSelectDtoList) {
        this.queueSelectDtoList = queueSelectDtoList;
    }
}
