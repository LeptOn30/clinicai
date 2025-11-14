package com.dgw.clinicai.scheduling.application.port.out;

import com.dgw.clinicai.scheduling.domain.model.events.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}