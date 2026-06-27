package mx.edu.diseniosistemas.restaurante.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Mockito.*;

public class DashboardServletTest {

    private DashboardServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    private MockedStatic<ConexionBD> conexionBDMockedStatic;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new DashboardServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        conexionBDMockedStatic = mockStatic(ConexionBD.class);
        conexionBDMockedStatic.when(ConexionBD::getConnection).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @AfterEach
    void tearDown() {
        conexionBDMockedStatic.close();
    }

    @Test
    void testDoGet() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("total")).thenReturn(5);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("totalVentas"), any());
        verify(request).setAttribute(eq("totalProductos"), any());
        verify(request).setAttribute(eq("totalCategorias"), any());
        verify(dispatcher).forward(request, response);
    }
}
