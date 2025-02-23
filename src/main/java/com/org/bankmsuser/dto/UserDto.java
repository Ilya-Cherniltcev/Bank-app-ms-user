package com.org.bankmsuser.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String name;
    private LocalDateTime dateOfBirth;
    private String phoneNumber;
}
