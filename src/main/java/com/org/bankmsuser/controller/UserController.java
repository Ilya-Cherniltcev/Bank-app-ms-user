package com.org.bankmsuser.controller;

import com.org.bankmsuser.dto.UserCreateDto;
import com.org.bankmsuser.dto.UserDto;
import com.org.bankmsuser.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    // получаю юзера по айди
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //создаю юзера
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreateDto));
    }

    // обновляю юзера (PUT - для всех полей)
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "passport", required = false) String passport,
                                              @RequestParam(value = "phoneNumber", required = false) String phoneNumber) {
        return ResponseEntity.ok(userService.updateUser(id, name, passport, phoneNumber));
    }


    // удаляю юзера
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // получаю список всех юзеров
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // поиск по номеру телефона
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserDto> getUserByPhone(@RequestParam(name = "phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(userService.getUserByPhone(phoneNumber));
    }

    // фильтрую юзеров по дате-времени занесения в бд
    @GetMapping("/filter/by-reg-date/")
    public ResponseEntity<List<UserDto>> filterUsersByDateOfRegistration(@RequestParam(name = "dateTimeFrom") LocalDateTime from, @RequestParam(name = "dateTimeTo") LocalDateTime to) {
        return ResponseEntity.ok(userService.filterUsersByDate(from, to));
    }


    // находим юзеров по части номера паспорта
    @GetMapping("/filter/by-passport/{passportMask}")
    public ResponseEntity<List<UserCreateDto>> findUsersByPassportMask(@PathVariable String passportMask) {
        return ResponseEntity.ok(userService.findUsersByPassportMask(passportMask));
    }

    // находим юзеров по части номера телефона
    @GetMapping("/filter/by-phone/{phoneMask}")
    public ResponseEntity<List<UserDto>> findUsersByPhoneMask(@PathVariable String phoneMask) {
        return ResponseEntity.ok(userService.findUsersByPhoneMask(phoneMask));
    }


    // получаем пользователя по паспорту
    @GetMapping("/passport/{passportNumber}")
    public ResponseEntity<UserDto> getUserByPassport(@PathVariable String passportNumber) {
        return ResponseEntity.ok(userService.getByPassport(passportNumber));
    }
}
