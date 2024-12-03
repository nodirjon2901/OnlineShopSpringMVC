package org.example.dao.user;

import org.example.dao.BaseDao;
import org.example.domain.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends BaseDao<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}
