package com.ciber.service;

import com.ciber.dao.LoteDAO;
import com.ciber.model.Lote;

import java.util.List;

public class LoteService {

    private LoteDAO dao = new LoteDAO();

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
    }

    public void finalizar(int id) {
        dao.finalizar(id);
    }

    public void eliminar(int id) {
        dao.inactivar(id);
    }
}