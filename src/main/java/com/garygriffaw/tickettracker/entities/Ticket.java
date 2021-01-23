package com.garygriffaw.tickettracker.entities;

import com.garygriffaw.tickettracker.enums.TicketPriority;
import com.garygriffaw.tickettracker.enums.TicketStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;

    @NotBlank(message = "Must provide a Title")
    @Size(min=2, max=50, message = "Title must be between {min} and {max} characters long")
    private String title;

    @Lob
    @NotBlank(message = "Must provide a Description")
    @Size(min=2, max=2000, message = "Description must be between {min} and {max} characters long")
    private String description;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Must provide a Ticket Status")
    private TicketStatus ticketStatus = TicketStatus.UNASSIGNED;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Must provide a Ticket Priority")
    private TicketPriority priority = TicketPriority.LOW;

    @NotNull(message = "Must provide the Created Datetime")
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "created_by")
    @NotNull(message = "Must provide Created By")
    private UserAccount createdBy;

    @ManyToOne
    @JoinColumn(name = "owned_by")
    @NotNull(message = "Must provide Owned By")
    private UserAccount ownedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private UserAccount assignedTo;

    private LocalDateTime closedDateTime;

    @ManyToOne
    @JoinColumn(name = "closed_by")
    private UserAccount closedBy;

    @Lob
    private String closureComment;

    @ManyToOne
    @JoinColumn(name = "queue")
    @NotNull(message = "Must provide the Queue")
    private Queue queue;

    @OneToMany(mappedBy = "ticket",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<TicketComment> comments;

    public Ticket() {
    }

    public String getTicketStatusDisplayValue() {
        return ticketStatus.getDisplayValue();
    }

    public String getTicketPriorityDisplayValue() {
        return priority.getDisplayValue();
    }

    public int getTicketPrioritySortOrder() { return priority.getSortOrder(); }

    public String getQueueDisplayValue() { return queue.getQueueName(); }

    public void addComment(TicketComment comment) {
        comments.add(comment);
        comment.setTicket(this);
    }

    public void removeComment(TicketComment comment) {
        comments.remove(comment);
        comment.setTicket(null);
    }

    // Generated Getters and Setters
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

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public UserAccount getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserAccount createdBy) {
        this.createdBy = createdBy;
    }

    public UserAccount getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(UserAccount ownedBy) {
        this.ownedBy = ownedBy;
    }

    public UserAccount getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserAccount assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getClosedDateTime() {
        return closedDateTime;
    }

    public void setClosedDateTime(LocalDateTime closedDateTime) {
        this.closedDateTime = closedDateTime;
    }

    public UserAccount getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(UserAccount closedBy) {
        this.closedBy = closedBy;
    }

    public String getClosureComment() {
        return closureComment;
    }

    public void setClosureComment(String closedComment) {
        this.closureComment = closedComment;
    }

    public List<TicketComment> getComments() {
        return comments;
    }

    public void setComments(List<TicketComment> comments) {
        this.comments = comments;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
}
