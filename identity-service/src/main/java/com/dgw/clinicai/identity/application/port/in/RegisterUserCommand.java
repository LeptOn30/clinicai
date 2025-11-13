package com.dgw.clinicai.identity.application.port.in;

import com.dgw.clinicai.identity.domain.model.Role;
import java.util.Set;

public record RegisterUserCommand(
        String username,
        String password,
        Set<Role> roles
) {
}