package com.dgw.clinicai.scheduling.application.port.in;

import java.util.UUID;

public interface CompleteAppointmentUseCase {
    void completeAppointment(CompleteAppointmentCommand command);
}