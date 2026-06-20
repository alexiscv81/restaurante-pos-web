package mx.edu.diseniosistemas.restaurante.controller;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import mx.edu.diseniosistemas.restaurante.dao.CategoriaDAO;
import mx.edu.diseniosistemas.restaurante.dao.ProductoDAO;
import mx.edu.diseniosistemas.restaurante.dao.VentaDAO;
 
@WebServlet(name = "DashboardServlet", urlPatterns = {"/app/dashboard"})
public class DashboardServlet extends HttpServlet {
 
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final VentaDAO ventaDAO = new VentaDAO();
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("totalCategorias", categoriaDAO.contarActivas());
            request.setAttribute("totalProductos", productoDAO.contarActivos());
            request.setAttribute("stockTotal", productoDAO.contarStockTotal());
            request.setAttribute("totalVentasRegistradas", ventaDAO.contarVentasRegistradas());
 
            BigDecimal totalVentas = ventaDAO.sumarTotalVentas();
            request.setAttribute("totalVentas", totalVentas);
            request.setAttribute("ventasRecientes", ventaDAO.listarRecientes(5));
 
            request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al cargar el dashboard", e);
        }
    }
}