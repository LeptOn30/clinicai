package com.dgw.clinicai.identity.application.service;

import com.dgw.clinicai.identity.application.port.in.LoginCommand;
import com.dgw.clinicai.identity.application.port.in.LoginResult;
import com.dgw.clinicai.identity.application.port.in.LoginUseCase;
import com.dgw.clinicai.identity.application.port.in.RegisterUserCommand;
import com.dgw.clinicai.identity.application.port.in.RegisterUserUseCase;
import com.dgw.clinicai.identity.application.port.out.TokenGenerator;
import com.dgw.clinicai.identity.application.port.out.UserRepository;
import com.dgw.clinicai.identity.domain.model.User;
import com.dgw.clinicai.identity.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityService implements LoginUseCase, RegisterUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResult login(LoginCommand command) throws AuthenticationException {
        log.info("Attempting to authenticate user: {}", command.username());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.username(), command.password())
        );

        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in repository"));

        String token = tokenGenerator.generateToken(user);
        return new LoginResult(token);
    }

    @Override
    public UserId registerUser(RegisterUserCommand command) {
        log.info("Attempting to register new user: {}", command.username());

        userRepository.findByUsername(command.username()).ifPresent(u -> {
            throw new UserAlreadyExistsException(command.username());
        });

        User newUser = User.create(
                userRepository.nextId(),
                command.username(),
                passwordEncoder.encode(command.password()),
                command.roles()
        );

        userRepository.save(newUser);
        return newUser.getId();
    }

    public static class UserAlreadyExistsException extends IllegalStateException {
        public UserAlreadyExistsException(String username) {
            super("User with username '" + username + "' already exists.");
        }
    }
}