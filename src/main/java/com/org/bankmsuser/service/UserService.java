package com.org.bankmsuser.service;

import com.org.bankmsuser.dto.UserCreateDto;
import com.org.bankmsuser.dto.UserDto;
import com.org.bankmsuser.entity.User;
import com.org.bankmsuser.exception.UserNotFoundException;
import com.org.bankmsuser.mapper.UserMapper;
import com.org.bankmsuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

<<<<<<< HEAD

=======
    /**
     * Create new User and save it in DataBase
     *
     * @param userCreateDto Use  method UserRepository {@link UserRepository#save(Object)} (UserCreateDto)}
     * @return UserDto
     */
    public UserDto createUser(UserCreateDto userCreateDto) {
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
     * @throws UserNotFoundException when user doesn't exist
     */
    public UserDto getUserById(Long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    /**
     * Update User
     *
     * @param userCreateDto Use  method UserRepository {@link UserRepository#updateUserById(Long, User)} }
     * @return UserDto
     * @throws UserNotFoundException when user doesn't exist
     */
    public UserDto updateUser(Long id, UserCreateDto userCreateDto) {
        getUserById(id);
        User user = userMapper.toUserEntity(userCreateDto);
        user.setUpdatedDate(LocalDateTime.now());
        return userMapper.toUserDto(userRepository.updateUserById(id, user));
    }

    /**
     * partial Update User
     *
     * @param userCreateDto Use  method UserRepository {@link UserRepository#updateUserById(Long, User)} }
     * @return UserDto or throw Exception (if it doesn't exist)
     */
    public UserDto partialUpdateUser(Long id, Map<String, Object> updates) {
        isUserExist(id);


    }

    /**
     * Remove User
     *
     * @param id Use  method UserRepository {@link UserRepository#deleteUserById(Long)} }
     * @throws UserNotFoundException when user doesn't exist
     */
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteUserById(id);
    }

    public List<UserDto> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .map(userMapper::toUserDto)
                .getContent();
    }

    public UserDto getUserByPhone(String phone) {
        return userMapper.toUserDto(
                userRepository.findByPhone(phone)
                        .orElseThrow(() -> new UserNotFoundException())
        );
    }

    public List<UserDto> filterUsersByDate(LocalDate from, LocalDate to) {
        return userMapper.toUserDtoList(
                userRepository.findByRegistrationDateBetween(from, to)
        );
    }

    //----------------избыточно, но пока оставим---------------
//    @Transactional
//    public UserDto updatePhoneNumber(Long id, String phone) {
//        Optional<User> optionalUser = userRepository.findById(id);
//        if (!optionalUser.isPresent()) {
//            throw new UserNotFoundException("User with ID " + id + " not found");
//        }
//        User user = optionalUser.get();
//        user.setPhoneNumber(phone);
//        user.setUpdatedDate(LocalDateTime.now());
//        return userMapper.toUserDto(userRepository.save(user));
//----------------------------------------------------------------------


    /**
     * Get User having passport number
     *
     * @param passport Use  method UserRepository {@link UserRepository#findUserByPassport(String)} }
     * @return UserDto
     * @throws UserNotFoundException when user doesn't exist
     */
    public UserDto getByPassport(String passport) {
        User user = userRepository.findUserByPassport(passport);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return userMapper.toUserDto(user);
    }
>>>>>>>
}
