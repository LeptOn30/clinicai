package com.dgw.clinicai.scheduling.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class AppointmentEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID patientId;

    @Column(nullable = false)
    private UUID providerId;

    private LocalDateTime appointmentTime;
    private String status;
}