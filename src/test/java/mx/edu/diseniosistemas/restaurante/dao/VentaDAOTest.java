package mx.edu.diseniosistemas.restaurante.dao;

import mx.edu.diseniosistemas.restaurante.model.DetalleVenta;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.model.Usuario;
import mx.edu.diseniosistemas.restaurante.model.Venta;
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
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VentaDAOTest {

    private MockedStatic<ConexionBD> conexionBDMockedStatic;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    private VentaDAO ventaDAO;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        conexionBDMockedStatic = mockStatic(ConexionBD.class);
        conexionBDMockedStatic.when(ConexionBD::getConnection).thenReturn(mockConnection);

        // Por defecto todos los prepareStatement devuelven el mismo mock
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        ventaDAO = new VentaDAO();
    }

    @AfterEach
    void tearDown() {
        conexionBDMockedStatic.close();
    }

    @Test
    void testListarRecientes() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id_venta")).thenReturn(1);
        when(mockResultSet.getTimestamp("fecha")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getBigDecimal("total")).thenReturn(new BigDecimal("100.00"));
        when(mockResultSet.getString("estado")).thenReturn("REGISTRADA");
        
        when(mockResultSet.getInt("id_usuario")).thenReturn(1);
        when(mockResultSet.getString("usuario_nombre")).thenReturn("Cajero 1");
        when(mockResultSet.getString("correo")).thenReturn("cajero@test.com");
        when(mockResultSet.getString("rol")).thenReturn("CAJERO");

        List<Venta> lista = ventaDAO.listarRecientes(10);
        assertEquals(1, lista.size());
        assertEquals(new BigDecimal("100.00"), lista.get(0).getTotal());
        assertEquals("Cajero 1", lista.get(0).getUsuario().getNombre());
    }

    @Test
    void testContarVentasRegistradas() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("total")).thenReturn(5);
        int total = ventaDAO.contarVentasRegistradas();
        assertEquals(5, total);
    }

    @Test
    void testSumarTotalVentas() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getBigDecimal("total")).thenReturn(new BigDecimal("1500.50"));
        BigDecimal total = ventaDAO.sumarTotalVentas();
        assertEquals(new BigDecimal("1500.50"), total);
    }

    @Test
    void testRegistrarVentaExito() throws SQLException {
        Venta v = new Venta();
        Usuario u = new Usuario();
        u.setIdUsuario(1);
        v.setUsuario(u);
        v.setTotal(new BigDecimal("50.00"));
        
        DetalleVenta d = new DetalleVenta();
        Producto p = new Producto();
        p.setIdProducto(1);
        p.setNombre("Taco");
        d.setProducto(p);
        d.setCantidad(2);
        d.setPrecioUnitario(new BigDecimal("25.00"));
        d.setSubtotal(new BigDecimal("50.00"));
        
        List<DetalleVenta> detalles = new ArrayList<>();
        detalles.add(d);
        v.setDetalles(detalles);

        // Mocks for generated keys
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(99); // idVenta

        // Mocks for stock check
        when(mockResultSet.getInt("stock")).thenReturn(10); // Hay 10 en stock

        int idGenerado = ventaDAO.registrarVenta(v);

        assertEquals(99, idGenerado);
        verify(mockConnection).commit();
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).setAutoCommit(true);
    }

    @Test
    void testRegistrarVentaSinIdGeneradoLanzaExcepcion() throws SQLException {
        Venta v = new Venta();
        Usuario u = new Usuario();
        u.setIdUsuario(1);
        v.setUsuario(u);

        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No se generó ID

        assertThrows(SQLException.class, () -> ventaDAO.registrarVenta(v));
        verify(mockConnection).rollback();
    }
}
