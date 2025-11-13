package com.dgw.clinicai.identity.application.port.in;

import com.dgw.clinicai.identity.domain.model.UserId;

public interface RegisterUserUseCase {
    UserId registerUser(RegisterUserCommand command);
}