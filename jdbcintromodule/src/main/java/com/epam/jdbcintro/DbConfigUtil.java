package com.epam.jdbcintro;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfigUtil {

    private Properties properties = new Properties();

    public DbConfigUtil() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jdbc.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getURL() {
        return properties.getProperty("jdbc.url");
    }

    public String getUserName() {
        return properties.getProperty("jdbc.username");
    }

    public String getPassword() {
        return properties.getProperty("jdbc.password");
    }

}
