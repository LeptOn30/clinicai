package com.dgw.clinicai.patient.infrastructure.adapter.in.web;

import com.dgw.clinicai.patient.application.port.in.RegisterNewPatientUseCase;
import com.dgw.clinicai.patient.application.port.in.RegisterPatientCommand;
import com.dgw.clinicai.patient.domain.model.PatientId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final RegisterNewPatientUseCase registerNewPatientUseCase;

    @PostMapping
    public ResponseEntity<String> registerPatient(@Valid @RequestBody RegisterPatientCommand command) {
        PatientId newPatientId = registerNewPatientUseCase.registerNewPatient(command);

        return ResponseEntity
                .created(URI.create("/api/v1/patients/" + newPatientId.getValue()))
                .body("Patient registered successfully with ID: " + newPatientId.getValue());
    }
}