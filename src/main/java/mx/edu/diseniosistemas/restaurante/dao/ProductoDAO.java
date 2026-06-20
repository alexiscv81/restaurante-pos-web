package mx.edu.diseniosistemas.restaurante.dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;
 
public class ProductoDAO {
 
    public List<Producto> listarActivos() throws SQLException {
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.stock, p.activo, "
                + "c.id_categoria, c.nombre AS categoria_nombre, c.descripcion AS categoria_descripcion "
                + "FROM productos p "
                + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
                + "WHERE p.activo = TRUE AND c.activo = TRUE "
                + "ORDER BY p.nombre";
 
        List<Producto> productos = new ArrayList<>();
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productos.add(mapearProductoConCategoria(rs));
            }
        }
        return productos;
    }
 
    public Producto buscarPorId(int idProducto) throws SQLException {
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.stock, p.activo, "
                + "c.id_categoria, c.nombre AS categoria_nombre, c.descripcion AS categoria_descripcion "
                + "FROM productos p "
                + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
                + "WHERE p.id_producto = ? AND p.activo = TRUE";
 
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProductoConCategoria(rs);
                }
            }
        }
        return null;
    }
 
    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos(id_categoria, nombre, descripcion, precio, stock, activo) "
                + "VALUES(?, ?, ?, ?, ?, TRUE)";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            llenarStatementProducto(ps, producto);
            ps.executeUpdate();
        }
    }
 
    public void actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE productos "
                + "SET id_categoria = ?, nombre = ?, descripcion = ?, precio = ?, stock = ? "
                + "WHERE id_producto = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            llenarStatementProducto(ps, producto);
            ps.setInt(6, producto.getIdProducto());
            ps.executeUpdate();
        }
    }
 
    public void eliminarLogico(int idProducto) throws SQLException {
        String sql = "UPDATE productos SET activo = FALSE WHERE id_producto = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ps.executeUpdate();
        }
    }
 
    public void actualizarStock(Connection cn, int idProducto, int nuevoStock) throws SQLException {
        String sql = "UPDATE productos SET stock = ? WHERE id_producto = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }
 
    public int contarActivos() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM productos WHERE activo = TRUE";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
 
    public int contarStockTotal() throws SQLException {
        String sql = "SELECT COALESCE(SUM(stock), 0) AS total FROM productos WHERE activo = TRUE";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
 
    private void llenarStatementProducto(PreparedStatement ps, Producto producto) throws SQLException {
        ps.setInt(1, producto.getCategoria().getIdCategoria());
        ps.setString(2, producto.getNombre().trim());
        ps.setString(3, producto.getDescripcion());
        ps.setBigDecimal(4, producto.getPrecio());
        ps.setInt(5, producto.getStock());
    }
 
    private Producto mapearProductoConCategoria(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("id_producto"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getBigDecimal("precio"));
        p.setStock(rs.getInt("stock"));
        p.setActivo(rs.getBoolean("activo"));
 
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getInt("id_categoria"));
        c.setNombre(rs.getString("categoria_nombre"));
        c.setDescripcion(rs.getString("categoria_descripcion"));
        p.setCategoria(c);
        return p;
    }
}
