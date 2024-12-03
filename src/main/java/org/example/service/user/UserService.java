package org.example.service.user;

import org.example.domain.dto.user.UserCreateDto;
import org.example.domain.dto.user.UserReadDto;
import org.example.domain.response.BaseResponse;
import org.example.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends BaseService<UserCreateDto, UserReadDto> {
    BaseResponse<UserReadDto> signIn(String email, String password);
}
