package mx.edu.diseniosistemas.restaurante.service;

import mx.edu.diseniosistemas.restaurante.model.Producto;
import java.math.BigDecimal;

public class ProductoService {
    public void validarProducto(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("Producto requerido");
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio debe ser mayor o igual a cero");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (producto.getCategoria() == null || producto.getCategoria().getIdCategoria() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una categoría");
        }
    }
}