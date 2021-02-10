package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.TicketCommentDto;
import com.garygriffaw.tickettracker.entities.Ticket;
import com.garygriffaw.tickettracker.entities.TicketComment;
import com.garygriffaw.tickettracker.entities.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TicketCommentConverterTest {

    TicketCommentConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TicketCommentConverter();
    }

    @Test
    void ticketCommentDtoToEntity() {
        //given
        TicketCommentDto dto = new TicketCommentDto();
        dto.setCreatedDateTime(LocalDateTime.now());
        dto.setCreatedByUserName("uname1");
        dto.setCreatedByUserDisplayValue("lname1, fname1 (uname1)");
        dto.setCommentText("abc");
        dto.setTicketId(1L);

        //when
        final TicketComment actualEntity = converter.ticketCommentDtoToEntity(dto);

        //then
        assertAll(
            () -> assertThat(actualEntity.getCreatedDateTime()).isNull(),
            () -> assertThat(actualEntity.getCreatedBy()).isNull(),
            () -> assertThat(actualEntity.getCommentText()).isEqualTo("abc"),
            () -> assertThat(actualEntity.getTicket()).isNull()
        );
    }

    @Test
    void entityToTicketCommentDto() {
        //given
        final LocalDateTime timeValue = LocalDateTime.now();

        final UserAccount user1 = createUserAccount("uname", "fname", "lname");

        final Ticket ticket1 = createTicket(100L);

        final TicketComment ticketComment = createTicketComment(1L, timeValue, user1, "abc", ticket1);

        //when
        final TicketCommentDto actualDto = converter.entityToTicketCommentDto(ticketComment);

        //then
        assertAll(
                () -> assertThat(actualDto.getId()).isEqualTo(1L),
                () -> assertThat(actualDto.getCreatedDateTime()).isEqualTo(timeValue),
                () -> assertThat(actualDto.getCreatedByUserName()).isEqualTo("uname"),
                () -> assertThat(actualDto.getCreatedByUserDisplayValue()).isEqualTo("lname, fname (uname)"),
                () -> assertThat(actualDto.getCommentText()).isEqualTo("abc"),
                () -> assertThat(actualDto.getTicketId()).isEqualTo(100L)
        );
    }

    @Test
    void entityToTicketCommentDtoNullUser() {
        //given
        final LocalDateTime timeValue = LocalDateTime.now();

        final Ticket ticket = createTicket(100L);

        final TicketComment ticketComment = createTicketComment(1L, timeValue, null, "abc", ticket);

        //when
        final TicketCommentDto actualDto = converter.entityToTicketCommentDto(ticketComment);

        //then
        assertAll(
                () -> assertThat(actualDto.getId()).isEqualTo(1L),
                () -> assertThat(actualDto.getCreatedDateTime()).isEqualTo(timeValue),
                () -> assertThat(actualDto.getCreatedByUserName()).isEqualTo(""),
                () -> assertThat(actualDto.getCreatedByUserDisplayValue()).isEqualTo(""),
                () -> assertThat(actualDto.getCommentText()).isEqualTo("abc"),
                () -> assertThat(actualDto.getTicketId()).isEqualTo(100L)
        );
    }

    @Test
    void entityListToTicketCommentListDto() {
        //given
        final LocalDateTime timeValue1 = LocalDateTime.now().minusMinutes(10);
        final LocalDateTime timeValue2 = LocalDateTime.now();

        final UserAccount user1 = createUserAccount("uname1", "fname1", "lname1");
        final UserAccount user2 = createUserAccount("uname2", "fname2", "lname2");

        final Ticket ticket1 = createTicket(100L);

        List<TicketComment> comments = new ArrayList<>();
        comments.add(createTicketComment(1L, timeValue1, user1, "abc", ticket1));
        comments.add(createTicketComment(2L, timeValue2, user2, "def", ticket1));

        //when
        final List<TicketCommentDto> actualDtoList = converter.entityListToTicketCommentListDto(comments);

        //then
        assertAll(
                () -> assertThat(actualDtoList.size()).isEqualTo(2),
                () -> assertThat(actualDtoList)
                        .extracting("id")
                        .containsExactlyInAnyOrder(1L, 2L),
                () -> assertThat(actualDtoList)
                        .extracting("createdDateTime")
                        .containsExactlyInAnyOrder(timeValue1, timeValue2),
                () -> assertThat(actualDtoList)
                        .extracting("createdByUserName")
                        .containsExactlyInAnyOrder("uname1", "uname2"),
                () -> assertThat(actualDtoList)
                        .extracting("createdByUserDisplayValue")
                        .containsExactlyInAnyOrder("lname1, fname1 (uname1)", "lname2, fname2 (uname2)"),
                () -> assertThat(actualDtoList)
                        .extracting("commentText")
                        .containsExactlyInAnyOrder("abc", "def"),
                () -> assertThat(actualDtoList)
                        .extracting("ticketId")
                        .containsExactlyInAnyOrder(100L, 100L)
        );
    }

    //Helpers
    private Ticket createTicket(Long id) {
        Ticket ticket = new Ticket();

        ticket.setTicketId(id);

        return ticket;
    }

    private TicketComment createTicketComment(Long id, LocalDateTime createdDateTime, UserAccount createdBy,
                                              String commentText, Ticket ticket) {
        TicketComment ticketComment = new TicketComment();

        ticketComment.setId(id);
        ticketComment.setCreatedDateTime(createdDateTime);
        ticketComment.setCreatedBy(createdBy);
        ticketComment.setCommentText(commentText);
        ticketComment.setTicket(ticket);

        return ticketComment;
    }

    private UserAccount createUserAccount(String userName, String firstName, String lastName) {
        UserAccount user = new UserAccount();

        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }
}