package com.ciber.audit;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class AuditRepository {

    private final MongoCollection<Document> collection;

    public AuditRepository() {

        MongoDatabase db = MongoConnection.getDatabase();

        collection = db.getCollection("audit_logs");
    }

    public void save(Document doc) {

        collection.insertOne(doc);
    }

    public List<Document> findRestorableLogs() {
        Bson query = Filters.and(
                Filters.in("action", "INACTIVATE", "DELETE"),
                Filters.ne("restored", true)
        );

        return collection.find(query)
                .sort(Sorts.descending("timestamp"))
                .into(new ArrayList<>());
    }

    public void markRestored(String auditId) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId(auditId)),
                Updates.combine(
                        Updates.set("restored", true),
                        Updates.set("restored_at", java.time.LocalDateTime.now().toString())
                )
        );
    }
}
