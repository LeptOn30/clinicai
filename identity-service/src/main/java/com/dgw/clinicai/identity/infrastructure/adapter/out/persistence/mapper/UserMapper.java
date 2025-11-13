package com.dgw.clinicai.identity.infrastructure.adapter.out.persistence.mapper;

import com.dgw.clinicai.identity.domain.model.User;
import com.dgw.clinicai.identity.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id.value", target = "id")
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}