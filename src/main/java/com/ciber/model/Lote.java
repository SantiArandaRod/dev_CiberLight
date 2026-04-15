package com.ciber.model;

import java.time.LocalDate;

public class Lote {

    private int id;
    private String nombre;
    private String cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoLote estado;
    private Tecnico tecnico;
    private boolean activo;

    public Lote(int id, String nombre, String cliente,
                LocalDate fechaInicio, LocalDate fechaFin,
                EstadoLote estado, Tecnico tecnico, boolean activo) {

        this.id = id;
        this.nombre = nombre;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.tecnico = tecnico;
        this.activo = activo;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCliente() { return cliente; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public EstadoLote getEstado() { return estado; }
    public Tecnico getTecnico() { return tecnico; }
    public boolean isActivo() { return activo; }
}