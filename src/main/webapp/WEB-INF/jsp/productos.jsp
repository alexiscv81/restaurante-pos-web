<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />

<div class="contenedor">
    <h2>Productos</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${param.ok == '1'}">
        <div class="ok">Producto registrado correctamente.</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/app/productos" class="formulario">
        <label>Categoría</label>
        <select name="idCategoria" required>
            <option value="">Seleccione...</option>
            <c:forEach var="c" items="${categorias}">
                <option value="${c.idCategoria}">${c.nombre}</option>
            </c:forEach>
        </select>

        <label>Nombre</label>
        <input type="text" name="nombre" required maxlength="120">

        <label>Descripción</label>
        <input type="text" name="descripcion" maxlength="255">

        <label>Precio</label>
        <input type="number" step="0.01" min="0" name="precio" required>

        <label>Stock</label>
        <input type="number" min="0" name="stock" required>

        <br><br>
        <button class="btn" type="submit">Guardar producto</button>
    </form>

    <table>
        <thead>
        <tr><th>ID</th><th>Producto</th><th>Categoría</th><th>Precio</th><th>Stock</th></tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${productos}">
            <tr>
                <td>${p.idProducto}</td>
                <td>${p.nombre}</td>
                <td>${p.categoria.nombre}</td>
                <td>$ ${p.precio}</td>
                <td>${p.stock}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="templates/footer.jsp" />