package org.example.service.product;

import org.example.domain.dto.product.ProductCreateDto;
import org.example.domain.dto.product.ProductReadDto;
import org.example.domain.entity.product.ProductCategory;
import org.example.domain.entity.product.ProductEntity;
import org.example.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public interface ProductService extends BaseService<ProductCreateDto, ProductReadDto> {
    List<ProductReadDto> findByCategory(ProductCategory category);
    List<ProductReadDto> findMyProducts(String seller);
    ProductEntity update(ProductEntity productEntity);
    HashMap<UUID, ProductEntity> productById();
}
