package com.org.bankmsuser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCreateDto {
    @NotBlank
    private String name;
    @Past
    private LocalDateTime dateOfBirth;
    @NotBlank
    private String passport;
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phoneNumber;
}
