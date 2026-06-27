package mx.edu.diseniosistemas.restaurante.controller;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import mx.edu.diseniosistemas.restaurante.dao.CategoriaDAO;
import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.service.CategoriaService;
 
@WebServlet(name = "CategoriaServlet", urlPatterns = {"/app/categorias"})
public class CategoriaServlet extends HttpServlet {
 
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final CategoriaService categoriaService = new CategoriaService();
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
 
        try {
            if ("eliminar".equals(accion)) {
                int idCategoria = Integer.parseInt(request.getParameter("id"));
                categoriaDAO.eliminarLogico(idCategoria);
                response.sendRedirect(request.getContextPath() + "/app/categorias?eliminada=1");
                return;
            }
 
            if ("editar".equals(accion)) {
                int idCategoria = Integer.parseInt(request.getParameter("id"));
                Categoria categoriaEditar = categoriaDAO.buscarPorId(idCategoria);
                request.setAttribute("categoriaEditar", categoriaEditar);
            }
 
            request.setAttribute("categorias", categoriaDAO.listarActivas());
            request.getRequestDispatcher("/WEB-INF/jsp/categorias.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al procesar categorías", e);
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Categoria categoria = new Categoria();
            String id = request.getParameter("idCategoria");
 
            if (id != null && !id.trim().isEmpty()) {
                categoria.setIdCategoria(Integer.parseInt(id));
            }
 
            categoria.setNombre(request.getParameter("nombre"));
            categoria.setDescripcion(request.getParameter("descripcion"));
            categoriaService.validarCategoria(categoria);
 
            if (categoria.getIdCategoria() > 0) {
                categoriaDAO.actualizar(categoria);
                response.sendRedirect(request.getContextPath() + "/app/categorias?actualizada=1");
            } else {
                categoriaDAO.insertar(categoria);
                response.sendRedirect(request.getContextPath() + "/app/categorias?ok=1");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("categorias", categoriaDAOListarSeguro());
            request.getRequestDispatcher("/WEB-INF/jsp/categorias.jsp").forward(request, response);
        }
    }
 
    private Object categoriaDAOListarSeguro() {
        try {
            return categoriaDAO.listarActivas();
        } catch (Exception e) {
            return java.util.Collections.emptyList();
        }
    }
}     