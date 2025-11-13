package com.dgw.clinicai.patient.infrastructure.adapter.out.persistence;

import com.dgw.clinicai.patient.application.port.out.PatientRepository;
import com.dgw.clinicai.patient.domain.model.Patient;
import com.dgw.clinicai.patient.domain.model.PatientId;
import com.dgw.clinicai.patient.infrastructure.adapter.out.persistence.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaPatientRepository implements PatientRepository {

    private final SpringPatientRepository springRepo;
    private final PatientMapper mapper;

    @Override
    public PatientId nextId() {
        return PatientId.from(UUID.randomUUID());
    }

    @Override
    public void save(Patient patient) {
        springRepo.save(mapper.toEntity(patient));
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return springRepo.findByContactInfo_Email(email).map(mapper::toDomain);
    }
}