package com.dgw.clinicai.scheduling.infrastructure.adapter.out.messaging;

import com.dgw.clinicai.scheduling.application.port.out.EventPublisher;
import com.dgw.clinicai.scheduling.domain.model.events.AppointmentCompletedEvent;
import com.dgw.clinicai.scheduling.domain.model.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;
    private static final String APPOINTMENTS_COMPLETED_TOPIC = "scheduling.appointments.completed";

    @Override
    public void publish(DomainEvent event) {
        if (event instanceof AppointmentCompletedEvent) {
            kafkaTemplate.send(APPOINTMENTS_COMPLETED_TOPIC, event);
        }
    }
}