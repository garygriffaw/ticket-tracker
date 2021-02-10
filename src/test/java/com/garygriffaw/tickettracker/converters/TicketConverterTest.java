package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.*;
import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.entities.Ticket;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.enums.TicketPriority;
import com.garygriffaw.tickettracker.enums.TicketStatus;
import com.garygriffaw.tickettracker.services.QueueService;
import com.garygriffaw.tickettracker.services.TicketService;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TicketConverterTest {

    @Mock
    QueueService queueService;

    @Mock
    TicketService ticketService;

    @Mock
    UserAccountService userAccountService;

    @Mock
    TicketCommentConverter ticketCommentConverter;

    @InjectMocks
    TicketConverter converter;

    @Test
    void ticketCreateDtoToEntity() {
        //given
        TicketCreateDto dto = new TicketCreateDto();
        dto.setTitle("title1");
        dto.setDescription("desc1");
        dto.setQueueId(1L);
        dto.setPriority("MEDIUM");

        final Queue queue = createQueue(1L, "queue1");
        given(queueService.findById(anyLong())).willReturn(queue);

        //when
        final Ticket actualEntity = converter.ticketCreateDtoToEntity(dto);

        //then
        assertAll(
                () -> assertThat(actualEntity.getTitle()).isEqualTo("title1"),
                () -> assertThat(actualEntity.getDescription()).isEqualTo("desc1"),
                () -> assertThat(actualEntity.getQueue()).isEqualTo(queue),
                () -> assertThat(actualEntity.getPriority()).isEqualTo(TicketPriority.MEDIUM)
        );
    }

    @Test
    void entityToTicketTableDto() {
        //given
        final LocalDateTime timeValue1 = LocalDateTime.now().minusMinutes(10);
        final LocalDateTime timeValue2 = LocalDateTime.now().minusMinutes(11);

        final Queue queue1 = createQueue(1L, "abc");

        final UserAccount createdByUser = createUserAccount("uname1", "fname1", "lname1");
        final UserAccount ownedByUser = createUserAccount("uname2", "fname2", "lname2");
        final UserAccount assignedToUser = createUserAccount("uname3", "fname3", "lname3");
        final UserAccount closedByUser = createUserAccount("uname4", "fname4", "lname4");

        final Ticket ticket = createTicket(1L, "title1", "desc1", TicketStatus.INWORK,
                TicketPriority.MEDIUM, timeValue1, createdByUser,
                ownedByUser, assignedToUser, timeValue2,
                closedByUser, "closure1", queue1);

        //when
        final TicketTableDto actualDto = converter.entityToTicketTableDto(ticket);

        //then
        assertAll(
                () -> assertThat(actualDto.getTicketId()).isEqualTo(1L),
                () -> assertThat(actualDto.getTitle()).isEqualTo("title1"),
                () -> assertThat(actualDto.getCreatedDateTime()).isEqualTo(timeValue1),
                () -> assertThat(actualDto.getTicketStatusDisplayValue()).isEqualTo("In Work"),
                () -> assertThat(actualDto.getPriorityDisplayValue()).isEqualTo("Medium")
        );
    }

    @Test
    void entityListToTicketTableDtoList() {
        //given
        final LocalDateTime timeValue1 = LocalDateTime.now().minusMinutes(10);
        final LocalDateTime timeValue2 = LocalDateTime.now().minusMinutes(11);
        final LocalDateTime timeValue3 = LocalDateTime.now().minusMinutes(12);
        final LocalDateTime timeValue4 = LocalDateTime.now().minusMinutes(13);

        final Queue queue1 = createQueue(1L, "abc");

        final UserAccount createdByUser = createUserAccount("uname1", "fname1", "lname1");
        final UserAccount ownedByUser = createUserAccount("uname2", "fname2", "lname2");
        final UserAccount assignedToUser = createUserAccount("uname3", "fname3", "lname3");
        final UserAccount closedByUser = createUserAccount("uname4", "fname4", "lname4");

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(createTicket(1L, "title1", "desc1", TicketStatus.INWORK,
                TicketPriority.MEDIUM, timeValue1, createdByUser,
                ownedByUser, assignedToUser, timeValue2,
                closedByUser, "closure1", queue1));
        tickets.add(createTicket(2L, "title2", "desc2", TicketStatus.UNASSIGNED,
                TicketPriority.HIGH, timeValue3, createdByUser,
                ownedByUser, assignedToUser, timeValue4,
                closedByUser, "closure2", queue1));

        //when
        final List<TicketTableDto> actualDtoList = converter.entityListToTicketTableDtoList(tickets);

        //then
        assertAll(
            () -> assertThat(actualDtoList.size()).isEqualTo(2),
            () -> assertThat(actualDtoList)
                .extracting("ticketId")
                .containsExactlyInAnyOrder(1L, 2L),
            () -> assertThat(actualDtoList)
                .extracting("title")
                .containsExactlyInAnyOrder("title1", "title2"),
            () -> assertThat(actualDtoList)
                .extracting("createdDateTime")
                .containsExactlyInAnyOrder(timeValue1, timeValue3),
            () -> assertThat(actualDtoList)
                .extracting("ticketStatusDisplayValue")
                .containsExactlyInAnyOrder("In Work", "Unassigned"),
            () -> assertThat(actualDtoList)
                .extracting("priorityDisplayValue")
                .containsExactlyInAnyOrder("Medium", "High")
        );
    }

    @Test
    void entityToTicketUpdateDto() {
        //given
        final LocalDateTime timeValue1 = LocalDateTime.now().minusMinutes(10);
        final LocalDateTime timeValue2 = LocalDateTime.now().minusMinutes(9);

        final Queue queue1 = createQueue(1L, "abc");

        final UserAccount createdByUser = createUserAccount("uname1", "fname1", "lname1");
        final UserAccount ownedByUser = createUserAccount("uname2", "fname2", "lname2");
        final UserAccount assignedToUser = createUserAccount("uname3", "fname3", "lname3");
        final UserAccount closedByUser = createUserAccount("uname4", "fname4", "lname4");

        final Ticket ticket = createTicket(1L, "title1", "desc1", TicketStatus.INWORK,
                TicketPriority.MEDIUM, timeValue1, createdByUser,
                ownedByUser, assignedToUser, timeValue2,
                closedByUser, "closure1", queue1);

        List<TicketCommentDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(createTicketCommentDto(1L, timeValue1, "uname1",
                "lname1, fname1 (uname1)", "abc", 1L));
        commentDtoList.add(createTicketCommentDto(2L, timeValue2, "uname2",
                "lname2, fname2 (uname2)", "def", 1L));
        given(ticketCommentConverter.entityListToTicketCommentListDto(any())).willReturn(commentDtoList);

        //when
        final TicketUpdateDto actualDto = converter.entityToTicketUpdateDto(ticket);

        //then
        assertAll(
                () -> assertThat(actualDto.getTicketId()).isEqualTo(1L),
                () -> assertThat(actualDto.getTitle()).isEqualTo("title1"),
                () -> assertThat(actualDto.getDescription()).isEqualTo("desc1"),
                () -> assertThat(actualDto.getCreatedDateTime()).isEqualTo(timeValue1),
                () -> assertThat(actualDto.getCreatedByUserName()).isEqualTo("uname1"),
                () -> assertThat(actualDto.getCreatedByUserDisplayValue()).isEqualTo("lname1, fname1 (uname1)"),
                () -> assertThat(actualDto.getOwnedByUserName()).isEqualTo("uname2"),
                () -> assertThat(actualDto.getAssignedToUserName()).isEqualTo("uname3"),
                () -> assertThat(actualDto.getTicketStatus()).isEqualTo("INWORK"),
                () -> assertThat(actualDto.getQueueId()).isEqualTo(1l),
                () -> assertThat(actualDto.getPriority()).isEqualTo("MEDIUM"),
                () -> assertThat(actualDto.getClosureComment()).isEqualTo("closure1"),
                () -> assertThat(actualDto.getComments()).isEqualTo(commentDtoList)
        );
    }

    @Test
    void entityToTicketUpdateDtoNulls() {
        //given
        final LocalDateTime timeValue1 = LocalDateTime.now().minusMinutes(10);

        final UserAccount createdByUser = createUserAccount("uname1", "fname1", "lname1");

        final Ticket ticket = createTicket(1L, "title1", "desc1", TicketStatus.INWORK,
                TicketPriority.MEDIUM, timeValue1, createdByUser,
                null, null, null,
                null, null, null);

        given(ticketCommentConverter.entityListToTicketCommentListDto(any())).willReturn(null);

        //when
        final TicketUpdateDto actualDto = converter.entityToTicketUpdateDto(ticket);

        //then
        assertAll(
                () -> assertThat(actualDto.getTicketId()).isEqualTo(1L),
                () -> assertThat(actualDto.getTitle()).isEqualTo("title1"),
                () -> assertThat(actualDto.getDescription()).isEqualTo("desc1"),
                () -> assertThat(actualDto.getCreatedDateTime()).isEqualTo(timeValue1),
                () -> assertThat(actualDto.getCreatedByUserName()).isEqualTo("uname1"),
                () -> assertThat(actualDto.getCreatedByUserDisplayValue()).isEqualTo("lname1, fname1 (uname1)"),
                () -> assertThat(actualDto.getOwnedByUserName()).isEqualTo(""),
                () -> assertThat(actualDto.getAssignedToUserName()).isEqualTo(""),
                () -> assertThat(actualDto.getTicketStatus()).isEqualTo("INWORK"),
                () -> assertThat(actualDto.getQueueId()).isNull(),
                () -> assertThat(actualDto.getPriority()).isEqualTo("MEDIUM"),
                () -> assertThat(actualDto.getClosureComment()).isNull(),
                () -> assertThat(actualDto.getComments()).isNull()
        );
    }

    @Test
    void ticketUpdateDtoToEntity() {
        //given
        final LocalDateTime timeValue1 = LocalDateTime.now().minusMinutes(10);
        final LocalDateTime timeValue2 = LocalDateTime.now().minusMinutes(9);

        final Queue queue1 = createQueue(1L, "abc");
        final Queue queue2 = createQueue(2L, "def");

        final UserAccount createdByUser = createUserAccount("uname1", "fname1", "lname1");
        final UserAccount ownedByUser = createUserAccount("uname2", "fname2", "lname2");
        final UserAccount assignedToUser = createUserAccount("uname3", "fname3", "lname3");
        final UserAccount closedByUser = createUserAccount("uname4", "fname4", "lname4");
        final UserAccount updatedOwnedByUser = createUserAccount("uname5", "fname5", "lname5");
        final UserAccount updatedAssignedToUser = createUserAccount("uname6", "fname6", "lname6");

        final Ticket ticket = createTicket(1L, "title1", "desc1", TicketStatus.INWORK,
                TicketPriority.MEDIUM, timeValue1, createdByUser,
                ownedByUser, assignedToUser, timeValue2,
                closedByUser, "closure1", queue1);

        TicketUpdateDto dto = new TicketUpdateDto();
        dto.setOwnedByUserName("uname5");
        dto.setAssignedToUserName("uname6");
        dto.setTicketStatus("ASSIGNED");
        dto.setQueueId(2L);
        dto.setPriority("LOW");
        dto.setClosureComment("closure2");

        given(ticketService.findById(anyLong())).willReturn(ticket);
        given(userAccountService.findById("uname5")).willReturn(updatedOwnedByUser);
        given(userAccountService.findById("uname6")).willReturn(updatedAssignedToUser);
        given(queueService.findById(anyLong())).willReturn(queue2);

        //when
        final Ticket actualEntity = converter.ticketUpdateDtoToEntity(dto);

        //then
        assertAll(
                () -> assertThat(actualEntity.getTicketId()).isEqualTo(1L),
                () -> assertThat(actualEntity.getTitle()).isEqualTo("title1"),
                () -> assertThat(actualEntity.getOwnedBy()).isEqualTo(updatedOwnedByUser),
                () -> assertThat(actualEntity.getAssignedTo()).isEqualTo(updatedAssignedToUser),
                () -> assertThat(actualEntity.getTicketStatus()).isEqualTo(TicketStatus.ASSIGNED),
                () -> assertThat(actualEntity.getQueue()).isEqualTo(queue2),
                () -> assertThat(actualEntity.getPriority()).isEqualTo(TicketPriority.LOW),
                () -> assertThat(actualEntity.getClosureComment()).isEqualTo("closure2")
        );
    }

    @Test
    void ticketUpdateDtoToEntityBlanks() {
        //given
        final LocalDateTime timeValue1 = LocalDateTime.now().minusMinutes(10);
        final LocalDateTime timeValue2 = LocalDateTime.now().minusMinutes(9);

        final Queue queue1 = createQueue(1L, "abc");

        final UserAccount createdByUser = createUserAccount("uname1", "fname1", "lname1");
        final UserAccount ownedByUser = createUserAccount("uname2", "fname2", "lname2");
        final UserAccount assignedToUser = createUserAccount("uname3", "fname3", "lname3");
        final UserAccount closedByUser = createUserAccount("uname4", "fname4", "lname4");
        final UserAccount updatedOwnedByUser = createUserAccount("uname5", "fname5", "lname5");

        final Ticket ticket = createTicket(1L, "title1", "desc1", TicketStatus.INWORK,
                TicketPriority.MEDIUM, timeValue1, createdByUser,
                ownedByUser, assignedToUser, timeValue2,
                closedByUser, "closure1", queue1);

        TicketUpdateDto dto = new TicketUpdateDto();
        dto.setOwnedByUserName("uname5");
        dto.setAssignedToUserName("");
        dto.setTicketStatus("ASSIGNED");
        dto.setQueueId(null);
        dto.setPriority("LOW");
        dto.setClosureComment("");

        given(ticketService.findById(anyLong())).willReturn(ticket);
        given(userAccountService.findById("uname5")).willReturn(updatedOwnedByUser);

        //when
        final Ticket actualEntity = converter.ticketUpdateDtoToEntity(dto);

        //then
        assertAll(
                () -> assertThat(actualEntity.getTicketId()).isEqualTo(1L),
                () -> assertThat(actualEntity.getTitle()).isEqualTo("title1"),
                () -> assertThat(actualEntity.getOwnedBy()).isEqualTo(updatedOwnedByUser),
                () -> assertThat(actualEntity.getAssignedTo()).isNull(),
                () -> assertThat(actualEntity.getTicketStatus()).isEqualTo(TicketStatus.ASSIGNED),
                () -> assertThat(actualEntity.getQueue()).isNull(),
                () -> assertThat(actualEntity.getPriority()).isEqualTo(TicketPriority.LOW),
                () -> assertThat(actualEntity.getClosureComment()).isEqualTo("")
        );
    }

    @Test
    void entityToTicketViewDto() {
        //given
        final LocalDateTime timeValue1 = LocalDateTime.now().minusMinutes(10);
        final LocalDateTime timeValue2 = LocalDateTime.now().minusMinutes(9);

        final Queue queue1 = createQueue(1L, "abc");

        final UserAccount createdByUser = createUserAccount("uname1", "fname1", "lname1");
        final UserAccount ownedByUser = createUserAccount("uname2", "fname2", "lname2");
        final UserAccount assignedToUser = createUserAccount("uname3", "fname3", "lname3");
        final UserAccount closedByUser = createUserAccount("uname4", "fname4", "lname4");

        final Ticket ticket = createTicket(1L, "title1", "desc1", TicketStatus.INWORK,
                TicketPriority.MEDIUM, timeValue1, createdByUser,
                ownedByUser, assignedToUser, timeValue2,
                closedByUser, "closure1", queue1);

        //when
        final TicketViewDto actualDto = converter.entityToTicketViewDto(ticket);

        //then
        assertAll(
                () -> assertThat(actualDto.getTicketId()).isEqualTo(1L),
                () -> assertThat(actualDto.getTitle()).isEqualTo("title1"),
                () -> assertThat(actualDto.getDescription()).isEqualTo("desc1"),
                () -> assertThat(actualDto.getCreatedDateTime()).isEqualTo(timeValue1),
                () -> assertThat(actualDto.getCreatedByUserDisplayValue()).isEqualTo("lname1, fname1 (uname1)"),
                () -> assertThat(actualDto.getOwnedByUserDisplayValue()).isEqualTo("lname2, fname2 (uname2)"),
                () -> assertThat(actualDto.getAssignedToUserDisplayValue()).isEqualTo("lname3, fname3 (uname3)"),
                () -> assertThat(actualDto.getTicketStatusDisplayValue()).isEqualTo("In Work"),
                () -> assertThat(actualDto.getQueueDisplayValue()).isEqualTo("abc"),
                () -> assertThat(actualDto.getPriorityDisplayValue()).isEqualTo("Medium"),
                () -> assertThat(actualDto.getClosedDateTime()).isEqualTo(timeValue2),
                () -> assertThat(actualDto.getClosedByUserDisplayValue()).isEqualTo("lname4, fname4 (uname4)"),
                () -> assertThat(actualDto.getClosureComment()).isEqualTo("closure1")
        );
    }

    @Test
    void entityToTicketViewDtoNulls() {
        //given
        final Ticket ticket = createTicket(1L, "title1", "desc1", TicketStatus.INWORK,
                TicketPriority.MEDIUM, null, null,
                null, null  , null,
                null, null, null);

        //when
        final TicketViewDto actualDto = converter.entityToTicketViewDto(ticket);

        //then
        assertAll(
                () -> assertThat(actualDto.getTicketId()).isEqualTo(1L),
                () -> assertThat(actualDto.getTitle()).isEqualTo("title1"),
                () -> assertThat(actualDto.getDescription()).isEqualTo("desc1"),
                () -> assertThat(actualDto.getCreatedDateTime()).isNull(),
                () -> assertThat(actualDto.getCreatedByUserDisplayValue()).isEqualTo(""),
                () -> assertThat(actualDto.getOwnedByUserDisplayValue()).isEqualTo(""),
                () -> assertThat(actualDto.getAssignedToUserDisplayValue()).isEqualTo(""),
                () -> assertThat(actualDto.getTicketStatusDisplayValue()).isEqualTo("In Work"),
                () -> assertThat(actualDto.getQueueDisplayValue()).isEqualTo(""),
                () -> assertThat(actualDto.getPriorityDisplayValue()).isEqualTo("Medium"),
                () -> assertThat(actualDto.getClosedDateTime()).isNull(),
                () -> assertThat(actualDto.getClosedByUserDisplayValue()).isEqualTo(""),
                () -> assertThat(actualDto.getClosureComment()).isNull()
        );
    }

    //Helpers
    private Queue createQueue(Long id, String queueName) {
        Queue queue = new Queue();

        queue.setQueueId(id);
        queue.setQueueName(queueName);

        return queue;
    }

    private Ticket createTicket(Long id, String title, String description, TicketStatus ticketStatus,
                                TicketPriority priority, LocalDateTime createdDateTime, UserAccount createdBy,
                                UserAccount ownedBy, UserAccount assignedTo, LocalDateTime closedDateTime,
                                UserAccount closedBy, String closureComment, Queue queue) {
        Ticket ticket = new Ticket();

        ticket.setTicketId(id);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setTicketStatus(ticketStatus);
        ticket.setPriority(priority);
        ticket.setCreatedDateTime(createdDateTime);
        ticket.setCreatedBy(createdBy);
        ticket.setOwnedBy(ownedBy);
        ticket.setAssignedTo(assignedTo);
        ticket.setClosedDateTime(closedDateTime);
        ticket.setClosedBy(closedBy);
        ticket.setClosureComment(closureComment);
        ticket.setQueue(queue);

        return ticket;
    }

    private TicketCommentDto createTicketCommentDto(Long id, LocalDateTime createdDateTime, String createdByUserName,
                                                    String createdByUserDisplayValue, String commentText, Long ticketId) {
        TicketCommentDto ticketCommentDto = new TicketCommentDto();

        ticketCommentDto.setId(id);
        ticketCommentDto.setCreatedDateTime(createdDateTime);
        ticketCommentDto.setCreatedByUserName(createdByUserName);
        ticketCommentDto.setCreatedByUserDisplayValue(createdByUserDisplayValue);
        ticketCommentDto.setCommentText(commentText);
        ticketCommentDto.setTicketId(ticketId);

        return ticketCommentDto;
    }

    private UserAccount createUserAccount(String userName, String firstName, String lastName) {
        UserAccount user = new UserAccount();

        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }
}