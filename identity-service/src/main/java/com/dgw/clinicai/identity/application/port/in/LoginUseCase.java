package com.dgw.clinicai.identity.application.port.in;

import org.springframework.security.core.AuthenticationException;

public interface LoginUseCase {
    LoginResult login(LoginCommand command) throws AuthenticationException;
}