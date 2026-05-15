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
                    "jdbc:mysql://172.30.16.36:3306/db_ciberlight",
                    "dsaranda89",
                    "67001389"
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