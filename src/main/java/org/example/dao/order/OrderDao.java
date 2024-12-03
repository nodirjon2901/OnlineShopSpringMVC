package org.example.dao.order;

import org.example.dao.BaseDao;
import org.example.domain.entity.order.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderDao extends BaseDao<OrderEntity, UUID> {
    Optional<OrderEntity> findByProductId(UUID productId);
    List<OrderEntity> findByUserId(UUID userId);
    void deleteAllByUserId(UUID userId);
}
