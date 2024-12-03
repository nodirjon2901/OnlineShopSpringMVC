package org.example.service.order;

import org.example.domain.dto.order.OrderCreateDto;
import org.example.domain.dto.order.OrderReadDto;
import org.example.domain.entity.order.OrderEntity;
import org.example.domain.entity.order.OrderStatus;
import org.example.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface OrderService extends BaseService<OrderCreateDto, OrderReadDto> {
    Optional<OrderEntity> findByProductId(UUID productId);
    List<OrderEntity> findByUserId(UUID userId);
    List<OrderEntity> findBySeller(String seller);
    void deleteAllMyOrders(UUID userId);
    OrderEntity update(OrderReadDto order, int amount);
    OrderEntity updateStatus(OrderReadDto order, OrderStatus status);
}
