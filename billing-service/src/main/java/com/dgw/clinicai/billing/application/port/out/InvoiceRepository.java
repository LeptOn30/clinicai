package com.dgw.clinicai.billing.application.port.out;

import com.dgw.clinicai.billing.domain.model.Invoice;
import com.dgw.clinicai.billing.domain.model.InvoiceId;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository {
    InvoiceId nextId();
    void save(Invoice invoice);
    Optional<Invoice> findBySourceAppointmentId(UUID sourceAppointmentId);
}