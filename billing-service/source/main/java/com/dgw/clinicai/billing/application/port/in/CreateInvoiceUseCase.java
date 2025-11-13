package com.dgw.clinicai.billing.application.port.in;

import com.dgw.clinicai.billing.domain.model.InvoiceId;

public interface CreateInvoiceUseCase {
    InvoiceId createInvoiceFromAppointment(CreateInvoiceCommand command);
}