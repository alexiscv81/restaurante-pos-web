package mx.edu.diseniosistemas.restaurante.controller;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mx.edu.diseniosistemas.restaurante.dao.ProductoDAO;
import mx.edu.diseniosistemas.restaurante.dao.VentaDAO;
import mx.edu.diseniosistemas.restaurante.model.DetalleVenta;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.model.Usuario;
import mx.edu.diseniosistemas.restaurante.model.Venta;
import mx.edu.diseniosistemas.restaurante.service.VentaService;
 
@WebServlet(name = "VentaServlet", urlPatterns = {"/app/ventas"})
public class VentaServlet extends HttpServlet {
 
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final VentaDAO ventaDAO = new VentaDAO();
    private final VentaService ventaService = new VentaService();
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            cargarDatosPantalla(request);
            request.getRequestDispatcher("/WEB-INF/jsp/ventas.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al cargar ventas", e);
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
            try {
                cargarDatosPantalla(request);
            } catch (Exception ex) {
                request.setAttribute("error", e.getMessage() + " / " + ex.getMessage());
            }
            request.getRequestDispatcher("/WEB-INF/jsp/ventas.jsp").forward(request, response);
        }
    }
 
    private void cargarDatosPantalla(HttpServletRequest request) throws Exception {
        request.setAttribute("productos", productoDAO.listarActivos());
        request.setAttribute("ventasRecientes", ventaDAO.listarRecientes(10));
    }
}
