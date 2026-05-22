package com.ciber.util;

import com.ciber.config.DatabaseConfig;
import com.ciber.config.DatabaseType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {

        if (DatabaseConfig.getDatabase() == DatabaseType.MYSQL) {
            return DriverManager.getConnection(
                    DatabaseConfig.getJdbcUrl(),
                    DatabaseConfig.getJdbcUser(),
                    DatabaseConfig.getJdbcPassword()
            );
        }

        if (DatabaseConfig.getDatabase() == DatabaseType.SQLITE) {
            return DriverManager.getConnection(DatabaseConfig.getJdbcUrl());
        }

        throw new SQLException("No database selected");
    }
}
