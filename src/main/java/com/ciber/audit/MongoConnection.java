package com.ciber.audit;

import com.ciber.config.DatabaseConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    private static MongoClient client;

    public static MongoDatabase getDatabase() {

        if (client == null) {
            client = MongoClients.create(DatabaseConfig.getMongoUri());
        }

        return client.getDatabase(DatabaseConfig.getMongoDatabase());
    }
}
