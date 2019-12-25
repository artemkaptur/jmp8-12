package com.epam.jdbcintro;

import com.epam.jdbcintro.task1.MyFirstConnection;

public class Main {

    public static void main(String[] args) {
        // Task 1:
        MyFirstConnection firstConnection = MyFirstConnection.getInstance();
        firstConnection.executeStatement("INSERT INTO task1 VALUES (default, 'Tom')");
        firstConnection.printData();
        firstConnection.executeStatement("INSERT INTO task1 VALUES (default, 'Kate')");
        firstConnection.printData();
        firstConnection.printTables();
        firstConnection.executePreparedStatement("INSERT INTO task1 VALUES (default, ?)", "Billie");
        firstConnection.printData();
    }

}
