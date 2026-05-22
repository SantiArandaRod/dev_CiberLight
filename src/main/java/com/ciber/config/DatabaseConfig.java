package com.ciber.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties PROPERTIES = new Properties();
    private static DatabaseType current;

    static {
        try (InputStream input = DatabaseConfig.class
                .getResourceAsStream("/application.properties")) {
            if (input != null) {
                PROPERTIES.load(input);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        current = resolveDatabaseType();
    }

    public static void setDatabase(DatabaseType type) {
        current = type;
    }

    public static DatabaseType getDatabase() {
        return current;
    }

    public static String getJdbcUrl() {
        if (current == DatabaseType.SQLITE) {
            return get("db.sqlite.url", "jdbc:sqlite:db_ciberlightsqlite");
        }

        return get("db.mysql.url", "jdbc:mysql://172.30.16.36:3306/db_ciberlight");
    }

    public static String getJdbcUser() {
        return get("db.mysql.user", "dsaranda89");
    }

    public static String getJdbcPassword() {
        return get("db.mysql.password", "67001389");
    }

    public static String getMongoUri() {
        return get("mongo.uri", "mongodb://172.30.16.104:27017");
    }

    public static String getMongoDatabase() {
        return get("mongo.database", "ciberlight_backup");
    }

    private static DatabaseType resolveDatabaseType() {
        String configured = get("db.type", DatabaseType.SQLITE.name());

        try {
            return DatabaseType.valueOf(configured.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return DatabaseType.SQLITE;
        }
    }

    private static String get(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        String envKey = key.toUpperCase(Locale.ROOT).replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        return PROPERTIES.getProperty(key, defaultValue);
    }
}
