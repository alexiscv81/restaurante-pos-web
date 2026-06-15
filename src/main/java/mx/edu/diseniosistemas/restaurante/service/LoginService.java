package mx.edu.diseniosistemas.restaurante.service;

import mx.edu.diseniosistemas.restaurante.dao.UsuarioDAO;
import mx.edu.diseniosistemas.restaurante.model.Usuario;
import java.sql.SQLException;

public class LoginService {
    private final UsuarioDAO usuarioDAO;

    public LoginService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public LoginService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario login(String correo, String password) throws SQLException {
        if (correo == null || correo.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        return usuarioDAO.autenticar(correo.trim(), password.trim());
    }
}