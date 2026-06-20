<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />
 
<div class="contenedor">
    <h2><fmt:message key="products.title" /></h2>
 
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${param.ok == '1'}">
        <div class="ok"><fmt:message key="products.saved" /></div>
    </c:if>
    <c:if test="${param.actualizado == '1'}">
        <div class="ok"><fmt:message key="products.updated" /></div>
    </c:if>
    <c:if test="${param.eliminado == '1'}">
        <div class="ok"><fmt:message key="products.deleted" /></div>
    </c:if>
 
    <form method="post" action="${pageContext.request.contextPath}/app/productos" class="formulario">
        <input type="hidden" name="idProducto" value="${productoEditar.idProducto}">
 
        <label for="idCategoria"><fmt:message key="product.category" /></label>
        <select id="idCategoria" name="idCategoria" required>
            <option value=""><fmt:message key="form.select" /></option>
            <c:forEach var="c" items="${categorias}">
                <option value="${c.idCategoria}" ${productoEditar.categoria.idCategoria == c.idCategoria ? 'selected' : ''}>${c.nombre}</option>
            </c:forEach>
        </select>
 
        <label for="nombre"><fmt:message key="product.name" /></label>
        <input type="text" id="nombre" name="nombre" required maxlength="120" value="${productoEditar.nombre}">
 
        <label for="descripcion"><fmt:message key="product.description" /></label>
        <input type="text" id="descripcion" name="descripcion" maxlength="255" value="${productoEditar.descripcion}">
 
        <label for="precio"><fmt:message key="product.price" /></label>
        <input type="number" id="precio" step="0.01" min="0" name="precio" required value="${productoEditar.precio}">
 
        <label for="stock"><fmt:message key="product.stock" /></label>
        <input type="number" id="stock" min="0" name="stock" required value="${productoEditar.stock}">
 
        <br><br>
        <button class="btn" type="submit"><fmt:message key="accion.guardar" /></button>
        <c:if test="${not empty productoEditar}">
            <a class="btn-secundario" href="${pageContext.request.contextPath}/app/productos"><fmt:message key="accion.cancelar" /></a>
        </c:if>
    </form>
 
    <table>
        <thead>
        <tr>
            <th><fmt:message key="table.id" /></th>
            <th><fmt:message key="product.name" /></th>
            <th><fmt:message key="product.category" /></th>
            <th><fmt:message key="product.price" /></th>
            <th><fmt:message key="product.stock" /></th>
            <th><fmt:message key="table.actions" /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${productos}">
            <tr>
                <td>${p.idProducto}</td>
                <td>${p.nombre}</td>
                <td>${p.categoria.nombre}</td>
                <td>$ ${p.precio}</td>
                <td>${p.stock}</td>
                <td>
                    <a class="link-accion" href="${pageContext.request.contextPath}/app/productos?accion=editar&id=${p.idProducto}"><fmt:message key="accion.editar" /></a>
                    |
                    <a class="link-accion peligro" href="${pageContext.request.contextPath}/app/productos?accion=eliminar&id=${p.idProducto}"
                       onclick="return confirm('<fmt:message key="confirm.delete" />');"><fmt:message key="accion.eliminar" /></a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty productos}">
            <tr>
                <td colspan="6"><fmt:message key="table.empty" /></td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
 
<jsp:include page="templates/footer.jsp" />