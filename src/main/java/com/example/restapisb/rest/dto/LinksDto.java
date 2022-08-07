package com.example.restapisb.rest.dto;

import java.util.List;

public record LinksDto(
        String self,
        List<String> rel,
        String first,
        String prev,
        String next,
        String last
) {

}