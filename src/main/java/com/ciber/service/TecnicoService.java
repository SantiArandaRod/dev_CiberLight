package com.ciber.service;

import com.ciber.dao.TecnicoDAO;
import com.ciber.model.Tecnico;

import java.util.List;

public class TecnicoService {

    private TecnicoDAO dao = new TecnicoDAO();

    public List<Tecnico> listar(){
        return dao.obtenerTodos();
    }

    public void crear(String nombre, String especialidad){
        dao.insertar(nombre, especialidad);
    }

    public void inactivar(int id){
        dao.inactivar(id);
    }

}