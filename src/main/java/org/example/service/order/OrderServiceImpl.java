package org.example.service.order;

import lombok.RequiredArgsConstructor;
import org.example.dao.order.OrderDao;
import org.example.dao.product.ProductDao;
import org.example.domain.dto.order.OrderCreateDto;
import org.example.domain.dto.order.OrderReadDto;
import org.example.domain.dto.product.ProductReadDto;
import org.example.domain.entity.order.OrderEntity;
import org.example.domain.entity.order.OrderStatus;
import org.example.domain.entity.product.ProductEntity;
import org.example.domain.response.BaseResponse;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderDao orderDao;

    private final ModelMapper modelMapper;

    private final ProductDao productDao;

    @Override
    public BaseResponse<OrderReadDto> create(OrderCreateDto orderCreateDto) {
        for (OrderEntity order : orderDao.findByUserId(orderCreateDto.getCustomerId())) {
            if (orderDao.findByProductId(orderCreateDto.getProductId()).isPresent() && order.getId().equals(orderDao.findByProductId(orderCreateDto.getProductId()).get().getId())){
                order.setAmount(order.getAmount() + orderCreateDto.getAmount());
                orderDao.update(order);
                return new BaseResponse<>("Order Successfully updated!", 201, modelMapper.map(order, OrderReadDto.class));
            }
        }
        OrderEntity orderEntity = modelMapper.map(orderCreateDto, OrderEntity.class);
        orderDao.create(orderEntity);
        return new BaseResponse<>("Order Successfully added!", 200, modelMapper.map(orderEntity, OrderReadDto.class));
    }

    @Override
    public OrderReadDto getById(UUID id) {
        return modelMapper.map(orderDao.findById(id), OrderReadDto.class);
    }

    @Override
    public int delete(UUID id) {
        return orderDao.deleteById(id);
    }

    @Override
    public List<OrderReadDto> getAll() {
        List<OrderEntity> all = orderDao.getAll();
        return modelMapper.map(all, new TypeToken<List<OrderReadDto>>() {}.getType());
    }

    @Override
    public Optional<OrderEntity> findByProductId(UUID productId) {
        OrderEntity orderEntity = orderDao.findByProductId(productId).get();
        return Optional.of(orderEntity);
    }

    @Override
    public List<OrderEntity> findByUserId(UUID userId) {
        List<OrderEntity> all = orderDao.findByUserId(userId);
        return modelMapper.map(all, new TypeToken<List<OrderReadDto>>() {}.getType());
    }

    @Override
    public List<OrderEntity> findBySeller(String seller) {
        List<OrderEntity> findBySellerId = new ArrayList<>();
        for (OrderEntity orderEntity : orderDao.getAll()) {
            if (productDao.findById(orderEntity.getProductId()).isPresent() && productDao.findById(orderEntity.getProductId()).get().getMaker().equals(seller)){
                findBySellerId.add(orderEntity);
            }
        }
        return findBySellerId;
    }

    @Override
    public void deleteAllMyOrders(UUID userId) {
        orderDao.deleteAllByUserId(userId);
    }

    @Override
    public OrderEntity update(OrderReadDto order, int amount) {
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        orderEntity.setAmount(amount);
        return orderDao.update(orderEntity);
    }

    @Override
    public OrderEntity updateStatus(OrderReadDto order, OrderStatus status) {
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        orderEntity.setStatus(status);
        return orderDao.update(orderEntity);
    }
}
