package mx.edu.diseniosistemas.restaurante.dao;

import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaDAOTest {

    private MockedStatic<ConexionBD> conexionBDMockedStatic;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    private CategoriaDAO categoriaDAO;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        conexionBDMockedStatic = mockStatic(ConexionBD.class);
        conexionBDMockedStatic.when(ConexionBD::getConnection).thenReturn(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        categoriaDAO = new CategoriaDAO();
    }

    @AfterEach
    void tearDown() {
        conexionBDMockedStatic.close();
    }

    @Test
    void testListarActivas() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id_categoria")).thenReturn(1);
        when(mockResultSet.getString("nombre")).thenReturn("Carnes");
        when(mockResultSet.getString("descripcion")).thenReturn("Cortes");
        when(mockResultSet.getBoolean("activo")).thenReturn(true);

        List<Categoria> lista = categoriaDAO.listarActivas();
        assertEquals(1, lista.size());
        assertEquals("Carnes", lista.get(0).getNombre());
    }

    @Test
    void testBuscarPorId() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id_categoria")).thenReturn(2);
        when(mockResultSet.getString("nombre")).thenReturn("Sopas");

        Categoria c = categoriaDAO.buscarPorId(2);
        assertNotNull(c);
        assertEquals("Sopas", c.getNombre());
    }

    @Test
    void testBuscarPorIdNotFound() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Categoria c = categoriaDAO.buscarPorId(99);
        assertNull(c);
    }

    @Test
    void testInsertar() throws SQLException {
        Categoria c = new Categoria();
        c.setNombre("Postres");
        c.setDescripcion("Dulces");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        assertDoesNotThrow(() -> categoriaDAO.insertar(c));
        verify(mockPreparedStatement).setString(1, "Postres");
    }

    @Test
    void testActualizar() throws SQLException {
        Categoria c = new Categoria(1, "Postres", "Dulces actualizados", true);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        assertDoesNotThrow(() -> categoriaDAO.actualizar(c));
        verify(mockPreparedStatement).setInt(3, 1);
    }

    @Test
    void testEliminarLogico() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> categoriaDAO.eliminarLogico(1));
        verify(mockPreparedStatement).setInt(1, 1);
    }

    @Test
    void testContarActivas() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("total")).thenReturn(5);

        int total = categoriaDAO.contarActivas();
        assertEquals(5, total);
    }
}
