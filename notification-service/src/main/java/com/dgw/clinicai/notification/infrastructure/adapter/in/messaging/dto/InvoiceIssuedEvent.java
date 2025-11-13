package com.dgw.clinicai.notification.infrastructure.adapter.in.messaging.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InvoiceIssuedEvent(
        UUID invoiceId,
        UUID patientId,
        BigDecimal totalAmount,
        LocalDate dueDate
) {}