package com.maxicampo.model;

public class UnidadVenta {

    private String id;
    private String idProducto;
    private String descripcion;
    private double conversion;
    private double precioVenta;

    public UnidadVenta(String id, String idProducto, String descripcion, double conversion, double precioVenta){
        this.id = id;
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.conversion = conversion;
        this.precioVenta = precioVenta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getConversion() {
        return conversion;
    }

    public void setConversion(double conversion) {
        this.conversion = conversion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
}
