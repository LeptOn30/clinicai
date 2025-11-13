package com.dgw.clinicai.patient.application.port.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RegisterPatientCommand(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotNull LocalDate dateOfBirth,
    @NotBlank @Email String email,
    @NotBlank String phoneNumber,
    String street, String city, String state, String zipCode
) {}