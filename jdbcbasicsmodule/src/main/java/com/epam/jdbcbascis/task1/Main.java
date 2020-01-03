package com.epam.jdbcbascis.task1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = loadProps();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new WritingTool(properties));
            t.start();
        }
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
