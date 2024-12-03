package org.example.dao.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.domain.entity.product.ProductCategory;
import org.example.domain.entity.product.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductDaoImpl implements ProductDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ProductEntity create(ProductEntity productEntity) {
        entityManager.persist(productEntity);
        return productEntity;
    }

    @Override
    @Transactional
    public ProductEntity update(ProductEntity productEntity) {
        return entityManager.merge(productEntity);
    }

    @Override
    public Optional<ProductEntity> findById(UUID uuid) {
        ProductEntity product = entityManager.find(ProductEntity.class, uuid);
        return Optional.ofNullable(product);
    }

    @Override
    public List<ProductEntity> getAll() {
        return entityManager.createQuery("select p from products p", ProductEntity.class)
                .getResultList();
    }

    @Override
    @Transactional
    public int deleteById(UUID uuid) {
        return entityManager.createQuery("delete from products where id =:id")
                .setParameter("id", uuid)
                .executeUpdate();
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        ProductEntity product = entityManager.createQuery("select p from products p where name=:name", ProductEntity.class)
                .setParameter("name", name)
                .getSingleResult();
        return Optional.of(product);
    }

    @Override
    public List<ProductEntity> findByCategory(ProductCategory category) {
        return entityManager.createQuery("select p from products p where p.category=:category", ProductEntity.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public List<ProductEntity> findMyProducts(String seller) {
        return entityManager.createQuery("select p from products p where p.maker=:seller", ProductEntity.class)
                .setParameter("seller", seller)
                .getResultList();
    }
}
