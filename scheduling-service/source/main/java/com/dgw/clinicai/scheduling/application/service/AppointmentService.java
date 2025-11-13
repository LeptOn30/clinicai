package com.dgw.clinicai.scheduling.application.service;

import com.dgw.clinicai.scheduling.application.port.in.CompleteAppointmentCommand;
import com.dgw.clinicai.scheduling.application.port.in.CompleteAppointmentUseCase;
import com.dgw.clinicai.scheduling.application.port.out.AppointmentRepository;
import com.dgw.clinicai.scheduling.application.port.out.EventPublisher;
import com.dgw.clinicai.scheduling.domain.model.AppointmentId;
import com.dgw.clinicai.scheduling.domain.model.events.AppointmentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService implements CompleteAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public void completeAppointment(CompleteAppointmentCommand command) {
        log.info("Completing appointment with ID: {}", command.appointmentId());
        var appointmentId = AppointmentId.from(command.appointmentId());

        var appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        var serviceCodes = command.performedServices().stream()
                .map(sc -> new AppointmentCompletedEvent.ServiceCode(sc.code(), sc.description(), sc.standardFee()))
                .collect(Collectors.toList());

        appointment.complete(serviceCodes);
        appointmentRepository.save(appointment);

        appointment.getDomainEvents().forEach(eventPublisher::publish);
        log.info("Published AppointmentCompletedEvent for appointment ID: {}", command.appointmentId());
    }
}