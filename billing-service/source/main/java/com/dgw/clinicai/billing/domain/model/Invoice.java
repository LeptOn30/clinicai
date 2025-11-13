package com.dgw.clinicai.billing.domain.model;

import com.dgw.clinicai.billing.domain.model.events.InvoiceIssuedEvent;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
public class Invoice {
    private final InvoiceId id;
    private final UUID patientId;
    private final UUID sourceAppointmentId;
    private final List<LineItem> lineItems = new ArrayList<>();
    private final transient List<Object> domainEvents = new ArrayList<>();
    private Money totalAmount;
    private InvoiceStatus status;
    private final LocalDate issueDate;
    private LocalDate dueDate;

    private Invoice(InvoiceId id, UUID patientId, UUID sourceAppointmentId) {
        this.id = id;
        this.patientId = patientId;
        this.sourceAppointmentId = sourceAppointmentId;
        this.status = InvoiceStatus.DRAFT;
        this.issueDate = LocalDate.now();
    }

    public static Invoice createFromAppointment(InvoiceId id, UUID patientId, UUID sourceAppointmentId, List<LineItemData> itemsData) {
        if (itemsData == null || itemsData.isEmpty()) {
            throw new IllegalArgumentException("Invoice must have at least one line item.");
        }
        Invoice invoice = new Invoice(id, patientId, sourceAppointmentId);
        itemsData.forEach(item -> invoice.addLineItem(item.serviceCode(), item.description(), Money.of(item.amount())));
        invoice.issue();
        return invoice;
    }

    private void addLineItem(String serviceCode, String description, Money amount) {
        if (this.status != InvoiceStatus.DRAFT) {
            throw new IllegalStateException("Cannot add items to an invoice that is not in DRAFT status.");
        }
        this.lineItems.add(new LineItem(serviceCode, description, amount));
    }

    private void issue() {
        if (this.status != InvoiceStatus.DRAFT) {
            throw new IllegalStateException("Invoice has already been issued.");
        }
        this.recalculateTotal();
        this.status = InvoiceStatus.ISSUED;
        this.dueDate = this.issueDate.plusDays(30);
        this.domainEvents.add(new InvoiceIssuedEvent(
                this.id.getValue(),
                this.patientId,
                this.totalAmount.getAmount(),
                this.dueDate
        ));
    }

    private void recalculateTotal() {
        this.totalAmount = this.lineItems.stream()
            .map(LineItem::getAmount)
            .reduce(Money.of(BigDecimal.ZERO), Money::add);
    }

    public record LineItemData(String serviceCode, String description, BigDecimal amount) {}

    public List<LineItem> getLineItems() {
        return Collections.unmodifiableList(lineItems);
    }

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}