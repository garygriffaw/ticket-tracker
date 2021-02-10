package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.TicketStatusSelectDto;
import com.garygriffaw.tickettracker.enums.TicketStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TicketStatusConverterTest {

    TicketStatusConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TicketStatusConverter();
    }

    @Test
    void entityToTicketStatusSelectDto() {
        //given

        //when
        final TicketStatusSelectDto actualDto = converter.entityToTicketStatusSelectDto(TicketStatus.INWORK);

        //then
        assertAll(
                () -> assertThat(actualDto.getStatusValue()).isEqualTo("INWORK"),
                () -> assertThat(actualDto.getDisplayValue()).isEqualTo("In Work")
        );
    }

    @Test
    void entityListToTicketStatusSelectListDto() {
        //given

        //when
        final List<TicketStatusSelectDto> actualDtoList =
                converter.entityListToTicketStatusSelectListDto(Arrays.asList(TicketStatus.values()));

        //then
        assertAll(
                () -> assertThat(actualDtoList)
                        .extracting("statusValue")
                        .containsExactlyInAnyOrder("UNASSIGNED", "ASSIGNED", "INWORK", "COMPLETE", "CANCELLED"),
                () -> assertThat(actualDtoList)
                        .extracting("displayValue")
                        .containsExactlyInAnyOrder("Unassigned", "Assigned", "In Work", "Complete", "Cancelled")
        );
    }
}