package com.maxicampo.service;

import com.maxicampo.dao.ProductoDAO;
import com.maxicampo.dao.UnidadVentaDAO;
import com.maxicampo.model.Producto;
import com.maxicampo.model.UnidadVenta;

public class InventarioService {
    private ProductoDAO productoDAO;
    private UnidadVentaDAO unidadVentaDAO;

    public InventarioService() {
        this.productoDAO = new ProductoDAO();
        this.unidadVentaDAO = new UnidadVentaDAO();
    }

    public void procesarVenta(String idUnidadVenta, double cantidadVendida){
        UnidadVenta unidad =  unidadVentaDAO.obtenerPorId(idUnidadVenta);
        Producto producto = productoDAO.obtenerPorId(unidad.getIdProducto());

        double cantidadADescontar = unidad.getConversion() * cantidadVendida;

        if(cantidadADescontar > producto.getStock()){
            throw new RuntimeException("Stock Insuficiente");
        }else{
            producto.setStock(producto.getStock() - cantidadADescontar);
            productoDAO.actualizarProducto(producto);
        }
    }

    public void reponerStock(String idUnidadVenta, double cantidadCompra){
        if(cantidadCompra > 0){
            UnidadVenta unidad =  unidadVentaDAO.obtenerPorId(idUnidadVenta);
            Producto producto = productoDAO.obtenerPorId(unidad.getIdProducto());

            double cantidadAReponer = unidad.getConversion() * cantidadCompra;

            producto.setStock(producto.getStock() + cantidadAReponer);
            productoDAO.actualizarProducto(producto);
        }else{
            throw new RuntimeException("No se puede realizar la compra");
        }
    }
}
