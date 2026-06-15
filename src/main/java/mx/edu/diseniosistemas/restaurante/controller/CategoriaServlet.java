package mx.edu.diseniosistemas.restaurante.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mx.edu.diseniosistemas.restaurante.dao.CategoriaDAO;
import mx.edu.diseniosistemas.restaurante.model.Categoria;

import java.io.IOException;

@WebServlet(name = "CategoriaServlet", urlPatterns = {"/app/categorias"})
public class CategoriaServlet extends HttpServlet {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("categorias", categoriaDAO.listarActivas());
            request.getRequestDispatcher("/WEB-INF/jsp/categorias.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Categoria c = new Categoria();
            c.setNombre(request.getParameter("nombre"));
            c.setDescripcion(request.getParameter("descripcion"));
            categoriaDAO.insertar(c);
            response.sendRedirect(request.getContextPath() + "/app/categorias?ok=1");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}