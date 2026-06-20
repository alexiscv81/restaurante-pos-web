<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />
 
<div class="contenedor">
    <h2><fmt:message key="dashboard.title" /></h2>
    <p><fmt:message key="dashboard.description" /></p>
 
    <div class="cards">
        <div class="card">
            <span class="card-numero">${totalCategorias}</span>
            <span class="card-texto"><fmt:message key="dashboard.categories" /></span>
        </div>
        <div class="card">
            <span class="card-numero">${totalProductos}</span>
            <span class="card-texto"><fmt:message key="dashboard.products" /></span>
        </div>
        <div class="card">
            <span class="card-numero">${stockTotal}</span>
            <span class="card-texto"><fmt:message key="dashboard.stock" /></span>
        </div>
        <div class="card">
            <span class="card-numero">${totalVentasRegistradas}</span>
            <span class="card-texto"><fmt:message key="dashboard.sales" /></span>
        </div>
    </div>
 
    <h3><fmt:message key="sales.recent" /></h3>
    <table>
        <thead>
        <tr>
            <th><fmt:message key="table.id" /></th>
            <th><fmt:message key="sales.date" /></th>
            <th><fmt:message key="sales.user" /></th>
            <th><fmt:message key="sales.total" /></th>
            <th><fmt:message key="sales.status" /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="v" items="${ventasRecientes}">
            <tr>
                <td>${v.idVenta}</td>
                <td>${v.fecha}</td>
                <td>${v.usuario.nombre}</td>
                <td>$ ${v.total}</td>
                <td>${v.estado}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty ventasRecientes}">
            <tr>
                <td colspan="5"><fmt:message key="table.empty" /></td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
 
<jsp:include page="templates/footer.jsp" />