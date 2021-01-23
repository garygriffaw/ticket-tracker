package com.garygriffaw.tickettracker.controllers;

import com.garygriffaw.tickettracker.converters.QueueConverter;
import com.garygriffaw.tickettracker.dto.QueueCreateDto;
import com.garygriffaw.tickettracker.dto.QueueSelectDto;
import com.garygriffaw.tickettracker.dto.QueueTableDto;
import com.garygriffaw.tickettracker.entities.Queue;
import com.garygriffaw.tickettracker.services.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/queues")
public class QueueController {

    @Autowired
    QueueService queueService;

    @Autowired
    QueueConverter queueConverter;

    // ---------------------------------------------------------------------------------------------------------------
    // Display the list of queues
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping
    public String processQueuesUrl(Model model) {
        return displayQueueList(model);
    }

    public String displayQueueList(Model model) {
        List<Queue> queues = queueService.getAll();
        Collections.sort(queues);
        List<QueueTableDto> queueDtos = queueConverter.entityListToQueueTableDtoList(queues);
        model.addAttribute("queues", queueDtos);

        return "queues/list";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Create a queue
    // ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/new")
    public String processNewQueueUrl(Model model) {
        QueueCreateDto queueCreateDto = new QueueCreateDto();

        model.addAttribute("queueCreateDto", queueCreateDto);

        return "queues/new-queue";
    }

    @PostMapping(value="/save", params={"save=Save"})
    public String saveQueue(Model model, @Valid QueueCreateDto queueCreateDto, Errors errors) {
        if(errors.hasErrors())
            return "queues/new-queue";

        Queue queue = queueConverter.queueCreateDtoToEntity(queueCreateDto);
        queueService.save(queue);

        return "redirect:/queues";
    }

    @PostMapping(value="/save", params={"cancel=Cancel"})
    public String cancelSaveQueue(Model model) {
        return displayQueueList(model);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Return a list of the queues
    // ---------------------------------------------------------------------------------------------------------------
    public List<QueueSelectDto> getQueueSelectListDto() {
        List<Queue> queues = queueService.getAll();
        Collections.sort(queues);
        return queueConverter.entityListToQueueSelectDtoList(queues);
    }
}
