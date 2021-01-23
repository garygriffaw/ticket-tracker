package com.garygriffaw.tickettracker.repositories;

import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.entities.Ticket;
import com.garygriffaw.tickettracker.entities.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {
    List<Ticket> findAllByTicketStatus(String ticketStatus);

    @Query("select t from Ticket t " +
            "where (t.createdBy = :myUserAccount or t.ownedBy = :myUserAccount) " +
            "and (t.assignedTo <> :myUserAccount or t.assignedTo is null) " +
            "and t.closedDateTime is null")
    List<Ticket> findMyOpenCreatedOrOwnedTickets(@Param("myUserAccount") UserAccount userAccount);

    @Query("select t from Ticket t " +
            "where t.assignedTo = :myUserAccount " +
            "and t.closedDateTime is null")
    List<Ticket> findMyOpenAssignedToTickets(@Param("myUserAccount") UserAccount userAccount);

    @Query("select t from Ticket t " +
            "where t.createdBy <> :myUserAccount " +
            "and t.ownedBy <> :myUserAccount " +
            "and t.assignedTo is null " +
            "and t.closedDateTime is null " +
            "and t.queue in :myQueues")
    List<Ticket> findOpenUnassignedTicketsNotMine(@Param("myUserAccount") UserAccount userAccount,
                                                  @Param("myQueues") Set<Queue> queues);

    @Query("select t from Ticket t " +
            "where (t.createdBy = :myUserAccount " +
            "or t.ownedBy = :myUserAccount " +
            "or t.assignedTo = :myUserAccount " +
            "or t.closedBy = :myUserAccount) " +
            "and t.closedDateTime is not null")
    List<Ticket> findMyClosedTickets(@Param("myUserAccount") UserAccount userAccount);
}
