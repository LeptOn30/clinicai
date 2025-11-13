package com.dgw.clinicai.patient.domain.model;

import lombok.Value;
import java.io.Serializable;
import java.util.UUID;

@Value
public class PatientId implements Serializable {
    UUID value;
    public static PatientId from(UUID value) {
        return new PatientId(value);
    }
}