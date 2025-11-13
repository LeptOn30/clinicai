package com.dgw.clinicai.billing.infrastructure.adapter.out.messaging;

import com.dgw.clinicai.billing.application.port.out.EventPublisher;
import com.dgw.clinicai.billing.domain.model.events.InvoiceIssuedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String INVOICES_ISSUED_TOPIC = "billing.invoices.issued";

    @Override
    public void publish(Object event) {
        if (event instanceof InvoiceIssuedEvent) {
            log.info("Publishing InvoiceIssuedEvent for invoice ID: {}", ((InvoiceIssuedEvent) event).invoiceId());
            kafkaTemplate.send(INVOICES_ISSUED_TOPIC, event);
        }
    }
}