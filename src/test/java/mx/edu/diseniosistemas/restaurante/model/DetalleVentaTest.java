package mx.edu.diseniosistemas.restaurante.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class DetalleVentaTest {
    @Test
    void testGettersAndSetters() {
        DetalleVenta d = new DetalleVenta();
        d.setIdDetalleVenta(1);
        d.setCantidad(2);
        d.setPrecioUnitario(new BigDecimal("15.00"));
        d.setSubtotal(new BigDecimal("30.00"));
        
        Producto p = new Producto();
        p.setIdProducto(1);
        d.setProducto(p);

        assertEquals(1, d.getIdDetalleVenta());
        assertEquals(2, d.getCantidad());
        assertEquals(new BigDecimal("15.00"), d.getPrecioUnitario());
        assertEquals(new BigDecimal("30.00"), d.getSubtotal());
        assertEquals(p, d.getProducto());
    }
}
