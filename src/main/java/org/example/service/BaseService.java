package org.example.service;

import org.example.domain.response.BaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Tojiahmedov Nodirjon
 * @param <CD> This is Creation DTO
 * @param <RD> This is Read DTO
 */
@Service
public interface BaseService<CD, RD> {
    BaseResponse<RD> create(CD cd);

    RD getById(UUID id);

    int delete(UUID id);

    List<RD> getAll();
}
