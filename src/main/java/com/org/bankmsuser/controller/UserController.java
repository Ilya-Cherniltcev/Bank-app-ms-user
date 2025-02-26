package com.org.bankmsuser.controller;

import com.org.bankmsuser.dto.UserDto;
import com.org.bankmsuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // получаю юзера по айди
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //создаю юзера
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        Long userId = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    // обновляю юзера (PUT - для всех полей)
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    // обновляю частично (PATCH - только переданные поля)
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(userService.partialUpdateUser(id, updates));
    }

    // удаляю юзера
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // получаю список всех юзеров
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    // поиск по номеру телефона
    @GetMapping("/by-phone")
    public ResponseEntity<UserDto> getUserByPhone(@RequestParam String phone) {
        return ResponseEntity.ok(userService.getUserByPhone(phone));
    }

    // фильтрую по дате рождения(надо ли?)
    @GetMapping("/filter")
    public ResponseEntity<List<UserDto>> filterUsersByDate(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(userService.filterUsersByDate(from, to));
    }

    // обновляю номер телефона
    @PatchMapping("/{id}/phone")
    public ResponseEntity<UserDto> updatePhoneNumber(@PathVariable Long id, @RequestParam String phone) {
        return ResponseEntity.ok(userService.updatePhoneNumber(id, phone));
    }

    // проверяю паспорт
    @PatchMapping("/{id}/verify-passport")
    public ResponseEntity<UserDto> verifyPassport(@PathVariable Long id) {
        return ResponseEntity.ok(userService.verifyPassport(id));
    }
}
