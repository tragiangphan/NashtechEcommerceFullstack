package com.nashtech.rookies.ecommerce.services;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class CommonServiceImpl<T, ID> implements CommonService<T, ID> {
  private final CrudRepository<T, ID> repository;

  protected CommonServiceImpl(CrudRepository<T, ID> repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public T save(T entity) {
    this.repository.save(entity);
    return entity;
  }

  @Override
  public Optional<T> findOne(ID pk) {
    return this.repository.findById(pk);
  }

  @Override
  public Iterable<T> findAll() {
    return this.repository.findAll();
  }

  @Override
  public void delete(ID id) {
    this.repository.deleteById(id);
  }
}
