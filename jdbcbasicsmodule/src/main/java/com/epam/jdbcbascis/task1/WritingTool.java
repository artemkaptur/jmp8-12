package com.epam.jdbcbascis.task1;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class WritingTool implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(WritingTool.class);

    private final Properties properties;

    WritingTool(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void run() {
        createTables();
    }

    private void createTables() {
        int numberOfTables = RandomUtils.nextInt(1, 11);
        for (int i = 0; i < numberOfTables; i++) {
            StringBuilder tableName = new StringBuilder()
                    .append(Thread.currentThread().getName().toLowerCase().replace("-", ""))
                    .append("_").append(i);
            int numberOfColumns = RandomUtils.nextInt(1, 21);
            int[] types = getRandomTypes(numberOfColumns);
            String createTableQuery = buildCreateTableQuery(tableName.toString(), types);
            executeQuery(properties, createTableQuery);
            LOGGER.info("Created table " + tableName);
            int numberOfRows = RandomUtils.nextInt(1, 101);
            for (int j = 0; j <= numberOfRows / 20; j++) {
                List<String> queriesForBatch = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    queriesForBatch.add(buildPopulateTableQuery(tableName.toString(), types));
                }
                executeBatchQuery(properties, queriesForBatch);
            }
        }
    }

    private String buildCreateTableQuery(String tableName, int[] type) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS table" + tableName;
        StringBuilder createColumnSqlQuery = new StringBuilder("(");
        for (int i = 0; i < type.length; i++) {
            createColumnSqlQuery.append("column").append(i).append(" ").append(ColumnType.getColumnType(type[i]).getType()).append(", ");
        }
        createColumnSqlQuery.delete(createColumnSqlQuery.length() - 2, createColumnSqlQuery.length());
        createColumnSqlQuery.append(")");
        return createTableSql + createColumnSqlQuery.toString();
    }

    private String buildPopulateTableQuery(String tableName, int[] type) {
        String populateTableSql = "INSERT INTO table" + tableName;
        StringBuilder populateColumnNameSql = new StringBuilder("(");
        StringBuilder populateColumnValueSql = new StringBuilder(" VALUES (");
        for (int i = 0; i < type.length; i++) {
            populateColumnNameSql.append("column").append(i).append(", ");
            populateColumnValueSql.append(ColumnType.getColumnType(type[i]).getValue()).append(", ");
        }
        populateColumnNameSql.delete(populateColumnNameSql.length() - 2, populateColumnNameSql.length());
        populateColumnValueSql.delete(populateColumnValueSql.length() - 2, populateColumnValueSql.length());
        populateColumnValueSql.append(")");
        populateColumnNameSql.append(")").append(populateColumnValueSql);
        return populateTableSql + populateColumnNameSql.toString();
    }

    private void executeQuery(Properties properties, String query) {
        try (Connection connection = getConnection(properties);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(query);
            connection.commit();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    private void executeBatchQuery(Properties properties, List<String> queries) {
        try (Connection connection = getConnection(properties);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            for (String query : queries) {
                statement.addBatch(query);
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    private int[] getRandomTypes(int numberOfColumns) {
        int[] types = new int[numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
            int randomType = RandomUtils.nextInt(1, 3);
            types[i] = randomType;
        }
        return types;
    }

    private Connection getConnection(Properties properties) throws SQLException {
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }

}
