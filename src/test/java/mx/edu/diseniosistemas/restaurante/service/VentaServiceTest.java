package mx.edu.diseniosistemas.restaurante.service;

import mx.edu.diseniosistemas.restaurante.model.DetalleVenta;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.model.Venta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VentaServiceTest {

    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        ventaService = new VentaService();
    }

    @Test
    void testCrearDetalleExitoso() {
        Producto p = new Producto();
        p.setNombre("Jugo");
        p.setPrecio(new BigDecimal("20.00"));
        p.setStock(10);

        DetalleVenta detalle = ventaService.crearDetalle(p, 2);

        assertNotNull(detalle);
        assertEquals(p, detalle.getProducto());
        assertEquals(2, detalle.getCantidad());
        assertEquals(new BigDecimal("20.00"), detalle.getPrecioUnitario());
        assertEquals(new BigDecimal("40.00"), detalle.getSubtotal());
    }

    @Test
    void testCrearDetalleStockInsuficienteLanzaExcepcion() {
        Producto p = new Producto();
        p.setPrecio(new BigDecimal("20.00"));
        p.setStock(1); // Solo hay 1 en stock

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ventaService.crearDetalle(p, 2); // Se piden 2
        });
        assertTrue(exception.getMessage().contains("Stock insuficiente"));
    }

    @Test
    void testCalcularTotalVenta() {
        DetalleVenta d1 = new DetalleVenta();
        d1.setSubtotal(new BigDecimal("50.00"));

        DetalleVenta d2 = new DetalleVenta();
        d2.setSubtotal(new BigDecimal("30.50"));

        Venta venta = new Venta();
        List<DetalleVenta> detalles = new ArrayList<>();
        detalles.add(d1);
        detalles.add(d2);
        venta.setDetalles(detalles);

        ventaService.calcularTotal(venta);

        assertNotNull(venta.getTotal());
        assertEquals(new BigDecimal("80.50"), venta.getTotal());
    }
}
