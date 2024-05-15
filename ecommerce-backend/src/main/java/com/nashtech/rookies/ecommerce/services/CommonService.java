package com.nashtech.rookies.ecommerce.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface CommonService<T, ID> {
  T save(T entity);

  Optional<T> findOne(ID pk);

  Iterable<T> findAll();

  void delete(ID id);
}
