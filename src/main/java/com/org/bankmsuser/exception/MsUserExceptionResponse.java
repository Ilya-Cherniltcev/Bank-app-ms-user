package com.org.bankmsuser.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MsUserExceptionResponse {
    private final LocalDateTime timestamp;
    private final Integer status;
    private final String path;
    private final String message;
}
