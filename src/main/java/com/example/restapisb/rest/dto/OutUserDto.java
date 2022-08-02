package com.example.restapisb.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OutUserDto(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("email")
        String email,
        @JsonProperty("age")
        int age
) {
}
