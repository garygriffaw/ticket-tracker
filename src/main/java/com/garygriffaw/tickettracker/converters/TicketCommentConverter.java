package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.TicketCommentDto;
import com.garygriffaw.tickettracker.entities.TicketComment;
import com.garygriffaw.tickettracker.entities.UserAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketCommentConverter {

    // ---------------------------------------------------------------------------------------------------------------
    // TicketCommentDto - Used when creating a new ticket comment and to display existing comments
    // ---------------------------------------------------------------------------------------------------------------
    public TicketComment ticketCommentDtoToEntity(TicketCommentDto dto) {
        TicketComment comment = new TicketComment();

        comment.setCommentText(dto.getCommentText());

        return comment;
    }

    public TicketCommentDto entityToTicketCommentDto(TicketComment comment) {
        TicketCommentDto dto = new TicketCommentDto();

        dto.setId(comment.getId());
        dto.setCreatedDateTime(comment.getCreatedDateTime());
        dto.setCreatedByUserName(getUserName(comment.getCreatedBy()));
        dto.setCreatedByUserDisplayValue(getUserDisplayValue(comment.getCreatedBy()));
        dto.setCommentText(comment.getCommentText());
        dto.setTicketId(comment.getTicket().getTicketId());

        return dto;
    }

    public List<TicketCommentDto> entityListToTicketCommentListDto(List<TicketComment> comments) {
        return comments.stream().map(x -> entityToTicketCommentDto(x)).collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------------------------------------------------
    // General methods
    // ---------------------------------------------------------------------------------------------------------------
    private String getUserName(UserAccount userAccount) {
        if(userAccount != null)
            return userAccount.getUserName();
        else
            return "";
    }

    private String getUserDisplayValue(UserAccount userAccount) {
        if(userAccount != null)
            return userAccount.getUserDisplayValue();
        else
            return "";
    }
}
