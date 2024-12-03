package org.example.service.product;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.example.dao.product.ProductDao;
import org.example.domain.dto.product.ProductCreateDto;
import org.example.domain.dto.product.ProductReadDto;
import org.example.domain.entity.product.ProductCategory;
import org.example.domain.entity.product.ProductEntity;
import org.example.domain.response.BaseResponse;
import org.example.exception.OnlineShopException;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductDao productDao;

    private final ModelMapper modelMapper;

    @Override
    public BaseResponse<ProductReadDto> create(ProductCreateDto productCreateDto) {
        ProductEntity product = modelMapper.map(productCreateDto, ProductEntity.class);
        try {
            product = productDao.create(product);
            return new BaseResponse<>("Successfully created!", 200, modelMapper.map(product, ProductReadDto.class));
        }catch (Exception e){
            return new BaseResponse<>("Failed creating new product!", 400, null);
        }
    }

    @Override
    public ProductReadDto getById(UUID id) {
        return modelMapper.map(productDao.findById(id), ProductReadDto.class);
    }

    @Override
    public int delete(UUID id) {
        return productDao.deleteById(id);
    }

    @Override
    public List<ProductReadDto> getAll() {
        List<ProductEntity> all = productDao.getAll();
        return modelMapper.map(all, new TypeToken<List<ProductReadDto>>() {}.getType());
    }

    @Override
    public List<ProductReadDto> findByCategory(ProductCategory category) {
        List<ProductEntity> byCategory = productDao.findByCategory(category);
        return modelMapper.map(byCategory, new TypeToken<List<ProductReadDto>>() {}.getType());
    }

    @Override
    public List<ProductReadDto> findMyProducts(String seller) {
        List<ProductEntity> myProducts = productDao.findMyProducts(seller);
        return modelMapper.map(myProducts, new TypeToken<List<ProductReadDto>>() {}.getType());
    }

    @Override
    public ProductEntity update(ProductEntity productEntity) {
        return productDao.update(productEntity);
    }

    @Override
    public HashMap<UUID, ProductEntity> productById() {
        HashMap<UUID, ProductEntity> productById = new HashMap<>();
        for (ProductEntity product : productDao.getAll()) {
            productById.put(product.getId(), product);
        }
        return productById;
    }
}
