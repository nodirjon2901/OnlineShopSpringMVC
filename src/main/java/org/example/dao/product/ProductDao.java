package org.example.dao.product;

import org.example.dao.BaseDao;
import org.example.domain.entity.product.ProductCategory;
import org.example.domain.entity.product.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDao extends BaseDao<ProductEntity, UUID> {
    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findByCategory(ProductCategory category);
    List<ProductEntity> findMyProducts(String seller);
}
