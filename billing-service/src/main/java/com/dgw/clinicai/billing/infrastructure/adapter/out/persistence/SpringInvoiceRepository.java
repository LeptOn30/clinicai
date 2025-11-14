package com.dgw.clinicai.billing.infrastructure.adapter.out.persistence;

import com.dgw.clinicai.billing.infrastructure.adapter.out.persistence.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringInvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {
    Optional<InvoiceEntity> findBySourceAppointmentId(UUID sourceAppointmentId);
}