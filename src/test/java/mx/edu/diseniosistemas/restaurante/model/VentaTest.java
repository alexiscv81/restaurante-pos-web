package mx.edu.diseniosistemas.restaurante.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VentaTest {
    @Test
    void testGettersAndSetters() {
        Venta v = new Venta();
        v.setIdVenta(1);
        v.setTotal(new BigDecimal("100.00"));
        v.setEstado("COMPLETADA");
        
        LocalDateTime t = LocalDateTime.now();
        v.setFecha(t);
        
        Usuario u = new Usuario();
        u.setIdUsuario(1);
        v.setUsuario(u);
        
        List<DetalleVenta> detalles = new ArrayList<>();
        v.setDetalles(detalles);

        assertEquals(1, v.getIdVenta());
        assertEquals(new BigDecimal("100.00"), v.getTotal());
        assertEquals("COMPLETADA", v.getEstado());
        assertEquals(t, v.getFecha());
        assertEquals(u, v.getUsuario());
        assertEquals(detalles, v.getDetalles());
    }
}
