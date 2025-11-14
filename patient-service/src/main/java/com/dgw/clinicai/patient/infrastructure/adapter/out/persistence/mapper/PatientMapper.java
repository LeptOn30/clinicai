package com.dgw.clinicai.patient.infrastructure.adapter.out.persistence.mapper;

import com.dgw.clinicai.patient.domain.model.Patient;
import com.dgw.clinicai.patient.infrastructure.adapter.out.persistence.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "id.value", target = "id")
    PatientEntity toEntity(Patient patient);

    Patient toDomain(PatientEntity entity);
}
