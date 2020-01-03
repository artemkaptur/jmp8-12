package com.epam.jdbcintro.task3;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    private static final Logger LOGGER = LogManager.getLogger(DataGenerator.class);

    private static final long JANUARY_2010 = 1262334056000L;
    private static final long JANUARY_2011 = 1293870056000L;
    private static final long MAY_2023 = 1682925656000L;
    private static final long MARCH_2025 = 1740813656000L;
    private static final long APRIL_2025 = 1743492056000L;
    private static final long JUNE_2030 = 1906528856000L;

    private Database db = new Database();

    private void fillUserTable() {
        String[] names = new String[]{"Tom", "Kate", "Billie", "Valera", "Vasya", "Anton", "Veronika", "Sveta", "John", "Fiona"};
        String[] sureNames = new String[]{"Smith", "White", "Ivanov", "Putin", "Obama", "Carter", "Petrov", "Sidorov"};
        for (int i = 0; i <= 1000; i++) {
            db.update("INSERT INTO user (id, name, surname, birthdate) VALUES (?, ?, ?, ?);",
                    i,
                    names[RandomUtils.nextInt(0, names.length)],
                    sureNames[RandomUtils.nextInt(0, sureNames.length)],
                    getRandomDateInRange(JANUARY_2010, JANUARY_2011));
        }
    }

    private void fillFriendshipTable() {
        for (int i = 0; i <= 70000; i++) {
            db.update("INSERT INTO friendship (user_id_1, user_id_2, timestamp) VALUES (?, ?, ?);",
                    RandomUtils.nextInt(0, 1000),
                    RandomUtils.nextInt(0, 1000),
                    getRandomDateInRange(MAY_2023, JUNE_2030));
        }
    }

    private void fillPostTable() {
        for (int i = 0; i <= 50000; i++) {
            db.update("INSERT INTO post (id, user_id, text, timestamp) VALUES (?, ?, ?, ?);",
                    i,
                    RandomUtils.nextInt(0, 1000),
                    RandomStringUtils.random(11, true, false),
                    getRandomDateInRange(MAY_2023, JUNE_2030));
        }
    }

    private void fillThumbUpTable() {
        for (int i = 0; i <= 300000; i++) {
            db.update("INSERT INTO thumbup (post_id, user_id, timestamp) VALUES (?, ?, ?);",
                    RandomUtils.nextInt(0, 50000),
                    RandomUtils.nextInt(0, 1000),
                    getRandomDateInRange(MARCH_2025, APRIL_2025));
        }
    }

    private Date getRandomDateInRange(long startDateInMillis, long endDateInMillis) {
        return new Date(ThreadLocalRandom.current().nextLong(startDateInMillis, endDateInMillis));
    }

    public void createTables() {
        db.update("CREATE TABLE IF NOT EXISTS user (" +
                "id BIGINT NOT NULL PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "surname VARCHAR(100) NOT NULL," +
                "birthdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");");
        db.update("CREATE TABLE IF NOT EXISTS friendship (" +
                "user_id_1 BIGINT NOT NULL," +
                "user_id_2 BIGINT NOT NULL," +
                "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id_1) REFERENCES user (id) ON DELETE CASCADE," +
                "FOREIGN KEY (user_id_2) REFERENCES user (id) ON DELETE CASCADE" +
                ");");
        db.update("CREATE TABLE IF NOT EXISTS post (" +
                "id BIGINT NOT NULL PRIMARY KEY," +
                "user_id BIGINT NOT NULL," +
                "text VARCHAR (10000) NOT NULL," +
                "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE" +
                ");");
        db.update("CREATE TABLE IF NOT EXISTS thumbup (" +
                "post_id BIGINT NOT NULL," +
                "user_id BIGINT NOT NULL," +
                "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE," +
                "FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE" +
                ");");
    }

    public void fillTables() {
        fillUserTable();
        fillFriendshipTable();
        fillPostTable();
        fillThumbUpTable();
    }

    public void printUsers() {
        ResultSet resultSet = db.select("SELECT u.name AS name FROM user u WHERE (u.id IN (\n" +
                "SELECT f.user_id_1 FROM friendship f\n" +
                "GROUP BY f.user_id_1\n" +
                "HAVING COUNT(f.user_id_1) > 100\n" +
                ") OR u.id IN (\n" +
                "SELECT f.user_id_2 FROM friendship f\n" +
                "GROUP BY f.user_id_2\n" +
                "HAVING COUNT(f.user_id_2) > 100\n" +
                ")) AND u.id IN (\n" +
                "SELECT p.user_id FROM post p WHERE p.id IN (\n" +
                "SELECT p1.id FROM post p1 \n" +
                "JOIN thumbup t ON t.post_id = p1.id\n" +
                "WHERE t.timestamp BETWEEN '2025-03-01' AND '2025-04-01'\n" +
                "GROUP BY p1.id\n" +
                "HAVING COUNT(p1.id) > 100\n" +
                ")\n" +
                ");");
        try {
            boolean isUserFound = false;
            while (resultSet.next()) {
                isUserFound = true;
                LOGGER.info(resultSet.getString(1));
            }
            if (!isUserFound) {
                LOGGER.info("No users found");
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error("Cannot print users:\n" + e);
        }
    }

}
