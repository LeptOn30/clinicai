package com.dgw.clinicai.billing.domain.model.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record InvoiceIssuedEvent(
        UUID invoiceId,
        UUID patientId,
        BigDecimal totalAmount,
        LocalDate dueDate,
        Instant issuedAt
) implements DomainEvent {
    public InvoiceIssuedEvent(UUID invoiceId, UUID patientId, BigDecimal totalAmount, LocalDate dueDate) {
        this(invoiceId, patientId, totalAmount, dueDate, Instant.now());
    }
}