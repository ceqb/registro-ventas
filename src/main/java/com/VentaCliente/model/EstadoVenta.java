package com.VentaCliente.model;


public enum EstadoVenta {

    PAGO("PAGADO"),
    DEUDA("CON DEUDA"),
    EN_PROCESO("EN PROCESO");

    private final String descripcion;

    EstadoVenta(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
