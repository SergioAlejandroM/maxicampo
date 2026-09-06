package com.maxicampo.service;

import com.maxicampo.dao.UsuarioDAO;
import com.maxicampo.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private UsuarioDAO usuarioDAO;

    public AuthService() {this.usuarioDAO = new UsuarioDAO();}

    public Usuario login(String usuario, String contraseña){
        Usuario user = usuarioDAO.obtenerPorUsuario(usuario);
        if(user != null && BCrypt.checkpw(contraseña, user.getPasswordHash())){
            return user;
        }else{
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
    }

}
