package com.maxicampo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    private static ConexionDB instancia = null;
    private Connection conexion;
    private String url = "jdbc:sqlite:maxicampo.db";

    private ConexionDB(){
        try{
            this.conexion = DriverManager.getConnection(url);
            Statement stmt = conexion.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos" + e.getMessage());
        }
    }

    public static ConexionDB getInstancia(){
        if(instancia == null){
            instancia = new ConexionDB();
            System.out.println("Conexion exitosa a la base de datos");
        }
        return instancia;
    }

    public Connection getConexion(){
        return conexion;
    }
}