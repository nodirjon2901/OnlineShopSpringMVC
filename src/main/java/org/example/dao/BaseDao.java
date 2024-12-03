package org.example.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseDao<E, ID> {
    E create(E e);
    E update(E e);
    Optional<E> findById(ID id);
    List<E> getAll();
    int deleteById(ID id);
}
