package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.QueueCreateDto;
import com.garygriffaw.tickettracker.dto.QueueSelectDto;
import com.garygriffaw.tickettracker.dto.QueueTableDto;
import com.garygriffaw.tickettracker.entities.Queue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueueConverter {

    // ---------------------------------------------------------------------------------------------------------------
    // QueueCreateDto - Used when creating a new queue
    // ---------------------------------------------------------------------------------------------------------------
    public Queue queueCreateDtoToEntity(QueueCreateDto dto) {
        Queue queue = new Queue();

        queue.setQueueName(dto.getQueueName());

        return queue;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // QueueTableDto - Used to display a list of queues
    // ---------------------------------------------------------------------------------------------------------------
    public QueueTableDto entityToQueueTableDto(Queue queue) {
        QueueTableDto dto = new QueueTableDto();

        dto.setQueueName(queue.getQueueName());

        return dto;
    }

    public List<QueueTableDto> entityListToQueueTableDtoList(List<Queue> queues) {
        return queues.stream().map(x -> entityToQueueTableDto(x)).collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------------------------------------------------
    // QueueSelectDto - Used to display the dropdown list of queues to select from
    // ---------------------------------------------------------------------------------------------------------------
    public QueueSelectDto entityToQueueSelectDto(Queue queue) {
        QueueSelectDto dto = new QueueSelectDto();

        dto.setQueueId(queue.getQueueId());
        dto.setQueueName(queue.getQueueName());

        return dto;
    }

    public List<QueueSelectDto> entityListToQueueSelectDtoList(List<Queue> queues) {
        return queues.stream().map(x -> entityToQueueSelectDto(x)).collect(Collectors.toList());
    }
}
