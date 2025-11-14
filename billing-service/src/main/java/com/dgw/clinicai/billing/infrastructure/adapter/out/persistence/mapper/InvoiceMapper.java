package com.dgw.clinicai.billing.infrastructure.adapter.out.persistence.mapper;

import com.dgw.clinicai.billing.domain.model.Invoice;
import com.dgw.clinicai.billing.domain.model.InvoiceId;
import com.dgw.clinicai.billing.domain.model.Money;
import com.dgw.clinicai.billing.infrastructure.adapter.out.persistence.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "totalAmount.amount", target = "totalAmount")
    @Mapping(source = "status", target = "status")
    InvoiceEntity toEntity(Invoice(id.value, patientId, sourceAppointmentId) invoice);

    // Note: Mapping back to a domain model is more complex as it involves reconstructing the aggregate.
    // This is a simplified version for read operations.
    Invoice toDomain(InvoiceEntity entity);
}
