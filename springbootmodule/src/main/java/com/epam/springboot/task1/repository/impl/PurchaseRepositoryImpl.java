package com.epam.springboot.task1.repository.impl;

import com.epam.springboot.task1.domain.Good;
import com.epam.springboot.task1.repository.PurchaseRepository;
import com.epam.springboot.task1.domain.Purchase;
import com.epam.springboot.task1.repository.rowMapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {

    private JdbcTemplate jdbcTemplate;
    private PurchaseMapper purchaseMapper;

    private static final String SQL_SELECT_ALL = "SELECT p.id, p.user_id, pg.purchase_id, pg.good_id, g.id, g.name, g.picture, g.delivery_status FROM purchases p LEFT JOIN purchases_goods pg ON p.id = pg.purchase_id INNER JOIN goods g ON pg.good_id = g.id";
    private static final String SQL_SELECT_BY_ID = "SELECT p.id, p.user_id, pg.purchase_id, pg.good_id, g.id, g.name, g.picture, g.delivery_status FROM purchases p LEFT JOIN purchases_goods pg ON p.id = pg.purchase_id INNER JOIN goods g ON pg.good_id = g.id AND p.id =";
    private static final String SQL_SELECT_BY_USER_ID = "SELECT p.id, p.user_id, pg.purchase_id, pg.good_id, g.id, g.name, g.picture, g.delivery_status FROM purchases p LEFT JOIN purchases_goods pg ON p.id = pg.purchase_id INNER JOIN goods g ON pg.good_id = g.id AND p.user_id =";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM purchases WHERE id=?";
    private static final String SQL_INSERT = "INSERT INTO purchases (user_id) VALUES (?)";
    private static final String SQL_INSERT_PURCHASE_GOOD = "INSERT INTO purchases_goods (purchase_id, good_id) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE purchases SET user_id = ?  WHERE id= ?";

    @Autowired
    public PurchaseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.purchaseMapper = new PurchaseMapper();
    }

    @Transactional
    public boolean add(Purchase entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                    ps.setInt(1, entity.getUserId());
                    return ps;
                },
                keyHolder);
        Number purchaseId = keyHolder.getKey();
        final int batchSize = 20;
        jdbcTemplate.batchUpdate(SQL_INSERT_PURCHASE_GOOD,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        Good good = entity.getGoods().get(i);
                        ps.setInt(1, purchaseId.intValue());
                        ps.setInt(2, good.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        if (batchSize > entity.getGoods().size()) {
                            return entity.getGoods().size();
                        }
                        return batchSize;
                    }
                }
        );
        return result > 0;
    }

    public Purchase findById(Integer primaryKey) {
        String sql = SQL_SELECT_BY_ID + primaryKey;
        List<Purchase> purchases = jdbcTemplate.query(sql, purchaseMapper);
        for (Object obj : purchases) {
            return (Purchase) obj;
        }
        return null;
    }

    public List<Purchase> findByUserId(Integer userPrimaryKey) {
        String sql = SQL_SELECT_BY_USER_ID + userPrimaryKey;
        List<Purchase> purchases = jdbcTemplate.query(sql, purchaseMapper);
        return jdbcTemplate.query(sql, purchaseMapper);
    }

    public List<Purchase> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, purchaseMapper);
    }

    public boolean update(Purchase entity) {
        return jdbcTemplate.update(SQL_UPDATE, entity.getGoods(), entity.getId()) > 0;
    }

    public boolean delete(Integer primaryKey) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, primaryKey) > 0;
    }

}
