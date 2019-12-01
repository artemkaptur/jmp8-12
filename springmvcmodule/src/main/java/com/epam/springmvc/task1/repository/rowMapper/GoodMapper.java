package com.epam.springmvc.task1.repository.rowMapper;

import com.epam.springmvc.task1.domain.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setImage(resultSet.getBytes("picture"));
        product.setDeliveryStatus(resultSet.getBoolean("delivery_status"));
        return product;
    }
}
