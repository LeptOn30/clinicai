package com.dgw.clinicai.identity.application.port.in;

public record LoginCommand(
        String username,
        String password
) {
}