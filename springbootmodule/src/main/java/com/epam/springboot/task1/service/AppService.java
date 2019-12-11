package com.epam.springboot.task1.service;

import java.util.List;

public interface AppService<T, ID> {

    boolean add(T entity);

    T findById(ID primaryKey);

    List<T> findAll();

    void update(T entity);

    void delete(ID primaryKey);

}
