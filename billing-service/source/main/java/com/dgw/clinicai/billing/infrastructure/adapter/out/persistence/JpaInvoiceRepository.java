package com.dgw.clinicai.billing.infrastructure.adapter.out.persistence;

import com.dgw.clinicai.billing.application.port.out.InvoiceRepository;
import com.dgw.clinicai.billing.domain.model.Invoice;
import com.dgw.clinicai.billing.domain.model.InvoiceId;
import com.dgw.clinicai.billing.infrastructure.adapter.out.persistence.mapper.InvoiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaInvoiceRepository implements InvoiceRepository {

    private final SpringInvoiceRepository springRepo;
    private final InvoiceMapper mapper;

    @Override
    public InvoiceId nextId() {
        return InvoiceId.from(UUID.randomUUID());
    }

    @Override
    public void save(Invoice invoice) {
        springRepo.save(mapper.toEntity(invoice));
    }

    @Override
    public Optional<Invoice> findBySourceAppointmentId(UUID sourceAppointmentId) {
        return springRepo.findBySourceAppointmentId(sourceAppointmentId).map(mapper::toDomain);
    }
}