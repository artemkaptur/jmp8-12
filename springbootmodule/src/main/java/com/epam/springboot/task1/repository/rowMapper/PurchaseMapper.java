package com.epam.springboot.task1.repository.rowMapper;

import com.epam.springboot.task1.domain.Good;
import com.epam.springboot.task1.domain.Purchase;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseMapper implements RowMapper<Purchase> {

    @Override
    public Purchase mapRow(ResultSet resultSet, int i) throws SQLException {
        Purchase purchase = new Purchase();
        purchase.setId(resultSet.getInt("id"));
        purchase.setUserId(resultSet.getInt("user_Id"));
        Good good = new Good();
        good.setId(resultSet.getInt("good_id"));
        good.setName(resultSet.getString("name"));
        good.setImage(resultSet.getBytes("picture"));
        good.setDeliveryStatus(resultSet.getBoolean("delivery_status"));
        purchase.getGoods().add(good);
        return purchase;
    }

}
