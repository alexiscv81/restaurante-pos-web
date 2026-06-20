package mx.edu.diseniosistemas.restaurante.dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;
 
public class CategoriaDAO {
 
    public List<Categoria> listarActivas() throws SQLException {
        String sql = "SELECT id_categoria, nombre, descripcion, activo "
                + "FROM categorias "
                + "WHERE activo = TRUE "
                + "ORDER BY nombre";
 
        List<Categoria> categorias = new ArrayList<>();
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categorias.add(mapearCategoria(rs));
            }
        }
        return categorias;
    }
 
    public Categoria buscarPorId(int idCategoria) throws SQLException {
        String sql = "SELECT id_categoria, nombre, descripcion, activo "
                + "FROM categorias "
                + "WHERE id_categoria = ? AND activo = TRUE";
 
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCategoria(rs);
                }
            }
        }
        return null;
    }
 
    public void insertar(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias(nombre, descripcion, activo) VALUES(?, ?, TRUE)";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre().trim());
            ps.setString(2, categoria.getDescripcion());
            ps.executeUpdate();
        }
    }
 
    public void actualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id_categoria = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre().trim());
            ps.setString(2, categoria.getDescripcion());
            ps.setInt(3, categoria.getIdCategoria());
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
 
    public int contarActivas() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM categorias WHERE activo = TRUE";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
 
    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        return new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getBoolean("activo")
        );
    }
}