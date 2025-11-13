package com.dgw.clinicai.scheduling.application.port.out;

import com.dgw.clinicai.scheduling.domain.model.Appointment;
import com.dgw.clinicai.scheduling.domain.model.AppointmentId;

import java.util.Optional;

public interface AppointmentRepository {
    AppointmentId nextId();
    void save(Appointment appointment);
    Optional<Appointment> findById(AppointmentId appointmentId);
}