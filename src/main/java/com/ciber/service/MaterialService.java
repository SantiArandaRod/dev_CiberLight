package com.ciber.service;

import com.ciber.audit.AuditAction;
import com.ciber.audit.AuditService;
import com.ciber.dao.MaterialDAO;
import com.ciber.model.Material;

import java.util.List;

public class MaterialService {

    private MaterialDAO dao = new MaterialDAO();

    public List<Material> listar() {
        return dao.listar();
    }
    public void crear(Material m) {
        if (m.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Nombre vacío");
        }

        dao.insertar(m);
    }
    public void entradaStock(int id, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        dao.aumentarStock(id, cantidad);
    }
    public void salidaStock(int id, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        boolean ok = dao.disminuirStock(id, cantidad);

        if (!ok) {
            throw new RuntimeException("Stock insuficiente");
        }
    }
    public boolean hayAlerta(Material m) {
        return m.getStock() < m.getStockMinimo();
    }
    private final AuditService auditService =
            new AuditService();

    public void eliminar(int id) {

        Material materialViejo =
                dao.buscarPorId(id);

        if (materialViejo == null) {

            throw new RuntimeException(
                    "Material no encontrado"
            );
        }

        auditService.log(

                "material",

                AuditAction.INACTIVATE,

                materialViejo,

                null
        );

        dao.inactivar(id);
    }
}
