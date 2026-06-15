package mx.edu.diseniosistemas.restaurante.dao;

import mx.edu.diseniosistemas.restaurante.model.Usuario;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;

import java.sql.*;

public class UsuarioDAO {
    public Usuario autenticar(String correo, String password) throws SQLException {
        String sql = "SELECT id_usuario, nombre, correo, rol, activo FROM usuarios WHERE correo = ? AND password = ? AND activo = TRUE";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setCorreo(rs.getString("correo"));
                    u.setRol(rs.getString("rol"));
                    u.setActivo(rs.getBoolean("activo"));
                    return u;
                }
            }
        }
        return null;
    }
}