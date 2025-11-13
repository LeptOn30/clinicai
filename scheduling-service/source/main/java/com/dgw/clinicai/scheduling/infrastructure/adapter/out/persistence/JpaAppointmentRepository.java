package com.dgw.clinicai.scheduling.infrastructure.adapter.out.persistence;

import com.dgw.clinicai.scheduling.application.port.out.AppointmentRepository;
import com.dgw.clinicai.scheduling.domain.model.Appointment;
import com.dgw.clinicai.scheduling.domain.model.AppointmentId;
import com.dgw.clinicai.scheduling.infrastructure.adapter.out.persistence.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaAppointmentRepository implements AppointmentRepository {

    private final SpringAppointmentRepository springRepo;
    private final AppointmentMapper mapper;

    @Override
    public AppointmentId nextId() {
        return AppointmentId.from(UUID.randomUUID());
    }

    @Override
    public void save(Appointment appointment) {
        springRepo.save(mapper.toEntity(appointment));
    }

    @Override
    public Optional<Appointment> findById(AppointmentId appointmentId) {
        return springRepo.findById(appointmentId.getValue()).map(mapper::toDomain);
    }
}