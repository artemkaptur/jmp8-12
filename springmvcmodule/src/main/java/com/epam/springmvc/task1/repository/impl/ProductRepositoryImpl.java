package com.epam.springmvc.task1.repository.impl;

import com.epam.springmvc.task1.repository.ProductRepository;
import com.epam.springmvc.task1.domain.Product;
import com.epam.springmvc.task1.repository.rowMapper.GoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.Types;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private JdbcTemplate jdbcTemplate;
    private GoodMapper goodMapper;

    private static final String SQL_SELECT_ALL = "SELECT id, name, picture, delivery_status FROM goods";
    private static final String SQL_SELECT_BY_ID = "SELECT id, name, picture, delivery_status FROM goods WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM goods WHERE id=?";
    private static final String SQL_INSERT = "INSERT INTO goods (id, name, delivery_status, picture) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE goods SET name = ?, delivery_status = ?, picture = ? WHERE id= ?";
    private static final String SQL_IS_PRODUCT_EXIST = "SELECT count(*) FROM goods WHERE name = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id, name, picture, delivery_status FROM goods WHERE name = ?";
    private static final String SQL_PRODUCT_COUNT = "SELECT count(*) FROM goods";

    @Autowired
    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.goodMapper = new GoodMapper();
    }

    public List<Product> findAllByPage(int pageId, int total) {
        String sql = "SELECT id, name, picture, delivery_status FROM goods ORDER BY name ASC LIMIT " + (pageId - 1) + "," + total;
        return jdbcTemplate.query(sql, goodMapper);
    }

    public boolean add(Product entity) {
        int status = 0;
        if (entity.getDeliveryStatus()) {
            status = 1;
        }
        return jdbcTemplate.update(SQL_INSERT, new Object[]{
                entity.getId(),
                entity.getName(),
                status,
                new SqlLobValue(new ByteArrayInputStream(entity.getImage()),
                        entity.getImage().length, new DefaultLobHandler()),
        }, new int[]{Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.BLOB}) > 0;
    }

    public Product findById(Integer primaryKey) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, goodMapper, primaryKey);
    }

    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, goodMapper);
    }

    @Override
    public Product findGoodByName(String name) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, goodMapper, name);
    }

    @Override
    public boolean isExist(String name) {
        return jdbcTemplate.queryForObject(SQL_IS_PRODUCT_EXIST, new Object[]{name}, Integer.class) > 0;
    }

    public boolean update(Product entity) {
        int status = 0;
        if (entity.getDeliveryStatus()) {
            status = 1;
        }
        return jdbcTemplate.update(SQL_UPDATE, new Object[]{
                entity.getName(),
                status,
                new SqlLobValue(new ByteArrayInputStream(entity.getImage()),
                        entity.getImage().length, new DefaultLobHandler()),
                entity.getId(),
        }, new int[]{Types.VARCHAR, Types.INTEGER, Types.BLOB, Types.INTEGER}) > 0;
    }

    public boolean delete(Integer primaryKey) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, primaryKey) > 0;
    }

    @Override
    public int productCount() {
        return jdbcTemplate.queryForObject(SQL_PRODUCT_COUNT, Integer.class);
    }

}
