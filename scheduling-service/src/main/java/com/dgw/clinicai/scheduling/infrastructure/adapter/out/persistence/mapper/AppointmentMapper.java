package com.dgw.clinicai.scheduling.infrastructure.adapter.out.persistence.mapper;

import com.dgw.clinicai.scheduling.domain.model.Appointment;
import com.dgw.clinicai.scheduling.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "id.value", target = "id")
    AppointmentEntity toEntity(Appointment appointment);

    Appointment toDomain(AppointmentEntity entity);
}