package com.example.restapisb.rest.services.interfaces;

import com.example.restapisb.rest.dto.InUserDto;
import com.example.restapisb.rest.dto.OutUserDto;

import java.util.List;

public interface IUserService {


    List<OutUserDto> getUsers();

    List<OutUserDto> getUsers(final int page, final int size);


    OutUserDto getUser(final String id);

    OutUserDto insertUser(final InUserDto inboundUserDto);

    OutUserDto updateUser(final String id, final InUserDto inboundUserDto);

    void deleteUser(final String id);

}
