package com.org.bankmsuser.service;

import com.org.bankmsuser.dto.UserCreateDto;
import com.org.bankmsuser.dto.UserDto;
import com.org.bankmsuser.entity.User;
import com.org.bankmsuser.exception.*;
import com.org.bankmsuser.mapper.UserMapper;
import com.org.bankmsuser.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * Create new User and save it in DataBase
     *
     * @param userCreateDto Use  method UserRepository {@link UserRepository#save(Object)} (UserCreateDto)}
     * @return UserDto
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     */
    public UserDto createUser(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        User user = userMapper.toUserEntity(userCreateDto);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        return userMapper.toUserDto(userRepository.save(user));
    }

    /**
     * Get User by id
     *
     * @param id Use  method UserRepository {@link UserRepository#findById(Object)} (Long)}
     * @return UserDto
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     * @throws ServiceException when user doesn't exist (ErrorCode.USER_NOT_FOUND)
     */
    public UserDto getUserById(Long id) {
        if (id == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND)));

    }

    /**
     * Update User
     *
     * @param id          user's identification
     * @param name        user's name
     * @param passport    user's passport number
     * @param phoneNumber user's phone number
     * @return UserDto
     * @throws ServiceException when user doesn't exist (ErrorCode.USER_NOT_FOUND)
     * @throws ServiceException when found non uniq data (ErrorCode.USER_EXISTS)
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     */
    @Transactional
    public UserDto updateUser(Long id, String name, String passport, String phoneNumber) {
        if (id == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        // check name
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));
        if (name != null && !user.getName().equals(name)) {
            user.setName(name);
        }
        // check passport
        if (passport != null && !user.getPassport().equals(passport)) {
            User tempUser = userRepository.findUserByPassport(passport);
            if (tempUser != null && tempUser.getPassport().equals(passport)) {
                throw new ServiceException(ErrorCode.USER_EXISTS);
            }
            user.setPassport(passport);
        }
        // check phone number
        if (phoneNumber != null && !user.getPhoneNumber().equals(phoneNumber)) {
            User tempUser = userRepository.findUserByPhoneNumber(phoneNumber);
            if (tempUser != null && tempUser.getPhoneNumber().equals(phoneNumber)) {
                throw new ServiceException(ErrorCode.USER_EXISTS);
            }
            user.setPhoneNumber(phoneNumber);
        }
        user.setUpdatedDate(LocalDateTime.now());
        return userMapper.toUserDto(user);
    }


    /**
     * Remove User
     *
     * @param id Use  method UserRepository }
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     * @throws ServiceException when user doesn't exist (ErrorCode.USER_NOT_FOUND)
     */
    public void deleteUser(Long id) {
        if (id == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        getUserById(id);
        userRepository.deleteById(id);
    }

    /**
     * Get all users
     *
     * @param page number of page
     * @param size page's size
     * @return List<UserDto>
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     */
    public List<UserDto> getAllUsers(Integer page, Integer size) {
        if (page == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        if (size == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        return userRepository.findAll(PageRequest.of(page, size))
                .map(userMapper::toUserDto)
                .getContent();
    }

    /**
     * Get User by phone nu,ber
     *
     * @param phone Use  method UserRepository {@link UserRepository#findUserByPhoneNumber(String)} }
     * @return UserDto
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     * @throws ServiceException when user doesn't exist (ErrorCode.USER_NOT_FOUND)
     */
    public UserDto getUserByPhone(String phone) {
        if (phone == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        User user = userRepository.findUserByPhoneNumber(phone);
        if (user == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserDto(user);
    }

    /**
     * Get users filtering by date of them registration
     *
     * @param from first date Use  method UserRepository {@link UserRepository#findUsersByCreatedDateBetween(LocalDateTime, LocalDateTime)} }
     * @param to   last date
     * @return List<UserDto>
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     */
    public List<UserDto> filterUsersByDate(LocalDateTime from, LocalDateTime to) {
        if (from == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        if (to == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        return userMapper.toUserDtoList(
                userRepository.findUsersByCreatedDateBetween(from, to)
        );
    }

    /**
     * Find users by passport mask
     *
     * @param passportMask passport mask Use method UserRepository {@link UserRepository#findUsersByPassportContaining(String)} }
     * @return List<UserDto>
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     * @throws ServiceException when user doesn't exist (ErrorCode.USER_NOT_FOUND)
     */
    public List<UserCreateDto> findUsersByPassportMask(String passportMask) {
        if (passportMask == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        List<User> users = userRepository.findUsersByPassportContaining(passportMask);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserCreateDtoList(users);
    }

    /**
     * Find users by phone mask
     *
     * @param phoneMask phone mask Use method UserRepository {@link UserRepository#findUsersByPhoneNumberContaining(String)} }
     * @return List<UserDto>
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     * @throws ServiceException when user doesn't exist (ErrorCode.USER_NOT_FOUND)
     */
    public List<UserDto> findUsersByPhoneMask(String phoneMask) {
        if (phoneMask == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        List<User> users = userRepository.findUsersByPhoneNumberContaining(phoneMask);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserDtoList(users);
    }

    /**
     * Get User having passport number
     *
     * @param passport Use  method UserRepository {@link UserRepository#findUserByPassport(String)} }
     * @return UserDto
     * @throws ServiceException when input data is null (ErrorCode.INPUT_DATA_NOT_VALID)
     * @throws ServiceException when user doesn't exist (ErrorCode.USER_NOT_FOUND)
     */
    public UserDto getByPassport(String passport) {
        if (passport == null) {
            throw new ServiceException(ErrorCode.INPUT_DATA_NOT_VALID);
        }
        User user = userRepository.findUserByPassport(passport);
        if (user == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserDto(user);
    }
}
