package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.*;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.enums.Role;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAccountConverter {

    @Autowired
    UserAccountService userAccountService;

    // ---------------------------------------------------------------------------------------------------------------
    // RegisterDto - Used when a user is registering
    // ---------------------------------------------------------------------------------------------------------------
    public UserAccount registerDtoToEntity(RegisterDto dto) {
        UserAccount user = new UserAccount();

        user.setUserName(dto.getUserName());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // UserAccountSelectDto - Used to display the dropdown list of users to select from
    // ---------------------------------------------------------------------------------------------------------------
    public UserAccountSelectDto entityToUserAccountSelectDto(UserAccount userAccount) {
        UserAccountSelectDto dto = new UserAccountSelectDto();

        dto.setUserName(userAccount.getUserName());
        dto.setUserDisplayValue(userAccount.getUserDisplayValue());

        return dto;
    }

    public List<UserAccountSelectDto> entityListToUserAccountSelectDtoList(List<UserAccount> userAccounts) {
        return userAccounts.stream().map(x -> entityToUserAccountSelectDto(x)).collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------------------------------------------------
    // ProfileDto - Used when a user is updating their profile
    // ---------------------------------------------------------------------------------------------------------------
    public ProfileDto entityToProfileDto(UserAccount user) {
        ProfileDto dto = new ProfileDto();

        dto.setUserName(user.getUserName());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public UserAccount profileDtoToEntity(ProfileDto dto) {
        UserAccount user = userAccountService.findById(dto.getUserName());

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        return user;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // PasswordDto - Used when a user is updating their password
    // ---------------------------------------------------------------------------------------------------------------
    public PasswordDto entityToPasswordDto(UserAccount user) {
        PasswordDto dto = new PasswordDto();

        dto.setUserName(user.getUserName());

        return dto;
    }

    public UserAccount passwordDtoToEntity(PasswordDto dto) {
        UserAccount user = userAccountService.findById(dto.getUserName());

        user.setPassword(dto.getPassword());

        return user;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // UserAccountQueueDto - Used when adding a queue to a user
    // ---------------------------------------------------------------------------------------------------------------
    public UserAccountQueueDto entityToUserAccountQueueDto(UserAccount userAccount) {
        UserAccountQueueDto dto = new UserAccountQueueDto();

        dto.setUserName(userAccount.getUserName());

        return dto;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // UserAccountTableDto - Used to display a list of users
    // ---------------------------------------------------------------------------------------------------------------
    public UserAccountTableDto entityToUserAccountTableDto(UserAccount userAccount) {
        UserAccountTableDto dto = new UserAccountTableDto();

        dto.setUserName(userAccount.getUserName());
        dto.setFirstName(userAccount.getFirstName());
        dto.setLastName(userAccount.getLastName());
        dto.setEmail(userAccount.getEmail());
        dto.setRoleDisplayValue(userAccount.getRoleDisplayValue());
        dto.setEnabled(userAccount.isEnabled());

        return dto;
    }

    public List<UserAccountTableDto> entityListToUserAccountTableListDto(List<UserAccount> userAccounts) {
        return userAccounts.stream().map(x -> entityToUserAccountTableDto(x)).collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------------------------------------------------
    // UserAccountUpdateDto - Used to make updates to a user
    // ---------------------------------------------------------------------------------------------------------------
    public UserAccountUpdateDto entityToUserAccountUpdateDto(UserAccount userAccount) {
        UserAccountUpdateDto dto = new UserAccountUpdateDto();

        dto.setUserName(userAccount.getUserName());
        dto.setFirstName(userAccount.getFirstName());
        dto.setLastName(userAccount.getLastName());
        dto.setEmail(userAccount.getEmail());
        dto.setRole(userAccount.getRole().name());
        dto.setEnabled(userAccount.isEnabled());

        return dto;
    }

    public UserAccount userAccountUpdateDtoToEntity(UserAccountUpdateDto dto) {
        UserAccount user = userAccountService.findById(dto.getUserName());

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setEnabled(dto.isEnabled());

        return user;
    }
}
