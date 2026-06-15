<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />

<div class="contenedor">
    <h2>Registrar venta</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty param.registrada}">
        <div class="ok">Venta registrada con folio: ${param.registrada}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/app/ventas" class="formulario">
        <label>Producto</label>
        <select name="idProducto" required>
            <option value="">Seleccione...</option>
            <c:forEach var="p" items="${productos}">
                <option value="${p.idProducto}">${p.nombre} - $${p.precio} - Stock: ${p.stock}</option>
            </c:forEach>
        </select>

        <label>Cantidad</label>
        <input type="number" name="cantidad" min="1" required>

        <br><br>
        <button class="btn" type="submit">Registrar venta</button>
    </form>
</div>

<jsp:include page="templates/footer.jsp" />