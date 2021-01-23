package com.garygriffaw.tickettracker.dto;

import com.garygriffaw.tickettracker.validators.AssignedToInQueue;
import com.garygriffaw.tickettracker.validators.StatusAssignedToValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@StatusAssignedToValid
@AssignedToInQueue
public class TicketUpdateDto {
    private long ticketId;

    @NotBlank(message = "Must provide a Title")
    @Size(min=2, max=50, message = "Title must be between {min} and {max} characters long")
    private String title;

    @NotBlank(message = "Must provide a Description")
    @Size(min=2, max=2000, message = "Description must be between {min} and {max} characters long")
    private String description;

    @NotNull(message = "Must provide the Created Datetime")
    private LocalDateTime createdDateTime;

    private String createdByUserName;

    private String createdByUserDisplayValue;

    @NotNull(message = "Must provide Owned By")
    private String ownedByUserName;

    private List<UserAccountSelectDto> ownedBySelectListDto;

    private String assignedToUserName;

    private List<UserAccountSelectDto> assignedToSelectListDto;

    @NotNull(message = "Must provide a Ticket Status")
    private String ticketStatus;

    private List<TicketStatusSelectDto> ticketStatusSelectListDto;

    @NotNull(message = "Must provide the Queue")
    private Long queueId;

    private List<QueueSelectDto> queueSelectListDto;

    @NotNull(message = "Must provide a Ticket Priority")
    private String priority;

    private List<TicketPrioritySelectDto> ticketPrioritySelectListDto;

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

    public String getOwnedByUserName() {
        return ownedByUserName;
    }

    public void setOwnedByUserName(String ownedByUserName) {
        this.ownedByUserName = ownedByUserName;
    }

    public List<UserAccountSelectDto> getOwnedBySelectListDto() {
        return ownedBySelectListDto;
    }

    public void setOwnedBySelectListDto(List<UserAccountSelectDto> ownedBySelectListDto) {
        this.ownedBySelectListDto = ownedBySelectListDto;
    }

    public String getAssignedToUserName() {
        return assignedToUserName;
    }

    public void setAssignedToUserName(String assignedToUserName) {
        this.assignedToUserName = assignedToUserName;
    }

    public List<UserAccountSelectDto> getAssignedToSelectListDto() {
        return assignedToSelectListDto;
    }

    public void setAssignedToSelectListDto(List<UserAccountSelectDto> assignedToSelectListDto) {
        this.assignedToSelectListDto = assignedToSelectListDto;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public List<TicketStatusSelectDto> getTicketStatusSelectListDto() {
        return ticketStatusSelectListDto;
    }

    public void setTicketStatusSelectListDto(List<TicketStatusSelectDto> ticketStatusSelectListDto) {
        this.ticketStatusSelectListDto = ticketStatusSelectListDto;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public List<QueueSelectDto> getQueueSelectListDto() {
        return queueSelectListDto;
    }

    public void setQueueSelectListDto(List<QueueSelectDto> queueSelectListDto) {
        this.queueSelectListDto = queueSelectListDto;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<TicketPrioritySelectDto> getTicketPrioritySelectListDto() {
        return ticketPrioritySelectListDto;
    }

    public void setTicketPrioritySelectListDto(List<TicketPrioritySelectDto> ticketPrioritySelectListDto) {
        this.ticketPrioritySelectListDto = ticketPrioritySelectListDto;
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
