package com.ciber.model;

public class Usuario {

    private int id;
    private String nombre;
    private Rol rol;
    private boolean isActivo;

    public Usuario(int id, String nombre, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.isActivo = true;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Rol getRol() {
        return rol;
    }

    public boolean isActivo() {
        return isActivo;
    }

    public void setActivo(boolean activo) {
        isActivo = activo;
    }

    public void login(){
        System.out.println(nombre + " inició sesión");
    }

    public void logout(){
        System.out.println(nombre + " cerró sesión");
    }
}