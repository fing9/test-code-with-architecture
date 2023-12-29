package com.example.demo.user.service.port;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import java.util.Optional;

public interface UserRepository {

    User getById(long id);

    User save(User user);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findById(long id);
}
