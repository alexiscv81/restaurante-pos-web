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

public class CategoriaServletTest {

    private CategoriaServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    private MockedStatic<ConexionBD> conexionBDMockedStatic;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new CategoriaServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getContextPath()).thenReturn("/RestaurantePOSWeb");

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
    void testDoGetListar() throws Exception {
        when(mockResultSet.next()).thenReturn(false); // Lista vacía
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("categorias"), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGetEliminar() throws Exception {
        when(request.getParameter("accion")).thenReturn("eliminar");
        when(request.getParameter("id")).thenReturn("1");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        servlet.doGet(request, response);
        verify(response).sendRedirect("/RestaurantePOSWeb/app/categorias?eliminada=1");
    }

    @Test
    void testDoPostInsertar() throws Exception {
        when(request.getParameter("nombre")).thenReturn("Nueva Cat");
        when(request.getParameter("descripcion")).thenReturn("Desc");
        
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        servlet.doPost(request, response);
        verify(response).sendRedirect("/RestaurantePOSWeb/app/categorias?ok=1");
    }

    @Test
    void testDoPostActualizar() throws Exception {
        when(request.getParameter("idCategoria")).thenReturn("1");
        when(request.getParameter("nombre")).thenReturn("Editada");
        when(request.getParameter("descripcion")).thenReturn("Desc");
        
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        servlet.doPost(request, response);
        verify(response).sendRedirect("/RestaurantePOSWeb/app/categorias?actualizada=1");
    }
}
