package com.maxicampo.dao;

import com.maxicampo.model.UnidadVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnidadVentaDAO {
    public void crearUnidadVenta(UnidadVenta unidadVenta){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sqlCrearUnidadVenta = """
                    INSERT INTO unidades_venta(id, id_producto, descripcion, conversion, precio_venta)
                    VALUES (?,?,?,?,?)
                """;
        try(PreparedStatement stmt = con.prepareStatement(sqlCrearUnidadVenta)){
            stmt.setString(1, unidadVenta.getId());
            stmt.setString(2, unidadVenta.getIdProducto());
            stmt.setString(3, unidadVenta.getDescripcion());
            stmt.setDouble(4, unidadVenta.getConversion());
            stmt.setDouble(5, unidadVenta.getPrecioVenta());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Error al crear la unidad de venta" + e.getMessage());
        }
    }

    public UnidadVenta obtenerPorId(String id){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sqlObtenerPorId = "SELECT * FROM unidades_venta WHERE id = ?";
        try(PreparedStatement stmt = con.prepareStatement(sqlObtenerPorId)){
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new UnidadVenta(
                        id,
                        rs.getString("id_producto"),
                        rs.getString("descripcion"),
                        rs.getDouble("conversion"),
                        rs.getDouble("precio_venta")
                );
            }else{
                return null;
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al obtener la unidad por id " + e.getMessage());
        }
    }

    public List<UnidadVenta> obtenerPorProducto(String idProducto){
        Connection con = ConexionDB.getInstancia().getConexion();
        List<UnidadVenta> unidadVentas = new ArrayList<>();
        String sqlObtenerPorProducto = "SELECT * FROM unidades_venta WHERE id_producto = ?";
        try(PreparedStatement stmt = con.prepareStatement(sqlObtenerPorProducto)){
            stmt.setString(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                UnidadVenta unidadVenta = new UnidadVenta(
                        rs.getString("id"),
                        idProducto,
                        rs.getString("descripcion"),
                        rs.getDouble("conversion"),
                        rs.getDouble("precio_venta")
                );
                unidadVentas.add(unidadVenta);
            }
            return  unidadVentas;
        }catch (SQLException e){
            throw new RuntimeException("Error al obtener las unidades por producto " + e.getMessage());
        }
    }

    public UnidadVenta actualizarUnidadVenta(UnidadVenta unidadVenta){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sqlActualizarUnidad = """
                UPDATE unidades_venta
                SET id_producto = ?, descripcion = ?, conversion = ?, precio_venta = ?
                WHERE id = ?
                """;
        try(PreparedStatement stmt = con.prepareStatement(sqlActualizarUnidad)){
            stmt.setString(1, unidadVenta.getIdProducto());
            stmt.setString(2, unidadVenta.getDescripcion());
            stmt.setDouble(3, unidadVenta.getConversion());
            stmt.setDouble(4, unidadVenta.getPrecioVenta());
            stmt.setString(5, unidadVenta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar la unidad de venta " + e.getMessage());
        }
        return unidadVenta;
    }

    public String eliminarUnidadVenta(String id){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sqlEliminarUnidad = "DELETE FROM unidades_venta WHERE id = ?";
        try(PreparedStatement stmt = con.prepareStatement(sqlEliminarUnidad)){
            stmt.setString(1, id);
            stmt.executeUpdate();
            return "Unidad de venta eliminada correctamente";
        }catch (SQLException e){
            throw new RuntimeException("Error al elmininar la unidad de venta" + e.getMessage());
        }
    }
}
