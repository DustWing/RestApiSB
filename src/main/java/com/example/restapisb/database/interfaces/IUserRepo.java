package com.example.restapisb.database.interfaces;


import com.example.restapisb.database.model.User;

import java.util.Collection;
import java.util.List;

public interface IUserRepo {


    List<User> findUsers();

    List<User> findUsers(final int page, final int size);


    User findUser(final String id);

    void insertUser(final User user);

    void delete(final String id);

    List<User> findUsers(Collection<String> ids);

    User getUsersBy(final String name, final String email);


}
