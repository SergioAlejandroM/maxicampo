package com.maxicampo.model;

import java.util.ArrayList;
import java.util.List;

public class Producto {

    private String id;
    private String nombre;
    private String categoria;
    private TipoMedida tipoMedida;
    private List<UnidadVenta> unidadVenta;
    private double stock;
    private double costo;

    public Producto(String id, String nombre, String categoria, TipoMedida tipoMedida, double stock, double costo){
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.tipoMedida = tipoMedida;
        this.unidadVenta = new ArrayList<>();
        this.stock = stock;
        this.costo = costo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public TipoMedida getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(TipoMedida tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public List<UnidadVenta> getUnidadVenta() {
        return unidadVenta;
    }

    public void agregarUnidadVenta(UnidadVenta unidad){
        unidadVenta.add(unidad);
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
