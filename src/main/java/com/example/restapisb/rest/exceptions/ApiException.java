package com.example.restapisb.rest.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final String id;
    private final String service;
    private final String code;
    private final HttpStatus httpStatus;


    private ApiException(String id, String service, String message, String code, HttpStatus httpStatus) {
        super(message);
        this.id = id;
        this.service = service;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getId() {
        return id;
    }

    public String getService() {
        return service;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String id;
        private String service;
        private String message;
        private String code;

        private HttpStatus httpStatus;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setService(String service) {
            this.service = service;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ApiException build() {

            if (httpStatus == null) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return new ApiException(id, service, message, code, httpStatus);
        }
    }

}
