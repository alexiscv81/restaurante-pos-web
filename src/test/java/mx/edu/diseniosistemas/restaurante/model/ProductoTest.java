package mx.edu.diseniosistemas.restaurante.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {
    @Test
    void testGettersAndSetters() {
        Producto p = new Producto();
        p.setIdProducto(1);
        p.setNombre("Taco");
        p.setDescripcion("Taco al pastor");
        p.setPrecio(new BigDecimal("15.50"));
        p.setStock(100);
        p.setActivo(true);
        
        Categoria c = new Categoria();
        c.setIdCategoria(1);
        p.setCategoria(c);

        assertEquals(1, p.getIdProducto());
        assertEquals("Taco", p.getNombre());
        assertEquals("Taco al pastor", p.getDescripcion());
        assertEquals(new BigDecimal("15.50"), p.getPrecio());
        assertEquals(100, p.getStock());
        assertTrue(p.isActivo());
        assertEquals(c, p.getCategoria());
    }
}
