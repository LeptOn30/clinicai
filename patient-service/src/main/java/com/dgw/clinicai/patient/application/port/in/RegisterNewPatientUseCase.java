package com.dgw.clinicai.patient.application.port.in;

import com.dgw.clinicai.patient.domain.model.PatientId;

public interface RegisterNewPatientUseCase {
    PatientId registerNewPatient(RegisterPatientCommand command);
}