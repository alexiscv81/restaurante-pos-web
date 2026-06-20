package mx.edu.diseniosistemas.restaurante.controller;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
 
@WebServlet(name = "IdiomaServlet", urlPatterns = {"/idioma"})
public class IdiomaServlet extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lang = request.getParameter("lang");
 
        if ("en".equalsIgnoreCase(lang)) {
            request.getSession().setAttribute("lang", "en");
        } else {
            request.getSession().setAttribute("lang", "es");
        }
 
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.trim().isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}