package com.dgw.clinicai.patient.infrastructure.adapter.out.persistence;

import com.dgw.clinicai.patient.infrastructure.adapter.out.persistence.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringPatientRepository extends JpaRepository<PatientEntity, UUID> {
    Optional<PatientEntity> findByContactInfo_Email(String email);
}