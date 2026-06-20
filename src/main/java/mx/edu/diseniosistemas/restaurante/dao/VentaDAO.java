package mx.edu.diseniosistemas.restaurante.dao;
 
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import mx.edu.diseniosistemas.restaurante.model.DetalleVenta;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.model.Usuario;
import mx.edu.diseniosistemas.restaurante.model.Venta;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;
 
public class VentaDAO {
 
    public int registrarVenta(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO ventas(id_usuario, total, estado) VALUES(?, ?, 'REGISTRADA')";
        String sqlDetalle = "INSERT INTO detalle_venta(id_venta, id_producto, cantidad, precio_unitario, subtotal) "
                + "VALUES(?, ?, ?, ?, ?)";
        String sqlProducto = "SELECT stock FROM productos WHERE id_producto = ? FOR UPDATE";
        String sqlUpdateStock = "UPDATE productos SET stock = ? WHERE id_producto = ?";
 
        try (Connection cn = ConexionBD.getConnection()) {
            cn.setAutoCommit(false);
            try {
                int idVenta;
                try (PreparedStatement ps = cn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, venta.getUsuario().getIdUsuario());
                    ps.setBigDecimal(2, venta.getTotal());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("No se generó id de venta");
                        }
                        idVenta = keys.getInt(1);
                    }
                }
 
                for (DetalleVenta d : venta.getDetalles()) {
                    Producto p = d.getProducto();
                    int stockActual;
                    try (PreparedStatement psStock = cn.prepareStatement(sqlProducto)) {
                        psStock.setInt(1, p.getIdProducto());
                        try (ResultSet rs = psStock.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException("Producto no encontrado: " + p.getIdProducto());
                            }
                            stockActual = rs.getInt("stock");
                        }
                    }
 
                    if (stockActual < d.getCantidad()) {
                        throw new SQLException("Stock insuficiente para el producto: " + p.getNombre());
                    }
 
                    try (PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle)) {
                        psDetalle.setInt(1, idVenta);
                        psDetalle.setInt(2, p.getIdProducto());
                        psDetalle.setInt(3, d.getCantidad());
                        psDetalle.setBigDecimal(4, d.getPrecioUnitario());
                        psDetalle.setBigDecimal(5, d.getSubtotal());
                        psDetalle.executeUpdate();
                    }
 
                    try (PreparedStatement psUpd = cn.prepareStatement(sqlUpdateStock)) {
                        psUpd.setInt(1, stockActual - d.getCantidad());
                        psUpd.setInt(2, p.getIdProducto());
                        psUpd.executeUpdate();
                    }
                }
 
                cn.commit();
                return idVenta;
            } catch (SQLException ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }
 
    public List<Venta> listarRecientes(int limite) throws SQLException {
        String sql = "SELECT v.id_venta, v.fecha, v.total, v.estado, "
                + "u.id_usuario, u.nombre AS usuario_nombre, u.correo, u.rol "
                + "FROM ventas v "
                + "INNER JOIN usuarios u ON v.id_usuario = u.id_usuario "
                + "ORDER BY v.fecha DESC "
                + "LIMIT ?";
 
        List<Venta> ventas = new ArrayList<>();
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapearVentaResumen(rs));
                }
            }
        }
        return ventas;
    }
 
    public int contarVentasRegistradas() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM ventas WHERE estado = 'REGISTRADA'";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
 
    public BigDecimal sumarTotalVentas() throws SQLException {
        String sql = "SELECT COALESCE(SUM(total), 0) AS total FROM ventas WHERE estado = 'REGISTRADA'";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        }
        return BigDecimal.ZERO;
    }
 
    private Venta mapearVentaResumen(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setIdVenta(rs.getInt("id_venta"));
        Timestamp fecha = rs.getTimestamp("fecha");
        if (fecha != null) {
            venta.setFecha(fecha.toLocalDateTime());
        }
        venta.setTotal(rs.getBigDecimal("total"));
        venta.setEstado(rs.getString("estado"));
 
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombre(rs.getString("usuario_nombre"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setRol(rs.getString("rol"));
        venta.setUsuario(usuario);
        return venta;
    }
}
