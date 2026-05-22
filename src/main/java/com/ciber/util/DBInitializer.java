package com.ciber.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {

    public static void initSQLite() {

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             InputStream input = DBInitializer.class
                     .getResourceAsStream("/sql/sqlite_schema.sql")) {

            if (input == null) {
                throw new IllegalStateException("SQLite schema not found");
            }

            StringBuilder sql = new StringBuilder();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String trimmed = line.trim();
                    if (!trimmed.startsWith("--")) {
                        sql.append(line).append('\n');
                    }
                }
            }

            for (String sentence : sql.toString().split(";")) {
                String trimmed = sentence.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
