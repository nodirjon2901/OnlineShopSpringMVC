package org.example.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.entity.product.ProductCategory;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateDto {
    String name;
    String description;
    String maker;
    Double price;
    String category;
}
