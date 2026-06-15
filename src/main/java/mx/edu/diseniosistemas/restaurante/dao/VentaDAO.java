package mx.edu.diseniosistemas.restaurante.dao;

import mx.edu.diseniosistemas.restaurante.model.DetalleVenta;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.model.Venta;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;

import java.sql.*;

public class VentaDAO {

    public int registrarVenta(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO ventas(id_usuario, total, estado) VALUES(?, ?, 'REGISTRADA')";
        String sqlDetalle = "INSERT INTO detalle_venta(id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES(?, ?, ?, ?, ?)";
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
                        if (!keys.next()) throw new SQLException("No se generó id de venta");
                        idVenta = keys.getInt(1);
                    }
                }

                for (DetalleVenta d : venta.getDetalles()) {
                    Producto p = d.getProducto();
                    int stockActual;
                    try (PreparedStatement psStock = cn.prepareStatement(sqlProducto)) {
                        psStock.setInt(1, p.getIdProducto());
                        try (ResultSet rs = psStock.executeQuery()) {
                            if (!rs.next()) throw new SQLException("Producto no encontrado: " + p.getIdProducto());
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
}