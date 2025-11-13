package com.dgw.clinicai.patient.application.service;

import com.dgw.clinicai.patient.application.port.in.RegisterNewPatientUseCase;
import com.dgw.clinicai.patient.application.port.in.RegisterPatientCommand;
import com.dgw.clinicai.patient.application.port.out.PatientRepository;
import com.dgw.clinicai.patient.domain.model.Address;
import com.dgw.clinicai.patient.domain.model.ContactInfo;
import com.dgw.clinicai.patient.domain.model.Patient;
import com.dgw.clinicai.patient.domain.model.PatientId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService implements RegisterNewPatientUseCase {

    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public PatientId registerNewPatient(RegisterPatientCommand command) {
        log.info("Attempting to register new patient with email: {}", command.email());

        patientRepository.findByEmail(command.email()).ifPresent(p -> {
            throw new PatientAlreadyExistsException(p.getId());
        });

        Patient newPatient = Patient.register(
                patientRepository.nextId(),
                command.firstName(),
                command.lastName(),
                command.dateOfBirth(),
                new ContactInfo(command.email(), command.phoneNumber()),
                new Address(command.street(), command.city(), command.state(), command.zipCode())
        );

        patientRepository.save(newPatient);
        log.info("Successfully registered new patient with ID: {}", newPatient.getId().getValue());
        return newPatient.getId();
    }

    public static class PatientAlreadyExistsException extends IllegalStateException {
        public PatientAlreadyExistsException(PatientId existingId) {
            super("A patient with this email already exists with ID: " + existingId.getValue());
        }
    }
}