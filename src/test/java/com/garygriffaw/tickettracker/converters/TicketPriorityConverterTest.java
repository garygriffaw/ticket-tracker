package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.TicketPrioritySelectDto;
import com.garygriffaw.tickettracker.enums.TicketPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TicketPriorityConverterTest {

    TicketPriorityConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TicketPriorityConverter();
    }

    @Test
    void entityToTicketPrioritySelectDto() {
        //given

        //when
        TicketPrioritySelectDto actualDto = converter.entityToTicketPrioritySelectDto(TicketPriority.MEDIUM);

        //then
        assertAll(
            () -> assertThat(actualDto.getPriorityValue()).isEqualTo("MEDIUM"),
            () -> assertThat(actualDto.getDisplayValue()).isEqualTo("Medium"),
            () -> assertThat(actualDto.getSortOrder()).isEqualTo(1)
        );
    }

    @Test
    void entityListToTicketPrioritySelectListDto() {
        //given

        //when
        List<TicketPrioritySelectDto> actualDtoList =
                converter.entityListToTicketPrioritySelectListDto(Arrays.asList(TicketPriority.values()));

        //then
        assertAll(
            () -> assertThat(actualDtoList)
                .extracting("priorityValue")
                .containsExactlyInAnyOrder("LOW", "MEDIUM", "HIGH", "URGENT"),
            () -> assertThat(actualDtoList)
                .extracting("displayValue")
                .containsExactlyInAnyOrder("Low", "Medium", "High", "Urgent"),
            () -> assertThat(actualDtoList)
                .extracting("sortOrder")
                .containsExactlyInAnyOrder(0, 1, 2, 3)
        );
    }
}