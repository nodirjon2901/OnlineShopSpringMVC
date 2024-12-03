package org.example.dao.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.domain.entity.order.OrderEntity;
import org.example.domain.entity.product.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderDaoImpl implements OrderDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public OrderEntity create(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
        return orderEntity;
    }

    @Override
    @Transactional
    public OrderEntity update(OrderEntity orderEntity) {
        return entityManager.merge(orderEntity);
    }

    @Override
    public Optional<OrderEntity> findById(UUID uuid) {
        OrderEntity order = entityManager.find(OrderEntity.class, uuid);
        return Optional.of(order);
    }

    @Override
    public List<OrderEntity> getAll() {
        return entityManager.createQuery("select o from orders o", OrderEntity.class)
                .getResultList();
    }

    @Override
    @Transactional
    public int deleteById(UUID uuid) {
        return entityManager.createQuery("delete from orders where id =:id")
                .setParameter("id", uuid)
                .executeUpdate();
    }

    @Override
    public Optional<OrderEntity> findByProductId(UUID productId) {
        try{
            OrderEntity order = entityManager.createQuery("select o from orders o where o.productId=:productId", OrderEntity.class)
                    .setParameter("productId", productId)
                    .getSingleResult();
            return Optional.of(order);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public List<OrderEntity> findByUserId(UUID userId) {
        return entityManager.createQuery("select o from orders o where o.customerId=:customerId and o.status='PROCESSING'", OrderEntity.class)
                .setParameter("customerId", userId)
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteAllByUserId(UUID userId) {
        entityManager.createQuery("delete from orders o where o.customerId=:userId and o.status='PROCESSING'")
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
