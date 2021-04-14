package com.epam.esm.service.service;

public interface CrudService<T> {
    T create(T entity);

    T update(T entity);

    void delete(Long id);

    T findById(Long id);
}
