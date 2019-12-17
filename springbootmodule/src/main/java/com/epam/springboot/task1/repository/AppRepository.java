package com.epam.springboot.task1.repository;

import com.epam.springboot.task1.domain.AppEntity;

import java.util.List;

public interface AppRepository<T extends AppEntity, ID> {

    boolean add(T entity);

    T findById(ID primaryKey);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(ID primaryKey);

}
