package com.dgw.clinicai.scheduling.domain.model.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AppointmentCompletedEvent(
        UUID appointmentId,
        UUID patientId,
        UUID providerId,
        List<ServiceCode> performedServices,
        Instant completionTime
) implements DomainEvent {
    public AppointmentCompletedEvent(UUID appointmentId, UUID patientId, UUID providerId, List<ServiceCode> performedServices) {
        this(appointmentId, patientId, providerId, performedServices, Instant.now());
    }
    public record ServiceCode(String code, String description, BigDecimal standardFee) {}
}