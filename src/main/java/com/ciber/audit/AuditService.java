package com.ciber.audit;

import org.bson.Document;

import java.time.LocalDateTime;

public class AuditService {

    private final AuditRepository repository =
            new AuditRepository();

    public void log(

            String table,
            AuditAction action,
            Object oldData,
            Object newData
    ) {

        Document doc = new Document()

                .append("table", table)

                .append("action", action.name())

                .append("timestamp",
                        LocalDateTime.now().toString())

                .append("old_data",

                        oldData != null
                                ? DocumentMapper.toDocument(oldData)
                                : null)

                .append("new_data",

                        newData != null
                                ? DocumentMapper.toDocument(newData)
                                : null);

        try {
            repository.save(doc);
        } catch (RuntimeException e) {
            throw new IllegalStateException(
                    "No se pudo respaldar en MongoDB. Verifica que MongoDB este iniciado.",
                    e
            );
        }
    }
}
