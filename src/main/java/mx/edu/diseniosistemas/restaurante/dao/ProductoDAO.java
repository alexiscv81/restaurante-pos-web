package mx.edu.diseniosistemas.restaurante.dao;

import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listarActivos() throws SQLException {
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.stock, p.activo, " +
             "c.id_categoria, c.nombre AS categoria_nombre, c.descripcion AS categoria_descripcion " +
             "FROM productos p " +
             "INNER JOIN categorias c ON p.id_categoria = c.id_categoria " +
             "WHERE p.activo = TRUE " +
             "ORDER BY p.nombre";
        List<Producto> productos = new ArrayList<>();
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
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

                productos.add(p);
            }
        }
        return productos;
    }

    public Producto buscarPorId(int idProducto) throws SQLException {
        String sql = "SELECT * FROM productos WHERE id_producto = ? AND activo = TRUE";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto();
                    p.setIdProducto(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setStock(rs.getInt("stock"));
                    Categoria c = new Categoria();
                    c.setIdCategoria(rs.getInt("id_categoria"));
                    p.setCategoria(c);
                    return p;
                }
            }
        }
        return null;
    }

    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos(id_categoria, nombre, descripcion, precio, stock, activo) VALUES(?, ?, ?, ?, ?, TRUE)";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, producto.getCategoria().getIdCategoria());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setBigDecimal(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
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
}