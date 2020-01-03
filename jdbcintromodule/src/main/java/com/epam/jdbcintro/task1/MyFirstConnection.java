package com.epam.jdbcintro.task1;

import com.epam.jdbcintro.DbConfigUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class MyFirstConnection {

    private static final Logger LOGGER = LogManager.getLogger(MyFirstConnection.class);
    private static final MyFirstConnection INSTANCE = new MyFirstConnection();

    private static DbConfigUtil dbConfigUtil = new DbConfigUtil();
    private static Connection connection;

    private MyFirstConnection() {
    }

    private static void connectToDb() {
        try {
            connection = DriverManager.getConnection(dbConfigUtil.getURL(), dbConfigUtil.getUserName(), dbConfigUtil.getPassword());
        } catch (SQLException e) {
            LOGGER.error("Error during connection:\n" + e);
        }
    }

    private static void createSimpleTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS task1 (id INT NOT NULL AUTO_INCREMENT, text VARCHAR(50), PRIMARY KEY (id))");
        } catch (SQLException e) {
            LOGGER.error("Error during statement execution:\n" + e);
        }
    }

    public static MyFirstConnection getInstance() {
        if (connection == null) {
            connectToDb();
            createSimpleTable();
        }
        return INSTANCE;
    }

    public void executeStatement(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            LOGGER.error("Error during statement execution:\n" + e);
        }
    }

    public void executePreparedStatement(String query, String value) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Error during prepared statement execution:\n" + e);
        }
    }

    public void printTables() {
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SHOW TABLES");
            LOGGER.info("Tables:");
            while (result.next()) {
                LOGGER.info(result.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Error during printing tables:\n" + e);
        }
    }

    public void printData() {
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM task1");
            LOGGER.info("Table data:");
            while (result.next()) {
                LOGGER.info(result.getString(1) + ", " + result.getString(2));
            }
        } catch (SQLException e) {
            LOGGER.error("Error during printing table data:\n" + e);
        }
    }

}
