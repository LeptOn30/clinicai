package com.dgw.clinicai.scheduling.domain.model;

import lombok.Value;
import java.io.Serializable;
import java.util.UUID;

@Value
public class AppointmentId implements Serializable {
    UUID value;
    public static AppointmentId from(UUID value) {
        return new AppointmentId(value);
    }
}