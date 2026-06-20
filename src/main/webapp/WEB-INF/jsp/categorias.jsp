<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />
 
<div class="contenedor">
    <h2><fmt:message key="categories.title" /></h2>
 
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${param.ok == '1'}">
        <div class="ok"><fmt:message key="categories.saved" /></div>
    </c:if>
    <c:if test="${param.actualizada == '1'}">
        <div class="ok"><fmt:message key="categories.updated" /></div>
    </c:if>
    <c:if test="${param.eliminada == '1'}">
        <div class="ok"><fmt:message key="categories.deleted" /></div>
    </c:if>
 
    <form method="post" action="${pageContext.request.contextPath}/app/categorias" class="formulario">
        <input type="hidden" name="idCategoria" value="${categoriaEditar.idCategoria}">
 
        <label for="nombre"><fmt:message key="category.name" /></label>
        <input type="text" id="nombre" name="nombre" required maxlength="80" value="${categoriaEditar.nombre}">
 
        <label for="descripcion"><fmt:message key="category.description" /></label>
        <input type="text" id="descripcion" name="descripcion" maxlength="255" value="${categoriaEditar.descripcion}">
 
        <br><br>
        <button class="btn" type="submit"><fmt:message key="accion.guardar" /></button>
        <c:if test="${not empty categoriaEditar}">
            <a class="btn-secundario" href="${pageContext.request.contextPath}/app/categorias"><fmt:message key="accion.cancelar" /></a>
        </c:if>
    </form>
 
    <table>
        <thead>
        <tr>
            <th><fmt:message key="table.id" /></th>
            <th><fmt:message key="category.name" /></th>
            <th><fmt:message key="category.description" /></th>
            <th><fmt:message key="table.actions" /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${categorias}">
            <tr>
                <td>${c.idCategoria}</td>
                <td>${c.nombre}</td>
                <td>${c.descripcion}</td>
                <td>
                    <a class="link-accion" href="${pageContext.request.contextPath}/app/categorias?accion=editar&id=${c.idCategoria}"><fmt:message key="accion.editar" /></a>
                    |
                    <a class="link-accion peligro" href="${pageContext.request.contextPath}/app/categorias?accion=eliminar&id=${c.idCategoria}"
                       onclick="return confirm('<fmt:message key="confirm.delete" />');"><fmt:message key="accion.eliminar" /></a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty categorias}">
            <tr>
                <td colspan="4"><fmt:message key="table.empty" /></td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
 
<jsp:include page="templates/footer.jsp" />