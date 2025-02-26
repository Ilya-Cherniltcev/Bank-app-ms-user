package com.org.bankmsuser.service;

import com.org.bankmsuser.dto.UserDto;
import com.org.bankmsuser.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public Long createUser(UserDto userDto) {
    }

    public UserDto getUserById(Long id) {
    }

    public UserDto updateUser(Long id, UserDto userDto) {
    }

    public UserDto partialUpdateUser(Long id, Map<String, Object> updates) {
    }

    public void deleteUser(Long id) {

    }

    public List<UserDto> getAllUsers(int page, int size) {
    }

    public UserDto getUserByPhone(String phone) {
    }

    public List<UserDto> filterUsersByDate(LocalDate from, LocalDate to) {
    }

    public UserDto updatePhoneNumber(Long id, String phone) {
    }


    public UserDto verifyPassport(Long id) {
    }
}
