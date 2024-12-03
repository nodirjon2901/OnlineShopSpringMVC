package org.example.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.entity.product.ProductCategory;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductReadDto {
    UUID id;
    String name;
    String description;
    String maker;
    Double price;
    ProductCategory category;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
}
