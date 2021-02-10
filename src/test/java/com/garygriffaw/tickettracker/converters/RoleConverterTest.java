package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.RoleSelectDto;
import com.garygriffaw.tickettracker.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RoleConverterTest {

    RoleConverter converter;

    @BeforeEach
    void setUp() {
        converter = new RoleConverter();
    }

    @Test
    void entityToRoleSelectDto() {
        //given

        //when
        final RoleSelectDto actualDto = converter.entityToRoleSelectDto(Role.ROLE_ADMIN);

        //then
        assertAll(
            () -> assertThat(actualDto.getRoleValue()).isEqualTo("ROLE_ADMIN"),
            () -> assertThat(actualDto.getDisplayValue()).isEqualTo("Admin")
        );
    }

    @Test
    void entityListToRoleSelectListDto() {
        //given

        //when
        final List<RoleSelectDto> actualDtoList = converter.entityListToRoleSelectListDto(Arrays.asList(Role.values()));

        //then
        assertAll(
            () -> assertThat(actualDtoList)
                .extracting("roleValue")
                .containsExactlyInAnyOrder("ROLE_ADMIN", "ROLE_USER"),
            () -> assertThat(actualDtoList)
                .extracting("displayValue")
                .containsExactlyInAnyOrder("Admin", "User")
        );
    }
}