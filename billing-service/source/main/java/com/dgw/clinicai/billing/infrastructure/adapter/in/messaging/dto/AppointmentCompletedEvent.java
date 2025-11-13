package com.dgw.clinicai.billing.infrastructure.adapter.in.messaging.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AppointmentCompletedEvent(
    UUID appointmentId,
    UUID patientId,
    UUID providerId,
    Instant completionTime,
    List<ServiceCode> performedServices
) {
    public record ServiceCode(String code, String description, BigDecimal standardFee) {}
}