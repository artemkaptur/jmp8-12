package com.epam.jdbcbascis.task2;

import com.epam.jdbcbascis.task2.model.Column;
import com.epam.jdbcbascis.task2.model.Table;
import com.epam.jdbcbascis.task2.model.TypeInfo;

import java.sql.*;
import java.util.*;

class DatabaseContent {

    private Connection connection;
    private HashMap<String, TypeInfo> typeNameMap;

    DatabaseContent(Connection connection) {
        this.connection = connection;
    }

    List<String> getDbTableNames() throws SQLException {
        List<String> tableNames = new ArrayList<>();
        try (ResultSet tables = connection.getMetaData().getTables(null, null, "tablethread_%", new String[]{"TABLE"})) {
            while (tables.next()) {
                tableNames.add(tables.getString("TABLE_NAME"));
            }
        }
        return tableNames;
    }

    Table getTable(String tableName) throws SQLException {
        Table table = new Table();
        table.setName(tableName);
        table.setColumns(getTableColumns(tableName));
        return table;
    }

    void createTable(Table table) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("CREATE TABLE ").append(table.getName()).append("(");
        List<Column> columns = table.getColumns();
        Iterator<Column> columnIterator = columns.iterator();
        Column column = columnIterator.next();
        String columnName = column.getName();
        String columnType = column.getTypeInfo().getTypeName();
        queryBuilder.append(columnName).append(" ").append(columnType);
        if (column.getTypeInfo().getJdbcTypeCode() == Types.VARCHAR) {
            queryBuilder.append("(").append(column.getSize()).append(")");
        }
        while (columnIterator.hasNext()) {
            column = columnIterator.next();
            columnName = column.getName();
            columnType = column.getTypeInfo().getTypeName();
            queryBuilder.append(",");
            queryBuilder.append(columnName).append(" ").append(columnType);
            if (column.getTypeInfo().getJdbcTypeCode() == Types.VARCHAR) {
                queryBuilder.append("(").append(column.getSize()).append(")");
            }
        }
        queryBuilder.append(")");
        String sql = queryBuilder.toString();

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private List<Column> getTableColumns(String tableName) throws SQLException {
        List<Column> columns = new ArrayList<>();
        try (ResultSet result = connection.getMetaData().getColumns(null, null, tableName, "%")) {
            while (result.next()) {
                String name = result.getString("COLUMN_NAME");
                String typeName = result.getString("TYPE_NAME");
                int typeSize = result.getInt("COLUMN_SIZE");
                TypeInfo typeInfo = typeNameMap().get(typeName);
                columns.add(new Column(name, typeInfo, typeSize));
            }
            return columns;
        }
    }

    private HashSet<TypeInfo> getTypeInfo() throws SQLException {
        HashSet<TypeInfo> typeInfoSet = new HashSet<>();
        try (ResultSet result = connection.getMetaData().getTypeInfo()) {
            while (result.next()) {
                TypeInfo typeInfo = new TypeInfo();
                typeInfo.setTypeName(result.getString(1));
                typeInfo.setJdbcTypeCode(result.getInt(2));
                typeInfo.setPrecision(result.getInt(3));
                typeInfo.setLiteralPrefix(result.getString(4));
                typeInfo.setLiteralSuffix(result.getString(5));
                typeInfo.setCaseSensitive(result.getBoolean(8));
                typeInfo.setUnsigned(result.getBoolean(10));
                typeInfo.setFixedPrecisionScale(result.getBoolean(11));
                typeInfo.setAutoIncrement(result.getBoolean(12));
                typeInfo.setMinimumScale(result.getShort(14));
                typeInfo.setMaximumScale(result.getShort(15));
                typeInfoSet.add(typeInfo);
            }
        }
        return typeInfoSet;
    }

    private HashMap<String, TypeInfo> typeNameMap() throws SQLException {
        if (typeNameMap == null) {
            typeNameMap = new HashMap<>();
            for (TypeInfo typeInfo : getTypeInfo()) {
                typeNameMap.put(typeInfo.getTypeName(), typeInfo);
            }
        }
        return typeNameMap;
    }

}
