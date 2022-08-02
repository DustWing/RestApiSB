package com.example.restapisb.rest.controllers.user;

import com.example.restapisb.rest.dto.ErrorDto;
import com.example.restapisb.rest.dto.InUserDto;
import com.example.restapisb.rest.dto.OutUserDto;
import com.example.restapisb.rest.dto.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/users", produces = APPLICATION_JSON_VALUE)
public interface IUserController {

    String userID = "userId";

    @Operation(summary = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all users"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    default ResponseEntity<ResultDto<List<OutUserDto>>> getUsers(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "20") int size,
            @RequestParam(required = false, name = "sort", defaultValue = "DESC") String sort,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "age") Integer age

    ) {
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get user By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    default ResponseEntity<ResultDto<OutUserDto>> getUser(@PathVariable(value = userID) final String id) {
        return ResponseEntity.badRequest().build();

    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated User"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    default ResponseEntity<ResultDto<OutUserDto>> updateUser(@PathVariable(value = userID) final String id, @Valid @RequestBody final InUserDto userIn) {
        return ResponseEntity.badRequest().build();

    }

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created User"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default ResponseEntity<ResultDto<OutUserDto>> insertUser(@RequestBody @Valid final InUserDto userIn) {
        return ResponseEntity.badRequest().build();

    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted User"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    default ResponseEntity<ResultDto<OutUserDto>> deleteUser(@PathVariable(value = userID) final String id) {
        return ResponseEntity.badRequest().build();

    }


}
