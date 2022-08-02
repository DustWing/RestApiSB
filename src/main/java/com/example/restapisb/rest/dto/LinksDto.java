package com.example.restapisb.rest.dto;

public record LinksDto(
        String self,
        String related,
        String next,
        String last
) {

}