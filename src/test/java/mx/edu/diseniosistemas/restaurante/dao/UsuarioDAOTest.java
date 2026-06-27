package mx.edu.diseniosistemas.restaurante.dao;

import mx.edu.diseniosistemas.restaurante.model.Usuario;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioDAOTest {

    private MockedStatic<ConexionBD> conexionBDMockedStatic;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        conexionBDMockedStatic = mockStatic(ConexionBD.class);
        conexionBDMockedStatic.when(ConexionBD::getConnection).thenReturn(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        usuarioDAO = new UsuarioDAO();
    }

    @AfterEach
    void tearDown() {
        conexionBDMockedStatic.close();
    }

    @Test
    void testAutenticarExitoso() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id_usuario")).thenReturn(1);
        when(mockResultSet.getString("nombre")).thenReturn("Test User");
        when(mockResultSet.getString("correo")).thenReturn("test@test.com");
        when(mockResultSet.getString("rol")).thenReturn("ADMIN");
        when(mockResultSet.getBoolean("activo")).thenReturn(true);

        Usuario u = usuarioDAO.autenticar("test@test.com", "123");
        
        assertNotNull(u);
        assertEquals(1, u.getIdUsuario());
        assertEquals("Test User", u.getNombre());
        assertEquals("ADMIN", u.getRol());
        
        verify(mockPreparedStatement).setString(1, "test@test.com");
        verify(mockPreparedStatement).setString(2, "123");
    }

    @Test
    void testAutenticarFallido() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        Usuario u = usuarioDAO.autenticar("test@test.com", "wrongpass");
        
        assertNull(u);
    }
}
