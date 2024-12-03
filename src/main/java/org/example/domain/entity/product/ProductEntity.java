package org.example.domain.entity.product;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.example.domain.entity.BaseEntity;

@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
@Setter
public class ProductEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "name cannot be empty or null")
    @NonNull
    private String name;

    private String description;

    private String maker;

    @Column(nullable = false)
    @NonNull
    @Positive
    private Double price;

    @Enumerated(EnumType.STRING)
    ProductCategory category;
}
