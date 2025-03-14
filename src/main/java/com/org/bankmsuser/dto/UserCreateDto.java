package com.org.bankmsuser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserCreateDto {
    @NotBlank
    private String name;
    @Past
    private LocalDate dateOfBirth;
    @NotBlank
    private String passport;
    @Pattern(regexp = "^\\+?[0-9]{4,15}$")
    private String phoneNumber;
}
