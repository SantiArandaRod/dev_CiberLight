package com.ciber.config;

public class DatabaseConfig {

    private static DatabaseType current = DatabaseType.MYSQL;

    public static void setDatabase(DatabaseType type) {
        current = type;
    }

    public static DatabaseType getDatabase() {
        return current;
    }
}