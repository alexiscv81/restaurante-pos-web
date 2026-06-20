package mx.edu.diseniosistemas.restaurante.service;
 
import mx.edu.diseniosistemas.restaurante.model.Categoria;
 
public class CategoriaService {
 
    public void validarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoría requerida");
        }
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }
        if (categoria.getNombre().trim().length() > 80) {
            throw new IllegalArgumentException("El nombre de la categoría no debe superar 80 caracteres");
        }
        if (categoria.getDescripcion() != null && categoria.getDescripcion().length() > 255) {
            throw new IllegalArgumentException("La descripción no debe superar 255 caracteres");
        }
    }
}