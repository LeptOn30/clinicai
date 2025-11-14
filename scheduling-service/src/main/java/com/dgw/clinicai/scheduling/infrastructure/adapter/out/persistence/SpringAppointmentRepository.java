package com.dgw.clinicai.scheduling.infrastructure.adapter.out.persistence;

import com.dgw.clinicai.scheduling.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringAppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {
}