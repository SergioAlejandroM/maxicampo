package com.maxicampo.dao;

import com.maxicampo.model.Producto;
import com.maxicampo.model.TipoMedida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public void crearProducto(Producto producto){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sqlCrearProducto = """
               INSERT INTO productos (id, nombre, categoria, tipo_medida, stock, costo)
               VALUES (?,?,?,?,?,?)
              """;
        try(PreparedStatement stmt = con.prepareStatement(sqlCrearProducto)){
            stmt.setString(1, producto.getId());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getCategoria());
            stmt.setString(4, producto.getTipoMedida().name());
            stmt.setDouble(5, producto.getStock());
            stmt.setDouble(6, producto.getCosto());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Error al crear el producto " + e.getMessage());
        }
    }

    public Producto obtenerPorId(String id){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sqlObtenerPorId = "SELECT * FROM productos WHERE id = ?";
        try(PreparedStatement stmt = con.prepareStatement(sqlObtenerPorId)){
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new Producto(
                        id,
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        TipoMedida.valueOf(rs.getString("tipo_medida")),
                        rs.getDouble("stock"),
                        rs.getDouble("costo")
                );
            }else{
                return null;
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al encontrar el producto " + e.getMessage());
        }
    }
    public List<Producto> obtenerTodos(){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sql = "SELECT * FROM productos";
        List<Producto> productos = new ArrayList<>();
        try(PreparedStatement stmt = con.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Producto producto = new Producto(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        TipoMedida.valueOf(rs.getString("tipo_medida")),
                        rs.getDouble("stock"),
                        rs.getDouble("costo")
                );

                productos.add(producto);
            }
            return productos;
        }catch (SQLException e){
            throw new RuntimeException("No se encontro ningun producto: " + e.getMessage());
        }
    }

    public Producto actualizarProducto(Producto producto){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sql = """
            UPDATE productos
            SET nombre = ?, categoria = ?, tipo_medida = ?, stock = ?, costo = ?
            WHERE id = ?
            """;

        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setString(3, producto.getTipoMedida().name());
            stmt.setDouble(4, producto.getStock());
            stmt.setDouble(5, producto.getCosto());
            stmt.setString(6, producto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el producto " + e.getMessage());
        }
        return producto;
    }

    public String eliminarProducto(String id){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sql = "DELETE FROM productos WHERE id = ?";

        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1, id);
            stmt.executeUpdate();
            return "Producto eliminado correctamente";
        }catch (SQLException e){
            throw new RuntimeException("Error al elmininar el producto" + e.getMessage());
        }
    }
}
