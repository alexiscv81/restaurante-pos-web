package mx.edu.diseniosistemas.restaurante.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mx.edu.diseniosistemas.restaurante.dao.ProductoDAO;
import mx.edu.diseniosistemas.restaurante.dao.VentaDAO;
import mx.edu.diseniosistemas.restaurante.model.*;
import mx.edu.diseniosistemas.restaurante.service.VentaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "VentaServlet", urlPatterns = {"/app/ventas"})
public class VentaServlet extends HttpServlet {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final VentaDAO ventaDAO = new VentaDAO();
    private final VentaService ventaService = new VentaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("productos", productoDAO.listarActivos());
            request.getRequestDispatcher("/WEB-INF/jsp/ventas.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            Producto producto = productoDAO.buscarPorId(idProducto);

            DetalleVenta detalle = ventaService.crearDetalle(producto, cantidad);
            Venta venta = new Venta();
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            venta.setUsuario(usuario);
            List<DetalleVenta> detalles = new ArrayList<>();
            detalles.add(detalle);
            venta.setDetalles(detalles);
            ventaService.calcularTotal(venta);

            int idVenta = ventaDAO.registrarVenta(venta);
            response.sendRedirect(request.getContextPath() + "/app/ventas?registrada=" + idVenta);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}