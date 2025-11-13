package com.dgw.clinicai.billing.application.service;

import com.dgw.clinicai.billing.application.port.in.CreateInvoiceCommand;
import com.dgw.clinicai.billing.application.port.in.CreateInvoiceUseCase;
import com.dgw.clinicai.billing.application.port.out.InvoiceRepository;
import com.dgw.clinicai.billing.application.port.out.EventPublisher;
import com.dgw.clinicai.billing.domain.model.Invoice;
import com.dgw.clinicai.billing.domain.model.InvoiceId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceService implements CreateInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public InvoiceId createInvoiceFromAppointment(CreateInvoiceCommand command) {
        log.info("Attempting to create invoice for appointment ID: {}", command.sourceAppointmentId());

        invoiceRepository.findBySourceAppointmentId(command.sourceAppointmentId()).ifPresent(invoice -> {
            log.warn("Invoice for appointment {} already exists with ID {}. Skipping creation.",
                command.sourceAppointmentId(), invoice.getId().getValue());
            throw new InvoiceAlreadyExistsException(invoice.getId());
        });

        var lineItemsData = command.lineItems().stream()
            .map(item -> new Invoice.LineItemData(item.serviceCode(), item.description(), item.amount()))
            .collect(Collectors.toList());

        Invoice newInvoice = Invoice.createFromAppointment(invoiceRepository.nextId(), command.patientId(), command.sourceAppointmentId(), lineItemsData);
        invoiceRepository.save(newInvoice);
        newInvoice.getDomainEvents().forEach(eventPublisher::publish);

        log.info("Successfully created and saved new invoice with ID: {}", newInvoice.getId().getValue());
        return newInvoice.getId();
    }
    
    public static class InvoiceAlreadyExistsException extends IllegalStateException {
        public InvoiceAlreadyExistsException(InvoiceId existingId) {
            super("Invoice already exists with ID: " + existingId.getValue());
        }
    }
}