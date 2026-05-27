package com.ciber.model;

public class RestauracionItem {

    private final String auditId;
    private final String tabla;
    private final String accion;
    private final int registroId;
    private final String nombre;
    private final String fecha;

    public RestauracionItem(String auditId, String tabla, String accion, int registroId, String nombre, String fecha) {
        this.auditId = auditId;
        this.tabla = tabla;
        this.accion = accion;
        this.registroId = registroId;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getAuditId() {
        return auditId;
    }

    public String getTabla() {
        return tabla;
    }

    public String getAccion() {
        return accion;
    }

    public int getRegistroId() {
        return registroId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }
}
