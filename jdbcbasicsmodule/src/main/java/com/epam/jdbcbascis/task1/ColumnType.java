package com.epam.jdbcbascis.task1;

import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Random;

public enum ColumnType {

    INT("INT") {
        @Override
        public String getValue() {
            return String.valueOf(RandomUtils.nextInt());
        }
    },
    VARCHAR("VARCHAR(255)") {
        @Override
        public String getValue() {
            return String.format("'text%s'", RandomUtils.nextInt());
        }
    };

    private static final List<ColumnType> VALUES = List.of(values());
    private final String type;

    public abstract String getValue();

    ColumnType(String type) {
        this.type = type;
    }

    public static ColumnType getColumnType(int i) {
        return VALUES.get(i - 1);
    }

    public String getType() {
        return type;
    }

}
