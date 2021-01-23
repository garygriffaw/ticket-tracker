package com.garygriffaw.tickettracker.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class TicketCreateDto {
    @NotBlank(message = "Must provide a Title")
    @Size(min=2, max=50, message = "Title must be between {min} and {max} characters long")
    private String title;

    @NotBlank(message = "Must provide a Description")
    @Size(min=2, max=2000, message = "Description must be between {min} and {max} characters long")
    private String description;

    @NotNull(message = "Must provide the Queue")
    private Long queueId;

    private List<QueueSelectDto> queueSelectListDto;

    @NotNull(message = "Must provide a Ticket Priority")
    private String priority;

    private List<TicketPrioritySelectDto> ticketPrioritySelectListDto;

    // Getters and Setters
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
}
