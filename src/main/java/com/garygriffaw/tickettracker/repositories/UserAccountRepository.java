package com.garygriffaw.tickettracker.repositories;

import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.entities.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, String> {

    @Query("select q from Queue q " +
            "where q.id not in :assignedQueueIds")
    List<Queue> findUnassignedQueues(@Param("assignedQueueIds") List<Long> ids);

    @Query("select count(u) from UserAccount u " +
            "inner join u.queues q " +
            "where u.userName = :user_name " +
            "and q.queueId = :queue_id")
    int countUserAccountQueue (@Param("user_name") String userName,
                               @Param("queue_id") Long queueId);
}
