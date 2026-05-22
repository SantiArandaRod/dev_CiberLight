package com.ciber.util;

import com.ciber.audit.MongoConnection;
import com.mongodb.client.MongoDatabase;

public class MongoTest {

    public static void main(String[] args) {

        MongoDatabase db =
                MongoConnection.getDatabase();

        if (db != null) {

            System.out.println(
                    "Base conectada: "
                            + db.getName()
            );
        }
    }
}