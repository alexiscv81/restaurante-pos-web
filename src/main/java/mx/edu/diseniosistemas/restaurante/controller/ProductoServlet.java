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
import mx.edu.diseniosistemas.restaurante.model.Categoria;
import mx.edu.diseniosistemas.restaurante.model.Producto;
import mx.edu.diseniosistemas.restaurante.service.ProductoService;
 
@WebServlet(name = "ProductoServlet", urlPatterns = {"/app/productos"})
public class ProductoServlet extends HttpServlet {
 
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProductoService productoService = new ProductoService();
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
 
        try {
            if ("eliminar".equals(accion)) {
                int idProducto = Integer.parseInt(request.getParameter("id"));
                productoDAO.eliminarLogico(idProducto);
                response.sendRedirect(request.getContextPath() + "/app/productos?eliminado=1");
                return;
            }
 
            if ("editar".equals(accion)) {
                int idProducto = Integer.parseInt(request.getParameter("id"));
                Producto productoEditar = productoDAO.buscarPorId(idProducto);
                request.setAttribute("productoEditar", productoEditar);
            }
 
            cargarCatalogos(request);
            request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al procesar productos", e);
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Producto producto = construirProductoDesdeRequest(request);
            productoService.validarProducto(producto);
 
            if (producto.getIdProducto() > 0) {
                productoDAO.actualizar(producto);
                response.sendRedirect(request.getContextPath() + "/app/productos?actualizado=1");
            } else {
                productoDAO.insertar(producto);
                response.sendRedirect(request.getContextPath() + "/app/productos?ok=1");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            try {
                cargarCatalogos(request);
            } catch (Exception ex) {
                request.setAttribute("error", e.getMessage() + " / " + ex.getMessage());
            }
            request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp").forward(request, response);
        }
    }
 
    private Producto construirProductoDesdeRequest(HttpServletRequest request) {
        Producto p = new Producto();
        String idProducto = request.getParameter("idProducto");
        if (idProducto != null && !idProducto.trim().isEmpty()) {
            p.setIdProducto(Integer.parseInt(idProducto));
        }
 
        Categoria c = new Categoria();
        c.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
        p.setCategoria(c);
        p.setNombre(request.getParameter("nombre"));
        p.setDescripcion(request.getParameter("descripcion"));
        p.setPrecio(new BigDecimal(request.getParameter("precio")));
        p.setStock(Integer.parseInt(request.getParameter("stock")));
        return p;
    }
 
    private void cargarCatalogos(HttpServletRequest request) throws Exception {
        request.setAttribute("productos", productoDAO.listarActivos());
        request.setAttribute("categorias", categoriaDAO.listarActivas());
    }
}