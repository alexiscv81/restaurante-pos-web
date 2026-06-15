package mx.edu.diseniosistemas.restaurante.dao;

import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listarActivas() throws SQLException {
        String sql = "SELECT id_categoria, nombre, descripcion, activo FROM categorias WHERE activo = TRUE ORDER BY nombre";
        List<Categoria> categorias = new ArrayList<>();
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categorias.add(new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBoolean("activo")
                ));
            }
        }
        return categorias;
    }

    public void insertar(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias(nombre, descripcion, activo) VALUES(?, ?, TRUE)";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.executeUpdate();
        }
    }

    public void eliminarLogico(int idCategoria) throws SQLException {
        String sql = "UPDATE categorias SET activo = FALSE WHERE id_categoria = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ps.executeUpdate();
        }
    }
}