package com.epam.esm.service.service;

import com.epam.esm.exception.ValidationException;

public interface CrudService<T> {
    T create(T entity) throws ValidationException;

    T update(T entity);

    void delete(Long id);

    T findById(Long id);
}
