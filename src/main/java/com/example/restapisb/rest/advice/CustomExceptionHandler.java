package com.example.restapisb.rest.advice;

import com.example.restapisb.rest.dto.ErrorDetailDto;
import com.example.restapisb.rest.dto.ErrorDto;
import com.example.restapisb.rest.exceptions.ApiException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR_MESSAGE_TEMPLATE = "message: %s %n requested uri: %s";
    public static final String FIELD_ERROR_SEPARATOR = ": ";
    public static final String INVALID_FIELDS = "Invalid fields";
    public static final String PARAMS_DELIMITER = "&";
    public static final String LIST_DELIMITER = ",";


    private String uriParamsToStr(final Map<String, String[]> map) {

        StringJoiner parameters = new StringJoiner(PARAMS_DELIMITER);
        map.forEach(
                (k, v) -> parameters.add(k + "=" + String.join(LIST_DELIMITER, v))
        );

        return parameters.toString();

    }

    private String getSelf(WebRequest request) {

        final String path = request.getDescription(false);

        return path + "?" + uriParamsToStr(request.getParameterMap());
    }


    @Override
    @NonNull
    protected ResponseEntity<Object> handleTypeMismatch(
            @NonNull TypeMismatchException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request
    ) {


        if (ex instanceof MethodArgumentTypeMismatchException mismatchException) {

            final String path = getSelf(request);

            var body = new ErrorDto(
                    UUID.randomUUID().toString(),
                    "Service",
                    OffsetDateTime.now(),
                    "Type mismatch",
                    "Type mismatch for parameter " + mismatchException.getName(),
                    status.value(),
                    path,
                    null
            );

            return ResponseEntity.status(status).headers(headers).body(body);
        }


        return super.handleTypeMismatch(ex, headers, status, request);

    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            WebRequest request
    ) {


        final String path = request.getDescription(false);

        var body = new ErrorDto(
                UUID.randomUUID().toString(),
                "Service",
                OffsetDateTime.now(),
                INVALID_FIELDS,
                null,
                status.value(),
                path,
                null
        );

        return ResponseEntity.status(status).headers(headers).body(body);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            WebRequest request
    ) {

        final String path = request.getDescription(false);


        List<ErrorDetailDto> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        new ErrorDetailDto(
                                error.getField() + FIELD_ERROR_SEPARATOR + error.getDefaultMessage())
                )
                .toList();


        var body = new ErrorDto(
                UUID.randomUUID().toString(),
                "Service",
                OffsetDateTime.now(),
                status.getReasonPhrase(),
                INVALID_FIELDS,
                status.value(),
                path,
                validationErrors
        );

        return ResponseEntity.status(status).headers(headers).body(body);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException exception, WebRequest request) {

        final String path = request.getDescription(false);

        final List<ErrorDetailDto> validationErrors = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorDetailDto(
                        violation.getPropertyPath() + FIELD_ERROR_SEPARATOR + violation.getMessage())
                )
                .toList();

        var status = HttpStatus.BAD_REQUEST;

        var body = new ErrorDto(
                UUID.randomUUID().toString(),
                "Service",
                OffsetDateTime.now(),
                status.getReasonPhrase(),
                INVALID_FIELDS,
                status.value(),
                path,
                validationErrors
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleApiExceptions(ApiException exception, WebRequest request) {
        final HttpStatus status = exception.getHttpStatus();
        final String path = request.getDescription(false);

        final String localizedMessage = exception.getMessage();
        final String message = localizedMessage != null ? localizedMessage : status.getReasonPhrase();

        var body = new ErrorDto(
                exception.getId(),
                exception.getService(),
                OffsetDateTime.now(),
                status.getReasonPhrase(),
                message,
                status.value(),
                path,
                null
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        final String path = request.getDescription(false);
        final String localizedMessage = exception.getMessage();
        final String message = localizedMessage != null ? localizedMessage : status.getReasonPhrase();
        logger.error(String.format(ERROR_MESSAGE_TEMPLATE, message, path), exception);

        var body = new ErrorDto(
                UUID.randomUUID().toString(),
                "Service",
                OffsetDateTime.now(),
                status.getReasonPhrase(),
                message,
                status.value(),
                path,
                null
        );


        return ResponseEntity.status(status).body(body);
    }


}
