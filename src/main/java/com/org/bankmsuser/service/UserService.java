package com.org.bankmsuser.service;

import com.org.bankmsuser.dto.UserCreateDto;
import com.org.bankmsuser.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateDto userCreateDto);

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, String name, String passport, String phoneNumber);

    void deleteUser(Long id);

    List<UserDto> getAllUsers(Integer page, Integer size);

    UserDto getUserByPhone(String phone);

    List<UserDto> filterUsersByDate(LocalDateTime from, LocalDateTime to);

    List<UserCreateDto> findUsersByPassportMask(String passportMask);

    List<UserDto> findUsersByPhoneMask(String phoneMask);

    UserDto getByPassport(String passport);
}
