package com.epam.springboot.task1.repository.rowMapper;

import com.epam.springboot.task1.domain.Good;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodMapper implements RowMapper<Good> {

    @Override
    public Good mapRow(ResultSet resultSet, int i) throws SQLException {
        Good good = new Good();
        good.setId(resultSet.getInt("id"));
        good.setName(resultSet.getString("name"));
        good.setImage(resultSet.getBytes("picture"));
        good.setDeliveryStatus(resultSet.getBoolean("delivery_status"));
        return good;
    }

}
