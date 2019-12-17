package com.epam.springboot.task1.repository;

import com.epam.springboot.task1.domain.Order;

import java.util.List;

public interface OrderRepository extends AppRepository<Order, Integer> {

    List<Order> findByUserId(Integer primaryKey);

}
