package com.epam.springboot.task1.repository.impl;

import com.epam.springboot.task1.repository.UserRepository;
import com.epam.springboot.task1.repository.rowMapper.UserMapper;
import com.epam.springboot.task1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private UserMapper userMapper;

    private static final String SQL_SELECT_ALL = "SELECT id, login, password, isAdmin FROM users";
    private static final String SQL_SELECT_BY_ID = "SELECT id, login, password, isAdmin FROM users WHERE id = ?";
    private static final String SQL_SELECT_BY_LOGIN = "SELECT id, login, password, isAdmin FROM users WHERE login = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id=?";
    private static final String SQL_INSERT = "INSERT INTO users (id, login, password, isAdmin) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE users SET login = ?, password = ?, isAdmin=?  WHERE id= ?";

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = new UserMapper();
    }

    public boolean add(User entity) {
        return jdbcTemplate.update(SQL_INSERT, entity.getId(), entity.getLogin(), entity.getPassword(), entity.getIsAdmin()) > 0;
    }

    public User findById(Integer primaryKey) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, userMapper, primaryKey);
    }

    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userMapper);
    }

    public boolean update(User entity) {
        return jdbcTemplate.update(SQL_UPDATE, entity.getLogin(), entity.getPassword(), entity.getId()) > 0;
    }

    public boolean delete(Integer primaryKey) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, primaryKey) > 0;
    }

    public User findUserByLogin(String login) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_LOGIN, userMapper, login);
    }

}
