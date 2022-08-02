package com.example.restapisb.database.repos;

import com.example.restapisb.database.interfaces.IUserRepo;
import com.example.restapisb.database.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepo implements IUserRepo {

    private final ConcurrentHashMap<String, User> userCache = new ConcurrentHashMap<>();

    @Override
    public List<User> findUsers() {
        return userCache.values().stream().toList();
    }

    @Override
    public List<User> findUsers(int page, int size) {

        if (page <= 0) {
            page = 1;
        }

        int from = (page - 1) * size;

        int to = page * size;

        if (userCache.values().size() < from) {
            return List.of();
        }

        if (userCache.values().size() < to) {
            return userCache.values().stream().skip(from).toList();
        }

        return userCache.values().stream().skip(from).limit(size).toList();

    }


    @Override
    public User findUser(String id) {
        return userCache.get(id);
    }

    @Override
    public void insertUser(User user) {
        userCache.put(user.getId(), user);
    }

    @Override
    public void delete(String id) {
        userCache.remove(id);
    }

    @Override
    public List<User> findUsers(Collection<String> ids) {

        return userCache.values().stream().filter(
                e -> ids.contains(e.getId())
        ).toList();
    }

    @Override
    public User getUsersBy(String name, String email) {

        return userCache.values().stream().filter(
                e -> e.getEmail().equals(email) || e.getName().equals(name)
        ).findAny().orElse(null);
    }
}
