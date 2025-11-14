package com.dgw.clinicai.scheduling.infrastructure.adapter.in.web;

import com.dgw.clinicai.scheduling.application.port.in.CompleteAppointmentCommand;
import com.dgw.clinicai.scheduling.application.port.in.CompleteAppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final CompleteAppointmentUseCase completeAppointmentUseCase;

    // In a real system, you'd have a POST to create an appointment.
    // This endpoint simulates completing an existing one.
    @PostMapping("/{appointmentId}/complete")
    public ResponseEntity<Void> completeAppointment(
            @PathVariable UUID appointmentId,
            @RequestBody CompleteAppointmentCommand.ServiceCodeList commandPayload) {
        completeAppointmentUseCase.completeAppointment(new CompleteAppointmentCommand(appointmentId, commandPayload.performedServices()));
        return ResponseEntity.ok().build();
    }
}