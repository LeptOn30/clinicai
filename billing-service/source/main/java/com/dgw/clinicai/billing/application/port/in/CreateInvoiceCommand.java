package com.dgw.clinicai.billing.application.port.in;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateInvoiceCommand(
    UUID patientId,
    UUID sourceAppointmentId,
    List<LineItem> lineItems
) {
    public record LineItem(String serviceCode, String description, BigDecimal amount) {}
}