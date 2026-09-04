package com.maxicampo.dao;

import com.maxicampo.model.Rol;
import com.maxicampo.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public void crearUsuario(Usuario usuario){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sqlCrearUsuario = """
                    INSERT INTO usuarios(id, usuario, nombre, password_hash, rol)
                    VALUES(?,?,?,?,?)
                """;
        try(PreparedStatement stmt = con.prepareStatement(sqlCrearUsuario)){
            stmt.setString(1, usuario.getId());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getPasswordHash());
            stmt.setString(5, usuario.getRol().name());
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Error al crear el usuario " + e.getMessage());
        }
    }

    public Usuario obtenerPorUsuario(String usuario){
        Connection con = ConexionDB.getInstancia().getConexion();
        String sqlObtenerPorUsuario = "SELECT * FROM  usuarios WHERE usuario = ?";
        try (PreparedStatement stmt = con.prepareStatement(sqlObtenerPorUsuario)){
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new Usuario(
                        rs.getString("id"),
                        usuario,
                        rs.getString("nombre"),
                        rs.getString("password_hash"),
                        Rol.valueOf(rs.getString("rol"))
                );
            }else {
                return null;
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al encontrar el usuario " + e.getMessage());
        }
    }
}
