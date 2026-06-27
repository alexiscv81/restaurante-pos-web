package mx.edu.diseniosistemas.restaurante.dao;

import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoDAOTest {

    private MockedStatic<ConexionBD> conexionBDMockedStatic;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    private ProductoDAO productoDAO;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        conexionBDMockedStatic = mockStatic(ConexionBD.class);
        conexionBDMockedStatic.when(ConexionBD::getConnection).thenReturn(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        productoDAO = new ProductoDAO();
    }

    @AfterEach
    void tearDown() {
        conexionBDMockedStatic.close();
    }

    @Test
    void testListarActivos() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id_producto")).thenReturn(1);
        when(mockResultSet.getString("nombre")).thenReturn("Taco");
        when(mockResultSet.getString("descripcion")).thenReturn("Taco al pastor");
        when(mockResultSet.getBigDecimal("precio")).thenReturn(new BigDecimal("15.50"));
        when(mockResultSet.getInt("stock")).thenReturn(100);
        when(mockResultSet.getBoolean("activo")).thenReturn(true);
        
        when(mockResultSet.getInt("id_categoria")).thenReturn(1);
        when(mockResultSet.getString("categoria_nombre")).thenReturn("Comida");
        when(mockResultSet.getString("categoria_descripcion")).thenReturn("Comida mexicana");

        List<Producto> lista = productoDAO.listarActivos();
        assertEquals(1, lista.size());
        assertEquals("Taco", lista.get(0).getNombre());
        assertEquals("Comida", lista.get(0).getCategoria().getNombre());
    }

    @Test
    void testBuscarPorId() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id_producto")).thenReturn(1);
        when(mockResultSet.getString("nombre")).thenReturn("Torta");

        Producto p = productoDAO.buscarPorId(1);
        assertNotNull(p);
        assertEquals("Torta", p.getNombre());
    }

    @Test
    void testBuscarPorIdNotFound() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Producto p = productoDAO.buscarPorId(99);
        assertNull(p);
    }

    @Test
    void testInsertar() throws SQLException {
        Producto p = new Producto();
        p.setNombre("Refresco");
        p.setDescripcion("Refresco de cola");
        p.setPrecio(new BigDecimal("20.00"));
        p.setStock(50);
        Categoria c = new Categoria();
        c.setIdCategoria(2);
        p.setCategoria(c);
        
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        assertDoesNotThrow(() -> productoDAO.insertar(p));
        verify(mockPreparedStatement).setInt(1, 2); // id_categoria
        verify(mockPreparedStatement).setString(2, "Refresco");
    }

    @Test
    void testActualizar() throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(1);
        p.setNombre("Refresco");
        p.setPrecio(new BigDecimal("20.00"));
        p.setStock(50);
        Categoria c = new Categoria();
        c.setIdCategoria(2);
        p.setCategoria(c);
        
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        assertDoesNotThrow(() -> productoDAO.actualizar(p));
        verify(mockPreparedStatement).setInt(6, 1); // id_producto
    }

    @Test
    void testEliminarLogico() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> productoDAO.eliminarLogico(1));
        verify(mockPreparedStatement).setInt(1, 1);
    }

    @Test
    void testActualizarStock() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertDoesNotThrow(() -> productoDAO.actualizarStock(mockConnection, 1, 90));
        verify(mockPreparedStatement).setInt(1, 90);
        verify(mockPreparedStatement).setInt(2, 1);
    }

    @Test
    void testContarActivos() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("total")).thenReturn(15);
        int total = productoDAO.contarActivos();
        assertEquals(15, total);
    }

    @Test
    void testContarStockTotal() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("total")).thenReturn(500);
        int total = productoDAO.contarStockTotal();
        assertEquals(500, total);
    }
}
