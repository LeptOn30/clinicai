package com.dgw.clinicai.patient.application.port.out;

import com.dgw.clinicai.patient.domain.model.Patient;
import com.dgw.clinicai.patient.domain.model.PatientId;
import java.util.Optional;

public interface PatientRepository {
    PatientId nextId();
    void save(Patient patient);
    Optional<Patient> findByEmail(String email);
}