package com.epam.jdbcbascis.task2;

import com.epam.jdbcbascis.task2.model.Column;
import com.epam.jdbcbascis.task2.model.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

class CopyTool {

    private static final Logger LOGGER = LogManager.getLogger(CopyTool.class);

    private Properties properties;
    private String sourceDb;
    private String targetDb;

    CopyTool(Properties properties, String sourceDb, String targetDb) {
        this.properties = properties;
        this.sourceDb = sourceDb;
        this.targetDb = targetDb;
    }

    void copyDatabase() {
        try (Connection srcConnection = getConnection(sourceDb);
             Connection trgConnection = getConnection(targetDb)) {
            DatabaseContent sourceDbContent = new DatabaseContent(srcConnection);
            DatabaseContent targetDbContent = new DatabaseContent(trgConnection);
            List<String> tableNames = sourceDbContent.getDbTableNames();
            Collections.sort(tableNames);
            for (String tableName : tableNames) {
                Table table = sourceDbContent.getTable(tableName);
                targetDbContent.createTable(table);
                copyTableData(srcConnection, table);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    private void copyTableData(Connection connection, Table table) throws SQLException {
        List<Column> columns = table.getColumns();
        StringBuilder columnListBuilder = new StringBuilder(8 * columns.size());
        Iterator<Column> iterator = columns.iterator();
        columnListBuilder.append(iterator.next().getName());
        while (iterator.hasNext()) {
            columnListBuilder.append(",").append(iterator.next().getName());
        }
        String columnsList = columnListBuilder.toString();
        String sql = "INSERT INTO " + targetDb + "." + table.getName() +
                "(" + columnsList + ")" +
                "SELECT " + columnsList + " " +
                "FROM " + sourceDb + "." + table.getName();
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private Connection getConnection(String database) throws SQLException {
        String url = properties.getProperty("jdbc2.url") + database + "?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }

}
