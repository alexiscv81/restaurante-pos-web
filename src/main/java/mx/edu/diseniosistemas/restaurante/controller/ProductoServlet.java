package mx.edu.diseniosistemas.restaurante.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mx.edu.diseniosistemas.restaurante.dao.CategoriaDAO;
import mx.edu.diseniosistemas.restaurante.dao.ProductoDAO;
import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.service.ProductoService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/app/productos"})
public class ProductoServlet extends HttpServlet {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProductoService productoService = new ProductoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("productos", productoDAO.listarActivos());
            request.setAttribute("categorias", categoriaDAO.listarActivas());
            request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Producto p = new Producto();
            Categoria c = new Categoria();
            c.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
            p.setCategoria(c);
            p.setNombre(request.getParameter("nombre"));
            p.setDescripcion(request.getParameter("descripcion"));
            p.setPrecio(new BigDecimal(request.getParameter("precio")));
            p.setStock(Integer.parseInt(request.getParameter("stock")));

            productoService.validarProducto(p);
            productoDAO.insertar(p);
            response.sendRedirect(request.getContextPath() + "/app/productos?ok=1");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}