package com.maxicampo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializadorDB {

    public void crearTablas(){
        Connection con = ConexionDB.getInstancia().getConexion();

        String sqlProductos = """
                CREATE TABLE IF NOT EXISTS productos (
                    id TEXT PRIMARY KEY,
                    nombre TEXT NOT NULL,
                    categoria TEXT,
                    tipo_medida TEXT NOT NULL,
                    stock REAL NOT NULL,
                    costo REAL NOT NULL
                );
                """;

        String sqlUnidadVenta = """
                CREATE TABLE IF NOT EXISTS unidades_venta (
                    id TEXT PRIMARY KEY,
                    id_producto TEXT NOT NULL,
                    descripcion TEXT NOT NULL,
                    conversion REAL NOT NULL,
                    precio_venta REAL NOT NULL,
                    FOREIGN KEY (id_producto) REFERENCES productos(id) ON DELETE CASCADE
                );
                """;

        String sqlUsuarios = """
                CREATE TABLE IF NOT EXISTS usuarios(
                    id TEXT PRIMARY KEY,
                    usuario TEXT NOT NULL UNIQUE,
                    nombre TEXT NOT NULL,
                    password_hash TEXT NOT NULL,
                    rol TEXT NOT NULL
                );
                """;
        try(Statement stmt = con.createStatement()){
            stmt.execute(sqlProductos);
            stmt.execute(sqlUnidadVenta);
            stmt.execute(sqlUsuarios);
        }catch (SQLException e){
            throw new RuntimeException("Error al crear las tablas " + e.getMessage());
        }
    }
}