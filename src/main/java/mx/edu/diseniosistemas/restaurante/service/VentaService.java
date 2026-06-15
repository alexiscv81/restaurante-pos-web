package mx.edu.diseniosistemas.restaurante.service;

import mx.edu.diseniosistemas.restaurante.model.DetalleVenta;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.model.Venta;

import java.math.BigDecimal;

public class VentaService {
    public DetalleVenta crearDetalle(Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("Producto requerido");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        if (producto.getStock() < cantidad) throw new IllegalArgumentException("Stock insuficiente");

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
        return detalle;
    }

    public void calcularTotal(Venta venta) {
        BigDecimal total = venta.getDetalles().stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        venta.setTotal(total);
    }
}