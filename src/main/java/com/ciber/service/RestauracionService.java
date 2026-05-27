package com.ciber.service;

import com.ciber.audit.AuditRepository;
import com.ciber.dao.LoteDAO;
import com.ciber.dao.MaterialDAO;
import com.ciber.dao.TecnicoDAO;
import com.ciber.model.RestauracionItem;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RestauracionService {

    private final AuditRepository auditRepository = new AuditRepository();
    private final MaterialDAO materialDAO = new MaterialDAO();
    private final LoteDAO loteDAO = new LoteDAO();
    private final TecnicoDAO tecnicoDAO = new TecnicoDAO();

    public List<RestauracionItem> listarRestaurables() {
        List<RestauracionItem> items = new ArrayList<>();

        for (Document log : auditRepository.findRestorableLogs()) {
            Document oldData = log.get("old_data", Document.class);
            if (oldData == null || oldData.get("id") == null) {
                continue;
            }

            String tabla = log.getString("table");
            if (!isSupported(tabla)) {
                continue;
            }

            items.add(new RestauracionItem(
                    log.getObjectId("_id").toHexString(),
                    tabla,
                    log.getString("action"),
                    oldData.getInteger("id"),
                    resolveName(tabla, oldData),
                    log.getString("timestamp")
            ));
        }

        return items;
    }

    public void restaurar(RestauracionItem item) {
        switch (item.getTabla()) {
            case "material" -> materialDAO.restaurar(item.getRegistroId());
            case "lote" -> loteDAO.restaurar(item.getRegistroId());
            case "tecnico" -> tecnicoDAO.restaurar(item.getRegistroId());
            default -> throw new IllegalArgumentException("Tipo no soportado: " + item.getTabla());
        }

        auditRepository.markRestored(item.getAuditId());
    }

    private boolean isSupported(String tabla) {
        return "material".equals(tabla) || "lote".equals(tabla) || "tecnico".equals(tabla);
    }

    private String resolveName(String tabla, Document oldData) {
        if ("tecnico".equals(tabla)) {
            String nombre = oldData.getString("nombre");
            String especialidad = oldData.getString("especialidad");
            return especialidad == null || especialidad.isBlank()
                    ? nombre
                    : nombre + " - " + especialidad;
        }

        return oldData.getString("nombre");
    }
}
