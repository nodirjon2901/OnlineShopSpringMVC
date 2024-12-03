package org.example.dao.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.entity.user.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public UserEntity create(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    @Override
//    @Transactional
    public UserEntity update(UserEntity userEntity) {
        return entityManager.merge(userEntity);
    }

    @Override
    public Optional<UserEntity> findById(UUID uuid) {
        UserEntity user = entityManager.find(UserEntity.class, uuid);
        return Optional.of(user);
    }

    @Override
    public List<UserEntity> getAll() {
        return entityManager.createQuery("select u from users u", UserEntity.class).getResultList();
    }

    @Override
    @Transactional
    public int deleteById(UUID uuid) {
        return entityManager.createQuery("delete from users where id =:id")
                .setParameter("id", uuid)
                .executeUpdate();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        UserEntity user = entityManager
                .createQuery("select u from users u where email=:email", UserEntity.class)
                .setParameter("email", email)
                .getSingleResult();
        return Optional.of(user);
    }
}
