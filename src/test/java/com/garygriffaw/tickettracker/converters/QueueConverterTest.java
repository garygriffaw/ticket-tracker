package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.QueueCreateDto;
import com.garygriffaw.tickettracker.dto.QueueSelectDto;
import com.garygriffaw.tickettracker.dto.QueueTableDto;
import com.garygriffaw.tickettracker.entities.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueueConverterTest {

    QueueConverter converter;

    @BeforeEach
    void setUp() {
        converter = new QueueConverter();
    }

    @Test
    void queueCreateDtoToEntity() {
        //given
        QueueCreateDto dto = new QueueCreateDto();
        dto.setQueueName("abc");

        //when
        final Queue actualEntity = converter.queueCreateDtoToEntity(dto);

        //then
        assertAll(
            () -> assertThat(actualEntity.getQueueName()).isEqualTo("abc"),
            () -> assertThat(actualEntity.getUserAccounts()).isNull()
        );
    }

    @Test
    void entityToQueueTableDto() {
        //given
        final Queue queue = createQueue(1L, "abc");

        //when
        final QueueTableDto actualDto = converter.entityToQueueTableDto(queue);

        //then
        assertThat(actualDto.getQueueName()).isEqualTo("abc");
    }

    @Test
    void entityListToQueueTableDtoList() {
        //given
        List<Queue> queues = new ArrayList<>();
        queues.add(createQueue(1L, "abc"));
        queues.add(createQueue(2L, "def"));

        //when
        final List<QueueTableDto> actualDtoList = converter.entityListToQueueTableDtoList(queues);

        //then
        assertAll(
            () -> assertThat(actualDtoList.size()).isEqualTo(2),
            () -> assertThat(actualDtoList)
                    .extracting("queueName")
                    .containsExactlyInAnyOrder("abc", "def")
        );
    }

    @Test
    void entityToQueueSelectDto() {
        //given
        final Queue queue = createQueue(1L, "abc");

        //when
        final QueueSelectDto actualDto = converter.entityToQueueSelectDto(queue);

        //then
        assertAll(
            () -> assertThat(actualDto.getQueueId()).isEqualTo(1L),
            () -> assertThat(actualDto.getQueueName()).isEqualTo("abc")
        );
    }

    @Test
    void entityListToQueueSelectDtoList() {
        //given
        List<Queue> queues = new ArrayList<>();
        queues.add(createQueue(1L, "abc"));
        queues.add(createQueue(2L, "def"));

        //when
        final List<QueueSelectDto> actualDtoList = converter.entityListToQueueSelectDtoList(queues);

        //then
        assertAll(
            () -> assertThat(actualDtoList.size()).isEqualTo(2),
            () -> assertThat(actualDtoList)
                    .extracting("queueId")
                    .containsExactlyInAnyOrder(1L, 2L),
            () -> assertThat(actualDtoList)
                    .extracting("queueName")
                    .containsExactlyInAnyOrder("abc", "def")
        );
    }

    //Helpers
    private Queue createQueue(Long id, String queueName) {
        Queue queue = new Queue();

        queue.setQueueId(id);
        queue.setQueueName(queueName);

        return queue;
    }
}