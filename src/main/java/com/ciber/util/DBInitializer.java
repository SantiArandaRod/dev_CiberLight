package com.ciber.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {

    public static void initSQLite() {

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             BufferedReader br = new BufferedReader(new FileReader("sqlite.sql"))) {

            String line;
            StringBuilder sql = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sql.append(line);
            }

            stmt.execute(sql.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}