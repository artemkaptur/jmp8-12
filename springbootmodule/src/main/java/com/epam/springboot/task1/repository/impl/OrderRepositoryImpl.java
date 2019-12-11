package com.epam.springboot.task1.repository.impl;

import com.epam.springboot.task1.repository.OrderRepository;
import com.epam.springboot.task1.domain.Order;
import com.epam.springboot.task1.repository.rowMapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private JdbcTemplate jdbcTemplate;
    private OrderMapper orderMapper;

    private static final String SQL_SELECT_ALL = "SELECT id, user_id FROM orders";
    private static final String SQL_SELECT_BY_ID = "SELECT id, user_id FROM orders WHERE id = ?";
    private static final String SQL_SELECT_BY_USER_ID = "SELECT id, user_id FROM orders WHERE user_id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM orders WHERE id=?";
    private static final String SQL_INSERT = "INSERT INTO orders (id, user_id) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE orders SET user_id = ? WHERE id= ?";

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderMapper = new OrderMapper();
    }

    public boolean add(Order entity) {
        return jdbcTemplate.update(SQL_INSERT, entity.getId(), entity.getUserId()) > 0;
    }

    public Order findById(Integer primaryKey) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, orderMapper, primaryKey);
    }

    public List<Order> findByUserId(Integer primaryKey) {
        return jdbcTemplate.query(SQL_SELECT_BY_USER_ID, orderMapper, primaryKey);
    }

    public List<Order> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, orderMapper);
    }

    public boolean update(Order entity) {
        return jdbcTemplate.update(SQL_UPDATE, entity.getUserId(), entity.getId()) > 0;
    }

    public boolean delete(Integer primaryKey) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, primaryKey) > 0;
    }

}
