package mx.edu.diseniosistemas.restaurante.service;

import mx.edu.diseniosistemas.restaurante.dao.UsuarioDAO;
import mx.edu.diseniosistemas.restaurante.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginExitoso() throws SQLException {
        // Arrange
        String correo = "admin@test.com";
        String password = "password123";
        Usuario usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1);
        usuarioMock.setCorreo(correo);
        usuarioMock.setNombre("Administrador");

        when(usuarioDAO.autenticar(correo, password)).thenReturn(usuarioMock);

        // Act
        Usuario resultado = loginService.login(correo, password);

        // Assert
        assertNotNull(resultado, "El usuario devuelto no debería ser null");
        assertEquals(correo, resultado.getCorreo());
        verify(usuarioDAO, times(1)).autenticar(correo, password);
    }

    @Test
    void testLoginFallidoCredencialesIncorrectas() throws SQLException {
        // Arrange
        String correo = "admin@test.com";
        String password = "badpassword";

        when(usuarioDAO.autenticar(correo, password)).thenReturn(null);

        // Act
        Usuario resultado = loginService.login(correo, password);

        // Assert
        assertNull(resultado, "El usuario debería ser null por credenciales inválidas");
        verify(usuarioDAO, times(1)).autenticar(correo, password);
    }

    @Test
    void testLoginConParametrosVacios() throws SQLException {
        // Act
        Usuario resultadoNull = loginService.login(null, null);
        Usuario resultadoVacio = loginService.login("", " ");

        // Assert
        assertNull(resultadoNull, "Debe devolver null si recibe nulos");
        assertNull(resultadoVacio, "Debe devolver null si recibe campos vacíos");
        // No debe llegar a consultar el DAO
        verify(usuarioDAO, never()).autenticar(anyString(), anyString());
    }
}
