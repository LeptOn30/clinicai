package com.dgw.clinicai.identity.infrastructure.adapter.in.web;

import com.dgw.clinicai.identity.application.port.in.LoginCommand;
import com.dgw.clinicai.identity.application.port.in.LoginResult;
import com.dgw.clinicai.identity.application.port.in.LoginUseCase;
import com.dgw.clinicai.identity.application.port.in.RegisterUserCommand;
import com.dgw.clinicai.identity.application.port.in.RegisterUserUseCase;
import com.dgw.clinicai.identity.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResult> login(@RequestBody LoginCommand command) {
        return ResponseEntity.ok(loginUseCase.login(command));
    }

    @PostMapping("/register")
    public ResponseEntity<UserId> register(@RequestBody RegisterUserCommand command) {
        UserId newUserId = registerUserUseCase.registerUser(command);
        return new ResponseEntity<>(newUserId, HttpStatus.CREATED);
    }
}