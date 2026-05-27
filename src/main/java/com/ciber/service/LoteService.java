package com.ciber.service;

import com.ciber.audit.AuditAction;
import com.ciber.audit.AuditService;
import com.ciber.dao.LoteDAO;
import com.ciber.model.Lote;

import java.util.List;

public class LoteService {

    private LoteDAO dao = new LoteDAO();
    private final AuditService auditService = new AuditService();
    private final NotificationService notificationService = new NotificationService();

    public List<Lote> listar() {
        return dao.listar();
    }

    public void crear(Lote l) {

        if (l.getNombre() == null || l.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre inválido");
        }

        if (l.getCliente() == null || l.getCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente inválido");
        }

        if (l.getTecnico() == null) {
            throw new IllegalArgumentException("Técnico requerido");
        }

        dao.insertar(l);
        notificationService.notifyLoteIniciado(l.getNombre(), l.getTecnico().getNombre());
    }

    public void finalizar(int id) {
        Lote lote = dao.buscarPorId(id);
        dao.finalizar(id);

        if (lote != null && lote.getTecnico() != null) {
            notificationService.notifyLoteFinalizado(lote.getNombre(), lote.getTecnico().getNombre());
        }
    }

    public void eliminar(int id) {
        Lote loteViejo = dao.buscarPorId(id);

        if (loteViejo == null) {
            throw new RuntimeException("Lote no encontrado");
        }

        auditService.log(
                "lote",
                AuditAction.INACTIVATE,
                loteViejo,
                null
        );

        dao.inactivar(id);
    }
}
