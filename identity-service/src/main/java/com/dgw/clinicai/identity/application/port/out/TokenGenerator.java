package com.dgw.clinicai.identity.application.port.out;

import com.dgw.clinicai.identity.domain.model.User;

public interface TokenGenerator {
    String generateToken(User user);
}