package com.ciber.service;

import com.ciber.audit.AuditAction;
import com.ciber.audit.AuditService;
import com.ciber.dao.TecnicoDAO;
import com.ciber.model.Tecnico;

import java.util.List;

public class TecnicoService {

    private TecnicoDAO dao = new TecnicoDAO();
    private final AuditService auditService = new AuditService();

    public List<Tecnico> listar(){
        return dao.obtenerTodos();
    }

    public void crear(String nombre, String especialidad){
        dao.insertar(nombre, especialidad);
    }

    public void inactivar(int id){
        Tecnico tecnicoViejo = dao.buscarPorId(id);

        if (tecnicoViejo == null) {
            throw new RuntimeException("Tecnico no encontrado");
        }

        auditService.log(
                "tecnico",
                AuditAction.INACTIVATE,
                tecnicoViejo,
                null
        );

        dao.inactivar(id);
    }

}
