package com.garygriffaw.tickettracker.services;

import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService {

    @Autowired
    UserAccountRepository userAccountRepository;

    public UserAccount save(UserAccount userAccount) { return userAccountRepository.save(userAccount); }

    public List<UserAccount> getAll() {
        return (List<UserAccount>) userAccountRepository.findAll();
    }

    public UserAccount findById(String userName) { return userAccountRepository.findById(userName).get(); }

    public List<Queue> getUnassignedQueues(List<Long> ids) {
        return (List<Queue>) userAccountRepository.findUnassignedQueues(ids);
    }

    public int getCountUserAccountQueue(String userName, Long queueId) {
        return (int) userAccountRepository.countUserAccountQueue(userName, queueId);
    }
}
