package com.dgw.clinicai.billing.infrastructure.adapter.in.messaging;

import com.dgw.clinicai.billing.application.port.in.CreateInvoiceUseCase;
import com.dgw.clinicai.billing.infrastructure.adapter.in.messaging.dto.AppointmentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppointmentEventListener {

    private final CreateInvoiceUseCase createInvoiceUseCase;
    private final SchedulingEventTranslator translator;

    @KafkaListener(topics = "scheduling.appointments.completed", groupId = "billing_service")
    public void handleAppointmentCompleted(@Payload AppointmentCompletedEvent event) {
        log.info("Received AppointmentCompletedEvent for appointment ID: {}", event.appointmentId());

        try {
            var command = translator.toCreateInvoiceCommand(event);
            createInvoiceUseCase.createInvoiceFromAppointment(command);
            log.info("Successfully processed event and initiated invoice creation for appointment ID: {}", event.appointmentId());
        } catch (Exception e) {
            // In a real system, send to a dead-letter queue (DLQ)
            log.error("Failed to process AppointmentCompletedEvent for appointment ID: {}", event.appointmentId(), e);
        }
    }
}