package com.dgw.clinicai.billing.application.port.out;

import com.dgw.clinicai.billing.domain.model.events.DomainEvent;

public interface EventPublisher {
    void publish(Object event);
}