package com.org.bankmsuser;

import com.org.bankmsuser.configuration.TestLiquibaseConfig;
import com.org.bankmsuser.dto.UserCreateDto;
import com.org.bankmsuser.dto.UserDto;
import com.org.bankmsuser.entity.User;
import com.org.bankmsuser.exception.DataExistException;
import com.org.bankmsuser.exception.IncorrectInputDataException;
import com.org.bankmsuser.exception.UserNotFoundException;
import com.org.bankmsuser.mapper.UserMapper;
import com.org.bankmsuser.repository.UserRepository;
import com.org.bankmsuser.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import com.org.bankmsuser.exception.IncorrectInputDataException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Import(TestLiquibaseConfig.class)
class BankMsUserApplicationTests {
    @InjectMocks
    UserService userService;
    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;
    UserDto testUserDto;
    User testUser = new User();
    UserCreateDto testUserCreateDto = new UserCreateDto();

    @BeforeEach
    void init() {
        testUserDto = new UserDto();
        testUserDto.setName("name");
        testUserDto.setDateOfBirth(LocalDate.of(1999, 1, 1));
        testUserDto.setPhoneNumber("+12345");
        testUser.setName(testUserDto.getName());
        testUser.setPassport("12345");
        testUser.setPhoneNumber(testUserDto.getPhoneNumber());
        testUserCreateDto.setName(testUser.getName());
    }

    // Testing of createUser method ---
    @Test
    void shouldThrowExceptionWhenCreatingUserWithNullInput() {
        assertThrows(IncorrectInputDataException.class, () -> userService.createUser(null));
    }

    @Test
    void shouldReturnUserDtoWhenCreating() {
        when(userRepository.save(any())).thenReturn(testUser);
        when(userMapper.toUserEntity(testUserCreateDto)).thenReturn(testUser);
        when(userMapper.toUserDto(any())).thenReturn(testUserDto);
        assertEquals(testUserDto, userService.createUser(testUserCreateDto));
    }

    // Testing of getUserById method ---
    @Test
    void shouldThrowExceptionWhenGetUserByIdWithNullInput() {
        assertThrows(IncorrectInputDataException.class, () -> userService.getUserById(null));
    }

    @Test
    void shouldReturnUserDtoWhenGetUserById() {
        Optional<User> testUserOptional = Optional.of(testUser);
        when(userRepository.findById(any())).thenReturn(testUserOptional);
        when(userMapper.toUserDto(any())).thenReturn(testUserDto);
        assertEquals(testUserDto, userService.getUserById(1L));
    }

    @Test
    void shouldThrowExceptionWhenGetUserByIdAndUserNotFound() {
        when(userRepository.findById(any())).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    // Testing of updateUser method ---
    @Test
    void shouldThrowExceptionWhenUpdateUserWithNullIdInput() {
        assertThrows(IncorrectInputDataException.class, () -> userService.updateUser(null, "", "", ""));
    }

    @Test
    void shouldThrowExceptionWhenUpdateUserAndThisPassportIsExist() {
        User updateUserTest = new User();
        updateUserTest.setPassport("1");
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
        when(userRepository.findUserByPassport(any())).thenReturn(updateUserTest);
        assertThrows(DataExistException.class, () -> userService.updateUser(1L, "", "1", ""));
    }

    @Test
    void shouldThrowExceptionWhenUpdateUserAndThisPhoneNumberIsExist() {
        User updateUserTest = new User();
        updateUserTest.setPhoneNumber("55555");
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
        when(userRepository.findUserByPhoneNumber(any())).thenReturn(updateUserTest);
        assertThrows(DataExistException.class, () -> userService.updateUser(1L, "", "", "55555"));
    }

    @Test
    void shouldReturnUserDtoWhenUpdateUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
        when(userRepository.findUserByPhoneNumber(any())).thenReturn(testUser);
        when(userMapper.toUserDto(any())).thenReturn(testUserDto);
        assertEquals(testUserDto, userService.updateUser(1L, "", "", ""));
    }

