package com.ciber.config;

import java.util.Locale;

public class DatabaseConfig {

    private static DatabaseType current;

    static {
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
            return AppConfig.get("db.sqlite.url", "jdbc:sqlite:db_ciberlightsqlite");
        }

        return AppConfig.get("db.mysql.url", "jdbc:mysql://172.30.16.238:3306/db_ciberlight");
    }

    public static String getJdbcUser() {
        return AppConfig.get("db.mysql.user", "dsaranda89");
    }

    public static String getJdbcPassword() {
        return AppConfig.get("db.mysql.password", "67001389");
    }

    public static String getMongoUri() {
        return AppConfig.get("mongo.uri", "mongodb://172.30.16.238:27017/?serverSelectionTimeoutMS=3000");
    }

    public static String getMongoDatabase() {
        return AppConfig.get("mongo.database", "ciberlight_backup");
    }

    private static DatabaseType resolveDatabaseType() {
        String configured = AppConfig.get("db.type", DatabaseType.SQLITE.name());

        try {
            return DatabaseType.valueOf(configured.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return DatabaseType.SQLITE;
        }
    }
}
