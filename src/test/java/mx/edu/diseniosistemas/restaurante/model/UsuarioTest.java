package mx.edu.diseniosistemas.restaurante.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    @Test
    void testGettersAndSetters() {
        Usuario u = new Usuario();
        u.setIdUsuario(1);
        u.setNombre("Juan");
        u.setCorreo("juan@test.com");
        u.setPassword("12345");
        u.setRol("ADMIN");
        u.setActivo(true);

        assertEquals(1, u.getIdUsuario());
        assertEquals("Juan", u.getNombre());
        assertEquals("juan@test.com", u.getCorreo());
        assertEquals("12345", u.getPassword());
        assertEquals("ADMIN", u.getRol());
        assertTrue(u.isActivo());
    }

    @Test
    void testConstructors() {
        Usuario u = new Usuario(2, "Pedro", "pedro@test.com", "CAJERO");
        assertEquals("Pedro", u.getNombre());
        assertEquals("CAJERO", u.getRol());
        assertTrue(u.isActivo());
    }
}
