package com.ciber.model;

public class Material {

    private int id;
    private String nombre;
    private int stock;
    private int stockMinimo;

    public Material(int id, String nombre, int stock, int stockMinimo) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void sumarStock(int cantidad){
        this.stock += cantidad;
    }

    public void restarStock(int cantidad){
        this.stock -= cantidad;
    }
}
