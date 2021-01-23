package com.garygriffaw.tickettracker.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TicketCommentDto {

    private Long id;

    private LocalDateTime createdDateTime;

    private String createdByUserName;

    private String createdByUserDisplayValue;

    @NotBlank(message = "Must provide a Comment")
    @Size(min=2, max=2000, message = "Comment must be between {min} and {max} characters long")
    private String commentText;

    private long ticketId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public String getCreatedByUserDisplayValue() {
        return createdByUserDisplayValue;
    }

    public void setCreatedByUserDisplayValue(String createdByUserDisplayValue) {
        this.createdByUserDisplayValue = createdByUserDisplayValue;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }
}
