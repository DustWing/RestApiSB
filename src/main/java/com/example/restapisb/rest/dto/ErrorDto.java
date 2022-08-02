package com.example.restapisb.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorDto(
        String id,
        String service,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss+", shape = JsonFormat.Shape.STRING)
        OffsetDateTime timestamp,
        String error,
        String message,
        int status,
        String path,
        List<ErrorDetailDto> errorDetails

) {
}
