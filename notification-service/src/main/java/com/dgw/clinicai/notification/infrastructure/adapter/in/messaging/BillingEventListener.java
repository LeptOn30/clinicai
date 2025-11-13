package com.dgw.clinicai.notification.infrastructure.adapter.in.messaging;

import com.dgw.clinicai.notification.application.port.in.SendNotificationUseCase;
import com.dgw.clinicai.notification.domain.model.NotificationRequest;
import com.dgw.clinicai.notification.infrastructure.adapter.in.messaging.dto.InvoiceIssuedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BillingEventListener {

    private final SendNotificationUseCase sendNotificationUseCase;

    @KafkaListener(topics = "billing.invoices.issued", groupId = "notification_service", containerFactory = "invoiceIssuedEventKafkaListenerContainerFactory")
    public void handleInvoiceIssuedEvent(@Payload InvoiceIssuedEvent event) {
        log.info("Received InvoiceIssuedEvent for invoice ID: {}", event.invoiceId());

        // In a real system, you would look up the patient's email using the patientId.
        // For this example, we'll use a placeholder.
        String patientEmail = "patient-placeholder@example.com";

        String subject = "New Invoice Issued: " + event.invoiceId();
        String body = String.format(
                "Dear Patient,\n\nA new invoice has been issued for you.\n\nAmount: $%.2f\nDue Date: %s\n\nThank you,\nClinic Administration",
                event.totalAmount(),
                event.dueDate()
        );

        NotificationRequest request = new NotificationRequest(patientEmail, subject, body);
        sendNotificationUseCase.sendNotification(request);
    }
}