package com.garygriffaw.tickettracker.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TicketViewDto {
    private long ticketId;

    private String title;

    private String description;

    private LocalDateTime createdDateTime;

    private String createdByUserDisplayValue;

    private String ownedByUserDisplayValue;

    private String assignedToUserDisplayValue;

    private String ticketStatusDisplayValue;

    private String queueDisplayValue;

    private String priorityDisplayValue;

    private LocalDateTime closedDateTime;;

    private String closedByUserDisplayValue;

    private String closureComment;

    private List<TicketCommentDto> comments;

    // Getters and Setters

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedByUserDisplayValue() {
        return createdByUserDisplayValue;
    }

    public void setCreatedByUserDisplayValue(String createdByUserDisplayValue) {
        this.createdByUserDisplayValue = createdByUserDisplayValue;
    }

    public String getOwnedByUserDisplayValue() {
        return ownedByUserDisplayValue;
    }

    public void setOwnedByUserDisplayValue(String ownedByUserDisplayValue) {
        this.ownedByUserDisplayValue = ownedByUserDisplayValue;
    }

    public String getAssignedToUserDisplayValue() {
        return assignedToUserDisplayValue;
    }

    public void setAssignedToUserDisplayValue(String assignedToUserDisplayValue) {
        this.assignedToUserDisplayValue = assignedToUserDisplayValue;
    }

    public String getTicketStatusDisplayValue() {
        return ticketStatusDisplayValue;
    }

    public void setTicketStatusDisplayValue(String ticketStatusDisplayValue) {
        this.ticketStatusDisplayValue = ticketStatusDisplayValue;
    }

    public String getQueueDisplayValue() {
        return queueDisplayValue;
    }

    public void setQueueDisplayValue(String queueDisplayValue) {
        this.queueDisplayValue = queueDisplayValue;
    }

    public String getPriorityDisplayValue() {
        return priorityDisplayValue;
    }

    public void setPriorityDisplayValue(String priorityDisplayValue) {
        this.priorityDisplayValue = priorityDisplayValue;
    }

    public LocalDateTime getClosedDateTime() {
        return closedDateTime;
    }

    public void setClosedDateTime(LocalDateTime closedDateTime) {
        this.closedDateTime = closedDateTime;
    }

    public String getClosedByUserDisplayValue() {
        return closedByUserDisplayValue;
    }

    public void setClosedByUserDisplayValue(String closedByUserDisplayValue) {
        this.closedByUserDisplayValue = closedByUserDisplayValue;
    }

    public String getClosureComment() {
        return closureComment;
    }

    public void setClosureComment(String closureComment) {
        this.closureComment = closureComment;
    }

    public List<TicketCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<TicketCommentDto> comments) {
        this.comments = comments;
    }
}