    // Testing of deleteUser method ---
    @Test
    void shouldThrowExceptionWhenDeleteUserWithNullIdInput() {
        assertThrows(IncorrectInputDataException.class, () -> userService.deleteUser(null));
    }

    // Testing of findUsersByPassportMask method ---
    @Test
    void shouldThrowExceptionWhenFindUsersByPassportMaskWithNullIdInput() {
        assertThrows(IncorrectInputDataException.class, () -> userService.findUsersByPassportMask(null));
    }

    @Test
    void shouldThrowExceptionWhenFindUsersByPassportMaskAndUsersAreNotFound() {
        when(userRepository.findUsersByPassportContaining(any())).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.findUsersByPassportMask("111"));
    }

    @Test
    void shouldReturnUserCreateDtoListWhenFindUsersByPassportMask() {
        List<User> testUserList = new ArrayList<>(List.of(testUser));
        List<UserCreateDto> testUserCreateDtoList = new ArrayList<>(List.of(testUserCreateDto));
        when(userRepository.findUsersByPassportContaining(any())).thenReturn(testUserList);
        when(userMapper.toUserCreateDtoList(any())).thenReturn(testUserCreateDtoList);
        assertEquals(testUserCreateDtoList, userService.findUsersByPassportMask("111"));
    }

    // Testing of findUsersByPhoneMask method ---
    @Test
    void shouldThrowExceptionWhenFindUsersByPhoneMaskWithNullIdInput() {
        assertThrows(IncorrectInputDataException.class, () -> userService.findUsersByPhoneMask(null));
    }

    @Test
    void shouldThrowExceptionWhenFindUsersByPhoneMaskAndUsersAreNotFound() {
        when(userRepository.findUsersByPhoneNumberContaining(any())).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.findUsersByPhoneMask("111"));
    }

    @Test
    void shouldReturnUserDtoListWhenFindUsersByPhoneMask() {
        List<User> testUserList = new ArrayList<>(List.of(testUser));
        List<UserDto> testUserDtoList = new ArrayList<>(List.of(testUserDto));
        when(userRepository.findUsersByPhoneNumberContaining(any())).thenReturn(testUserList);
        when(userMapper.toUserDtoList(any())).thenReturn(testUserDtoList);
        assertEquals(testUserDtoList, userService.findUsersByPhoneMask("111"));
    }

    // Testing of getByPassport method ---
    @Test
    void shouldThrowExceptionWhenGetByPassportWithNullIdInput() {
        assertThrows(IncorrectInputDataException.class, () -> userService.getByPassport(null));
    }

    @Test
    void shouldThrowExceptionWhenGetByPassportAndUsersAreNotFound() {
        when(userRepository.findUserByPassport(any())).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.getByPassport("111"));
    }

    // Testing of getAllUsers method ---
    @Test
    void shouldReturnListOfUsersWhenValidPageAndSizeProvided() {
        // Arrange
        User user = new User();
        UserDto userDto = new UserDto();
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        // Act
        List<UserDto> result = userService.getAllUsers(0, 5);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionWhenPageIsNull() {
        assertThrows(IncorrectInputDataException.class, () -> userService.getAllUsers(null, 5));
    }

    @Test
    void shouldThrowExceptionWhenSizeIsNull() {
        assertThrows(IncorrectInputDataException.class, () -> userService.getAllUsers(0, null));
    }

    // Testing of getUserByPhone method ---
    @Test
    void shouldReturnUserDtoWhenUserExists() {
        // Arrange
        String phone = "123456789";
        User user = new User();
        UserDto userDto = new UserDto();
        when(userRepository.findUserByPhoneNumber(phone)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUserByPhone(phone);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findUserByPhoneNumber(phone);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        String phone = "123456789";
        when(userRepository.findUserByPhoneNumber(phone)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getUserByPhone(phone));
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsNull() {
        assertThrows(IncorrectInputDataException.class, () -> userService.getUserByPhone(null));
    }

}
