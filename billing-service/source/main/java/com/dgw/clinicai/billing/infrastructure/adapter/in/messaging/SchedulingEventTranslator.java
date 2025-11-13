package com.dgw.clinicai.billing.infrastructure.adapter.in.messaging;

import com.dgw.clinicai.billing.application.port.in.CreateInvoiceCommand;
import com.dgw.clinicai.billing.infrastructure.adapter.in.messaging.dto.AppointmentCompletedEvent;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class SchedulingEventTranslator {

    public CreateInvoiceCommand toCreateInvoiceCommand(AppointmentCompletedEvent event) {
        if (event == null || event.patientId() == null || event.performedServices() == null) {
            throw new IllegalArgumentException("Received invalid AppointmentCompletedEvent data.");
        }

        var lineItems = event.performedServices().stream()
            .map(serviceCode -> new CreateInvoiceCommand.LineItem(
                serviceCode.code(),
                serviceCode.description(),
                serviceCode.standardFee()
            )).collect(Collectors.toList());

        return new CreateInvoiceCommand(event.patientId(), event.appointmentId(), lineItems);
    }
}