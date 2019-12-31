package com.epam.jdbcbascis.task2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    // args[0] - source db, args[1] - target db
    public static void main(String[] args) {
        Properties properties = loadProps();
        CopyTool copyTool = new CopyTool(properties, args[0], args[1]);
        copyTool.copyDatabase();
    }

    private static Properties loadProps() {
        Properties properties = new Properties();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
