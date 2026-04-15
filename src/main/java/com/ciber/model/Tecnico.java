package com.ciber.model;

public class Tecnico extends Usuario {

    private String especialidad;
    private int lotesActivos;

    public Tecnico(int id, String nombre, Rol rol, boolean b, String especialidad) {
        super(id, nombre, rol);
        this.especialidad = especialidad;
        this.lotesActivos = 0;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public int getLotesActivos() {
        return lotesActivos;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setLotesActivos(int lotesActivos) {
        this.lotesActivos = lotesActivos;
    }
}