package com.example.restapisb.rest.controllers.user;

import com.example.restapisb.rest.dto.InUserDto;
import com.example.restapisb.rest.dto.OutUserDto;
import com.example.restapisb.rest.dto.ResultDto;
import com.example.restapisb.rest.services.interfaces.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class UserController implements IUserController {

    private final IUserService userService;


    public UserController(
            final IUserService userService
    ) {
        this.userService = userService;
    }


    public ResponseEntity<ResultDto<List<OutUserDto>>> getUsers(
            final Integer page,
            final Integer size,
            final String sort,
            final String name,
            final String email,
            final Integer age
    ) {

        List<OutUserDto> users;
        if (page == null || size == null) {
            users = userService.getUsers();
        } else {
            users = userService.getUsers(page, size);
        }


        if (name != null && !name.isBlank()) {
            users = users.stream().filter(
                    user -> user.name().equals(name)
            ).toList();
        }

        if (email != null && !email.isBlank()) {
            users = users.stream().filter(
                    user -> user.email().equals(email)
            ).toList();
        }

        if (age != null && age >= 0) {
            users = users.stream().filter(
                    user -> user.age() == age
            ).toList();
        }


        return ResponseEntity.ok(
                new ResultDto<>(
                        users,
                        null,
                        null
                )
        );

    }


    public ResponseEntity<ResultDto<OutUserDto>> getUser(String id) {


        var user = userService.getUser(id);

        return ResponseEntity.ok(
                new ResultDto<>(
                        user,
                        null,
                        null
                )
        );
    }

    public ResponseEntity<ResultDto<OutUserDto>> updateUser(String id, final InUserDto userIn) {

        var user = userService.updateUser(id, userIn);

        return ResponseEntity.ok().body(
                new ResultDto<>(
                        user,
                        null,
                        null
                )
        );
    }


    public ResponseEntity<ResultDto<OutUserDto>> insertUser(final InUserDto userIn) {

        var user = userService.insertUser(userIn);

        return ResponseEntity.created(
                        URI.create("/api/users/" + user.id())
                )
                .body(
                        new ResultDto<>(
                                user,
                                null,
                                null
                        )
                );
    }


    public void deleteUser(final String id) {
        userService.deleteUser(id);
    }

}
