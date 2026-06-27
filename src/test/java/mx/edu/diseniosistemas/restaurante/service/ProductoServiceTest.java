package mx.edu.diseniosistemas.restaurante.service;

import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoServiceTest {

    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService();
    }

    @Test
    void testValidarProductoValido() {
        Producto p = new Producto();
        p.setNombre("Refresco");
        p.setPrecio(new BigDecimal("15.50"));
        p.setStock(10);
        Categoria c = new Categoria();
        c.setIdCategoria(1);
        p.setCategoria(c);

        // No debe lanzar excepción
        assertDoesNotThrow(() -> productoService.validarProducto(p));
    }

    @Test
    void testValidarProductoSinNombreLanzaExcepcion() {
        Producto p = new Producto();
        p.setNombre(""); // Vacío
        p.setPrecio(new BigDecimal("15.50"));
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarProducto(p);
        });
        assertTrue(exception.getMessage().contains("nombre del producto es obligatorio"));
    }

    @Test
    void testValidarProductoPrecioNegativoLanzaExcepcion() {
        Producto p = new Producto();
        p.setNombre("Hamburguesa");
        p.setPrecio(new BigDecimal("-5.00")); // Negativo
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarProducto(p);
        });
        assertTrue(exception.getMessage().contains("precio debe ser mayor o igual a cero"));
    }

    @Test
    void testValidarProductoStockNegativoLanzaExcepcion() {
        Producto p = new Producto();
        p.setNombre("Taco");
        p.setPrecio(new BigDecimal("10.00"));
        p.setStock(-2); // Negativo
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarProducto(p);
        });
        assertTrue(exception.getMessage().contains("stock no puede ser negativo"));
    }

    @Test
    void testValidarProductoSinCategoriaLanzaExcepcion() {
        Producto p = new Producto();
        p.setNombre("Agua");
        p.setPrecio(new BigDecimal("10.00"));
        p.setStock(5);
        p.setCategoria(null); // Sin categoría
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.validarProducto(p);
        });
        assertTrue(exception.getMessage().contains("seleccionar una categoría"));
    }
}
