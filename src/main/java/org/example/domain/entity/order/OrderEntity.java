package org.example.domain.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.example.domain.entity.BaseEntity;
import org.example.domain.entity.product.ProductCategory;

import java.util.UUID;
@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
@Setter
public class OrderEntity extends BaseEntity {

    @Column(nullable = false)
    @NonNull
    private UUID customerId;


    @Column(nullable = false)
    @NonNull
    private UUID productId;


    @Column(nullable = false)
    @Positive
    private int amount;
    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
