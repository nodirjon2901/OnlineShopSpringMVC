package org.example.domain.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.entity.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderReadDto {
    UUID id;
    private UUID customerId;
    private UUID productId;
    private int amount;
    private OrderStatus status;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
}
