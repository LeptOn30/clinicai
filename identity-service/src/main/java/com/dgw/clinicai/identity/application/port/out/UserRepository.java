package com.dgw.clinicai.identity.application.port.out;

import com.dgw.clinicai.identity.domain.model.User;
import com.dgw.clinicai.identity.domain.model.UserId;

import java.util.Optional;

public interface UserRepository {
    UserId nextId();
    Optional<User> findByUsername(String username);
    void save(User user);
}