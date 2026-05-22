package com.ciber.audit;

import org.bson.Document;

import java.lang.reflect.Field;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;

public class DocumentMapper {

    public static Document toDocument(Object obj) {

        Document doc = new Document();

        Class<?> currentClass = obj.getClass();

        try {

            while (currentClass != null && currentClass != Object.class) {
                Field[] fields = currentClass.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    doc.append(field.getName(), normalizeValue(field.get(obj)));
                }

                currentClass = currentClass.getSuperclass();
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return doc;
    }

    private static Object normalizeValue(Object value) {
        if (value == null ||
                value instanceof String ||
                value instanceof Number ||
                value instanceof Boolean) {
            return value;
        }

        if (value instanceof Enum<?> enumValue) {
            return enumValue.name();
        }

        if (value instanceof TemporalAccessor) {
            return value.toString();
        }

        if (value instanceof Collection<?> collection) {
            return collection.stream()
                    .map(DocumentMapper::normalizeValue)
                    .toList();
        }

        return toDocument(value);
    }
}
