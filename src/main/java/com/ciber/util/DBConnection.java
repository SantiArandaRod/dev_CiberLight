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
                    "jdbc:mysql://localhost:3306/db_ciberlight",
                    "root",
                    "password"
            );
        }

        if (DatabaseConfig.getDatabase() == DatabaseType.SQLITE) {
            return DriverManager.getConnection(
                    "jdbc:sqlite:db_ciberlightsqlite"
            );
        }

        throw new SQLException("No database selected");
    }
}