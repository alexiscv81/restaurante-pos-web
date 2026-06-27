package mx.edu.diseniosistemas.restaurante.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.edu.diseniosistemas.restaurante.util.ConexionBD;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    private LoginServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private HttpSession session;
    
    private MockedStatic<ConexionBD> conexionBDMockedStatic;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new LoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
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
    void testDoGet() throws Exception {
        servlet.doGet(request, response);
        verify(request).getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPostExito() throws Exception {
        when(request.getParameter("correo")).thenReturn("admin@test.com");
        when(request.getParameter("password")).thenReturn("123");
        
        // Simular que el usuario existe en BD
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id_usuario")).thenReturn(1);
        when(mockResultSet.getString("nombre")).thenReturn("Admin");
        when(mockResultSet.getString("rol")).thenReturn("ADMIN");

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("usuario"), any());
        verify(response).sendRedirect("/RestaurantePOSWeb/app/dashboard");
    }

    @Test
    void testDoPostFallido() throws Exception {
        when(request.getParameter("correo")).thenReturn("admin@test.com");
        when(request.getParameter("password")).thenReturn("wrong");
        
        // Simular que NO existe en BD
        when(mockResultSet.next()).thenReturn(false);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(dispatcher).forward(request, response);
    }
}
