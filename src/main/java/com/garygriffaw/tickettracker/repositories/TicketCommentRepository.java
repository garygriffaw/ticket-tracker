package com.garygriffaw.tickettracker.repositories;

import com.garygriffaw.tickettracker.entities.TicketComment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketCommentRepository extends PagingAndSortingRepository<TicketComment, Long> {
}
