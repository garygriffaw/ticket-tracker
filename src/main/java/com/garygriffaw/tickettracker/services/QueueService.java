package com.garygriffaw.tickettracker.services;

import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.repositories.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueService {

    @Autowired
    QueueRepository queueRepository;

    public Queue save(Queue queue) { return queueRepository.save(queue); }

    public List<Queue> getAll() { return (List<Queue>) queueRepository.findAll(); }

    public Queue findById(Long id) { return queueRepository.findById(id).get(); }
}
