package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.RoleSelectDto;
import com.garygriffaw.tickettracker.enums.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConverter {

    // ---------------------------------------------------------------------------------------------------------------
    // RoleSelectDto - Used to display the dropdown list of roles to select from
    // ---------------------------------------------------------------------------------------------------------------
    public RoleSelectDto entityToRoleSelectDto(Role role) {
        RoleSelectDto dto = new RoleSelectDto();

        dto.setRoleValue(role.name());
        dto.setDisplayValue(role.getDisplayValue());

        return dto;
    }

    public List<RoleSelectDto> entityListToRoleSelectListDto(List<Role> roles) {
        return roles.stream().map(x -> entityToRoleSelectDto(x)).collect(Collectors.toList());
    }
}
