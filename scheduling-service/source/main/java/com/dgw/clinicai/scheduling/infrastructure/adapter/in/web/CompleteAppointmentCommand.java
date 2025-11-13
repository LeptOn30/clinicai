package com.dgw.clinicai.scheduling.infrastructure.adapter.in.web;

import java.math.BigDecimal;
import java.util.List;

/** A DTO to wrap the list for cleaner JSON in the request body */
public record CompleteAppointmentCommand(List<ServiceCode> performedServices) {
    public record ServiceCode(String code, String description, BigDecimal standardFee) {
    }
    public record ServiceCodeList(List<ServiceCode> performedServices) {}
}