package com.example.restapisb.rest.services.impl;

import com.example.restapisb.database.interfaces.IUserRepo;
import com.example.restapisb.database.model.User;
import com.example.restapisb.rest.dto.InUserDto;
import com.example.restapisb.rest.dto.OutUserDto;
import com.example.restapisb.rest.exceptions.ApiException;
import com.example.restapisb.rest.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {


    private final IUserRepo userRepo;

    public UserService(IUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<OutUserDto> getUsers() {
        return userRepo.findUsers().stream().map(
                user -> new OutUserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getAge()
                )
        ).toList();
    }

    @Override
    public List<OutUserDto> getUsers(final int page, final int size) {

        return userRepo.findUsers(page, size).stream().map(
                user -> new OutUserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getAge()
                )
        ).toList();
    }

    @Override
    public OutUserDto getUser(String id) {
        var user = userRepo.findUser(id);

        if (user == null) {
            throw ApiException.builder().setMessage("User Not found").setHttpStatus(HttpStatus.NOT_FOUND).build();
        }

        return new OutUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }

    @Override
    public OutUserDto insertUser(final InUserDto inboundUserDto) {

        var id = UUID.randomUUID().toString();

//        var userFoundDb = userRepo.getUsersBy(inboundUserDto.name(), inboundUserDto.email());
//        if (userFoundDb != null) {
//            throw ApiException.builder().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage("User already exists").build();
//
//        }

        var user = new User(
                id,
                inboundUserDto.name(),
                inboundUserDto.email(),
                inboundUserDto.age()
        );


        userRepo.insertUser(user);

        return new OutUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }

    @Override
    public OutUserDto updateUser(String id, InUserDto inboundUserDto) {


        var user = userRepo.findUser(id);

        if (user == null) {
            throw ApiException.builder().setMessage("User Not found").setHttpStatus(HttpStatus.NOT_FOUND).build();
        }

        user.setName(inboundUserDto.name());
        user.setEmail(inboundUserDto.email());
        user.setAge(inboundUserDto.age());

        userRepo.insertUser(user);

        return new OutUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }

    @Override
    public void deleteUser(String id) {
        var user = userRepo.findUser(id);

        if (user == null) {
            throw ApiException.builder().setMessage("User Not found").setHttpStatus(HttpStatus.NOT_FOUND).build();
        }
        userRepo.delete(id);
    }
}
