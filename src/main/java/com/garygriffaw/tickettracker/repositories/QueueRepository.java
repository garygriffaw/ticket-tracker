package com.garygriffaw.tickettracker.repositories;

import com.garygriffaw.tickettracker.entities.Queue;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QueueRepository extends PagingAndSortingRepository<Queue, Long> {
}
