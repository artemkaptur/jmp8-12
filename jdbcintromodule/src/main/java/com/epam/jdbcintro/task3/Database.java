package com.epam.jdbcintro.task3;

import com.epam.jdbcintro.DbConfigUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

class Database {

    private static final Logger LOGGER = LogManager.getLogger(Database.class);

    private static DbConfigUtil dbConfigUtil = new DbConfigUtil();
    private static Connection connection;

    Database() {
        connectToDb();
    }

    private PreparedStatement getPreparedStatement(String sqlScript, Object... objects) {
        try {
            PreparedStatement statement = connection.prepareStatement(sqlScript);
            int parameterIndex = 1;
            for (Object object : objects) {
                statement.setObject(parameterIndex, object);
                parameterIndex++;
            }
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException("Error during statement building:\n", e);
        }
    }

    private static void connectToDb() {
        try {
            connection = DriverManager.getConnection(dbConfigUtil.getURL(), dbConfigUtil.getUserName(), dbConfigUtil.getPassword());
        } catch (SQLException e) {
            LOGGER.error("Error during connection:\n" + e);
        }
    }

    ResultSet select(String sqlScript, Object... objects) {
        try {
            return getPreparedStatement(sqlScript, objects).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute select query:\n", e);
        }
    }

    boolean update(String sqlScript, Object... objects) {
        try {
            return getPreparedStatement(sqlScript, objects).executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute update query:\n", e);
        }
    }

}
