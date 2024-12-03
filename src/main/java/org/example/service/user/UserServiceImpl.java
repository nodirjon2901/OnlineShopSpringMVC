package org.example.service.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.example.dao.user.UserDao;
import org.example.domain.dto.user.UserCreateDto;
import org.example.domain.dto.user.UserReadDto;
import org.example.domain.entity.user.UserEntity;
import org.example.domain.response.BaseResponse;
import org.example.exception.DataNotFoundException;
import org.example.exception.OnlineShopException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final ModelMapper modelMapper;


    @Override
    public BaseResponse<UserReadDto> create(UserCreateDto userCreateDto) {
        UserEntity userEntity = modelMapper.map(userCreateDto, UserEntity.class);
        try {
            userEntity = userDao.create(userEntity);
            return new BaseResponse<>("Successfully created", 200, modelMapper.map(userEntity, UserReadDto.class));
        } catch (Exception e) {
            return new BaseResponse<>("Failed creating new user!", 400, null);
        }

    }

    @Override
    public UserReadDto getById(UUID id) {
        return modelMapper.map(userDao.findById(id), UserReadDto.class);
    }

    @Override
    public int delete(UUID id) {
        return 0;
    }

    @Override
    public BaseResponse<UserReadDto> signIn(String email, String password) {
        try {
            Optional<UserEntity> optionalUser = userDao.findByEmail(email);
            if (Objects.equals(optionalUser.get().getPassword(), password)){
                return new BaseResponse<>("Successfully logged in!", 200, modelMapper.map(optionalUser.get(), UserReadDto.class));
            }else {
                return new BaseResponse<>("Failed to login check email and password!", 400, null);
            }
        } catch (Exception e){
            return new BaseResponse<>("Failed to login check email and password!", 400, null);
        }
    }

    @Override
    public List<UserReadDto> getAll() {
        List<UserEntity> all = userDao.getAll();
        return modelMapper.map(all, new TypeToken<List<UserReadDto>>() {}.getType());
    }
}
