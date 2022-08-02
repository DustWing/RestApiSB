package com.example.restapisb.rest.dto;

import javax.validation.constraints.*;

public record InUserDto(
        @NotNull
        @NotBlank
        @Size(max = 100)
        String name,
        @NotNull
        @NotBlank
        @Email
        @Size(max = 250)
        String email,
        @NotNull
        @Min(0)
        @Max(101)
        Integer age
) {
}
