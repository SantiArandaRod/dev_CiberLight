package com.ciber.audit;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class AuditRepository {

    private final MongoCollection<Document> collection;

    public AuditRepository() {

        MongoDatabase db = MongoConnection.getDatabase();

        collection = db.getCollection("audit_logs");
    }

    public void save(Document doc) {

        collection.insertOne(doc);
    }
}