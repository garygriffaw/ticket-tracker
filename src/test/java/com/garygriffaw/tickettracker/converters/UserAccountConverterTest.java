package com.garygriffaw.tickettracker.converters;

import com.garygriffaw.tickettracker.dto.*;
import com.garygriffaw.tickettracker.entities.UserAccount;
import com.garygriffaw.tickettracker.enums.Role;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserAccountConverterTest {

    @Mock
    UserAccountService userAccountService;

    @InjectMocks
    UserAccountConverter converter;

    @Test
    void registerDtoToEntity() {
        //given
        RegisterDto dto = new RegisterDto();
        dto.setUserName("uname1");
        dto.setFirstName("fname1");
        dto.setLastName("lname1");
        dto.setEmail("email1");
        dto.setPassword("pass1");
        dto.setConfirmPassword("cpass1");

        //when
        UserAccount actualEntity = converter.registerDtoToEntity(dto);

        //then
        assertAll(
            () -> assertThat(actualEntity.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualEntity.getFirstName()).isEqualTo("fname1"),
            () -> assertThat(actualEntity.getLastName()).isEqualTo("lname1"),
            () -> assertThat(actualEntity.getEmail()).isEqualTo("email1"),
            () -> assertThat(actualEntity.getPassword()).isEqualTo("pass1"),
            () -> assertThat(actualEntity.getRole()).isEqualTo(Role.ROLE_USER),
            () -> assertThat(actualEntity.isEnabled()).isTrue()
        );
    }

    @Test
    void entityToUserAccountSelectDto() {
        //given
        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);

        //when
        UserAccountSelectDto actualDto = converter.entityToUserAccountSelectDto(user1);

        //then
        assertAll(
            () -> assertThat(actualDto.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualDto.getUserDisplayValue()).isEqualTo("lname1, fname1 (uname1)")
        );
    }

    @Test
    void entityListToUserAccountSelectDtoList() {
        //given
        List<UserAccount> users = new ArrayList<>();
        users.add(createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true));
        users.add(createUserAccount("uname2", "fname2", "lname2", "email2",
                "pass2", Role.ROLE_ADMIN, false));

        //when
        List<UserAccountSelectDto> actualDtoList = converter.entityListToUserAccountSelectDtoList(users);

        //then
        assertAll(
            () -> assertThat(actualDtoList.size()).isEqualTo(2),
            () -> assertThat(actualDtoList)
                    .extracting("userName")
                    .containsExactlyInAnyOrder("uname1", "uname2"),
            () -> assertThat(actualDtoList)
                    .extracting("userDisplayValue")
                    .containsExactlyInAnyOrder("lname1, fname1 (uname1)", "lname2, fname2 (uname2)")
        );
    }

    @Test
    void entityToProfileDto() {
        //given
        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);

        //when
        ProfileDto actualDto = converter.entityToProfileDto(user1);

        //then
        assertAll(
            () -> assertThat(actualDto.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualDto.getFirstName()).isEqualTo("fname1"),
            () -> assertThat(actualDto.getLastName()).isEqualTo("lname1"),
            () -> assertThat(actualDto.getEmail()).isEqualTo("email1")
        );
    }

    @Test
    void profileDtoToEntity() {
        //given
        ProfileDto dto = new ProfileDto();
        dto.setUserName("uname2");
        dto.setFirstName("fname2");
        dto.setLastName("lname2");
        dto.setEmail("email2");

        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);
        given(userAccountService.findById(any())).willReturn(user1);

        //when
        UserAccount actualEntity = converter.profileDtoToEntity(dto);

        //then
        assertAll(
            () -> assertThat(actualEntity.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualEntity.getFirstName()).isEqualTo("fname2"),
            () -> assertThat(actualEntity.getLastName()).isEqualTo("lname2"),
            () -> assertThat(actualEntity.getEmail()).isEqualTo("email2"),
            () -> assertThat(actualEntity.getPassword()).isEqualTo("pass1"),
            () -> assertThat(actualEntity.getRole()).isEqualTo(Role.ROLE_USER),
            () -> assertThat(actualEntity.isEnabled()).isTrue()
        );
    }

    @Test
    void entityToPasswordDto() {
        //given
        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);

        //when
        PasswordDto actualDto = converter.entityToPasswordDto(user1);

        //then
        assertAll(
            () -> assertThat(actualDto.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualDto.getPassword()).isNull(),
            () -> assertThat(actualDto.getConfirmPassword()).isNull()
        );
    }

    @Test
    void passwordDtoToEntity() {
        //given
        PasswordDto dto = new PasswordDto();
        dto.setPassword("pass2");

        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);
        given(userAccountService.findById(any())).willReturn(user1);

        //when
        UserAccount actualEntity = converter.passwordDtoToEntity(dto);

        //then
        assertAll(
            () -> assertThat(actualEntity.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualEntity.getFirstName()).isEqualTo("fname1"),
            () -> assertThat(actualEntity.getLastName()).isEqualTo("lname1"),
            () -> assertThat(actualEntity.getEmail()).isEqualTo("email1"),
            () -> assertThat(actualEntity.getPassword()).isEqualTo("pass2"),
            () -> assertThat(actualEntity.getRole()).isEqualTo(Role.ROLE_USER),
            () -> assertThat(actualEntity.isEnabled()).isTrue()
        );
    }

    @Test
    void entityToUserAccountQueueDto() {
        //given
        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);

        //when
        UserAccountQueueDto actualDto = converter.entityToUserAccountQueueDto(user1);

        //then
        assertAll(
            () -> assertThat(actualDto.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualDto.getQueueId()).isNull(),
            () -> assertThat(actualDto.getQueueSelectDtoList()).isNull()
        );
    }

    @Test
    void entityToUserAccountTableDto() {
        //given
        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);

        //when
        UserAccountTableDto actualDto = converter.entityToUserAccountTableDto(user1);

        //then
        assertAll(
            () -> assertThat(actualDto.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualDto.getFirstName()).isEqualTo("fname1"),
            () -> assertThat(actualDto.getLastName()).isEqualTo("lname1"),
            () -> assertThat(actualDto.getEmail()).isEqualTo("email1"),
            () -> assertThat(actualDto.getRoleDisplayValue()).isEqualTo("User"),
            () -> assertThat(actualDto.isEnabled()).isTrue()
        );
    }

    @Test
    void entityListToUserAccountTableListDto() {
        //given
        List<UserAccount> users = new ArrayList<>();
        users.add(createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true));
        users.add(createUserAccount("uname2", "fname2", "lname2", "email2",
                "pass2", Role.ROLE_ADMIN, false));

        //when
        List<UserAccountTableDto> actualDtoList = converter.entityListToUserAccountTableListDto(users);

        //then
        assertAll(
            () -> assertThat(actualDtoList.size()).isEqualTo(2),
            () -> assertThat(actualDtoList)
                .extracting("userName")
                .containsExactlyInAnyOrder("uname1", "uname2"),
            () -> assertThat(actualDtoList)
                .extracting("firstName")
                .containsExactlyInAnyOrder("fname1", "fname2"),
            () -> assertThat(actualDtoList)
                .extracting("lastName")
                .containsExactlyInAnyOrder("lname1", "lname2"),
            () -> assertThat(actualDtoList)
                .extracting("email")
                .containsExactlyInAnyOrder("email1", "email2"),
            () -> assertThat(actualDtoList)
                .extracting("roleDisplayValue")
                .containsExactlyInAnyOrder("User", "Admin"),
            () -> assertThat(actualDtoList)
                .extracting("enabled")
                .containsExactlyInAnyOrder(true, false)
        );
    }

    @Test
    void entityToUserAccountUpdateDto() {
        //given
        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);

        //when
        UserAccountUpdateDto actualDto = converter.entityToUserAccountUpdateDto(user1);

        //then
        assertAll(
            () -> assertThat(actualDto.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualDto.getFirstName()).isEqualTo("fname1"),
            () -> assertThat(actualDto.getLastName()).isEqualTo("lname1"),
            () -> assertThat(actualDto.getEmail()).isEqualTo("email1"),
            () -> assertThat(actualDto.getRole()).isEqualTo("ROLE_USER"),
            () -> assertThat(actualDto.getRoleSelectListDto()).isNull(),
            () -> assertThat(actualDto.isEnabled()).isTrue(),
            () -> assertThat(actualDto.getQueues()).isNull()
        );
    }

    @Test
    void userAccountUpdateDtoToEntity() {
        //given
        UserAccountUpdateDto dto = new UserAccountUpdateDto();
        dto.setUserName("uname2");
        dto.setFirstName("fname2");
        dto.setLastName("lname2");
        dto.setEmail("email2");
        dto.setRole("ROLE_ADMIN");
        dto.setEnabled(false);

        UserAccount user1 = createUserAccount("uname1", "fname1", "lname1", "email1",
                "pass1", Role.ROLE_USER, true);
        given(userAccountService.findById(any())).willReturn(user1);

        //when
        UserAccount actualEntity = converter.userAccountUpdateDtoToEntity(dto);

        //then
        assertAll(
            () -> assertThat(actualEntity.getUserName()).isEqualTo("uname1"),
            () -> assertThat(actualEntity.getFirstName()).isEqualTo("fname2"),
            () -> assertThat(actualEntity.getLastName()).isEqualTo("lname2"),
            () -> assertThat(actualEntity.getEmail()).isEqualTo("email2"),
            () -> assertThat(actualEntity.getPassword()).isEqualTo("pass1"),
            () -> assertThat(actualEntity.getRole()).isEqualTo(Role.ROLE_ADMIN),
            () -> assertThat(actualEntity.isEnabled()).isFalse()
        );
    }

    //Helpers
    private UserAccount createUserAccount(String userName, String firstName, String lastName, String email,
                                          String password, Role role, boolean enabled) {
        UserAccount user = new UserAccount();

        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setEnabled(enabled);

        return user;
    }
}