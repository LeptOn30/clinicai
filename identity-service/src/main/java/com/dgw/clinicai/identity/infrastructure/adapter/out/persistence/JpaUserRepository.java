package com.dgw.clinicai.identity.infrastructure.adapter.out.persistence;

import com.dgw.clinicai.identity.application.port.out.UserRepository;
import com.dgw.clinicai.identity.domain.model.User;
import com.dgw.clinicai.identity.domain.model.UserId;
import com.dgw.clinicai.identity.infrastructure.adapter.out.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final SpringUserRepository springRepo;
    private final UserMapper mapper;

    @Override
    public UserId nextId() {
        return UserId.from(UUID.randomUUID());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return springRepo.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public void save(User user) {
        UserEntity entity = mapper.toEntity(user);
        springRepo.save(entity);
    }
}