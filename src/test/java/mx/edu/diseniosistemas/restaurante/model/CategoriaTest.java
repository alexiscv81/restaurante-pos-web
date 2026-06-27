package mx.edu.diseniosistemas.restaurante.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoriaTest {
    @Test
    void testGettersAndSetters() {
        Categoria c = new Categoria();
        c.setIdCategoria(1);
        c.setNombre("Bebidas");
        c.setDescripcion("Refrescos y jugos");
        c.setActivo(true);

        assertEquals(1, c.getIdCategoria());
        assertEquals("Bebidas", c.getNombre());
        assertEquals("Refrescos y jugos", c.getDescripcion());
        assertTrue(c.isActivo());
    }

    @Test
    void testConstructors() {
        Categoria c = new Categoria(2, "Postres", "Dulces", true);
        assertEquals(2, c.getIdCategoria());
        assertEquals("Postres", c.getNombre());
        assertEquals("Dulces", c.getDescripcion());
        assertTrue(c.isActivo());
    }
}
