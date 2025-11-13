package com.dgw.clinicai.identity.domain.model;

import lombok.Getter;

import java.util.Set;

@Getter
public class User {
    private final UserId id;
    private final String username;
    private final String password; // Hashed password
    private final Set<Role> roles;

    private User(UserId id, String username, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public static User create(UserId id, String username, String hashedPassword, Set<Role> roles) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role.");
        }
        return new User(id, username, hashedPassword, roles);
    }
}