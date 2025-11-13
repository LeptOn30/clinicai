package com.dgw.clinicai.scheduling.domain.model;

import com.dgw.clinicai.scheduling.domain.model.events.AppointmentCompletedEvent;
import com.dgw.clinicai.scheduling.domain.model.events.DomainEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Appointment {
    private final AppointmentId id;
    private final UUID patientId;
    private final UUID providerId;
    private final LocalDateTime appointmentTime;
    private AppointmentStatus status;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    private Appointment(AppointmentId id, UUID patientId, UUID providerId, LocalDateTime appointmentTime) {
        this.id = id;
        this.patientId = patientId;
        this.providerId = providerId;
        this.appointmentTime = appointmentTime;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public static Appointment schedule(AppointmentId id, UUID patientId, UUID providerId, LocalDateTime appointmentTime) {
        // In a real system, you would check provider availability, patient status, etc.
        return new Appointment(id, patientId, providerId, appointmentTime);
    }

    public void complete(List<AppointmentCompletedEvent.ServiceCode> performedServices) {
        if (this.status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Only a SCHEDULED appointment can be completed.");
        }
        this.status = AppointmentStatus.COMPLETED;
        this.registerEvent(new AppointmentCompletedEvent(
                this.id.getValue(),
                this.patientId,
                this.providerId,
                performedServices
        ));
    }

    public void cancel() {
        if (this.status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Only a SCHEDULED appointment can be canceled.");
        }
        this.status = AppointmentStatus.CANCELLED;
        // Optionally register an AppointmentCancelledEvent
    }

    private void registerEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }
}