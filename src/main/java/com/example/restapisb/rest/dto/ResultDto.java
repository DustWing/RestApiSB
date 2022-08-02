package com.example.restapisb.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResultDto<T>(
        @JsonProperty("data")
        T data,
        @JsonProperty("links")
        LinksDto links,
        @JsonProperty("metaData")
        MetaDataDto metaData
) {

}
