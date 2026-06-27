<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />
 
<div class="row mb-4">
    <div class="col-12">
        <h2 class="fw-bold text-primary"><fmt:message key="dashboard.title" /></h2>
        <p class="text-muted"><fmt:message key="dashboard.description" /></p>
    </div>
</div>
 
<div class="row g-4 mb-5">
    <div class="col-md-3 col-sm-6">
        <div class="card bg-primary text-white h-100 shadow-sm border-0">
            <div class="card-body">
                <h2 class="display-5 fw-bold mb-0">${totalCategorias}</h2>
                <p class="card-text text-uppercase fw-semibold"><small><fmt:message key="dashboard.categories" /></small></p>
            </div>
        </div>
    </div>
    <div class="col-md-3 col-sm-6">
        <div class="card bg-success text-white h-100 shadow-sm border-0">
            <div class="card-body">
                <h2 class="display-5 fw-bold mb-0">${totalProductos}</h2>
                <p class="card-text text-uppercase fw-semibold"><small><fmt:message key="dashboard.products" /></small></p>
            </div>
        </div>
    </div>
    <div class="col-md-3 col-sm-6">
        <div class="card bg-warning text-dark h-100 shadow-sm border-0">
            <div class="card-body">
                <h2 class="display-5 fw-bold mb-0">${stockTotal}</h2>
                <p class="card-text text-uppercase fw-semibold"><small><fmt:message key="dashboard.stock" /></small></p>
            </div>
        </div>
    </div>
    <div class="col-md-3 col-sm-6">
        <div class="card bg-info text-dark h-100 shadow-sm border-0">
            <div class="card-body">
                <h2 class="display-5 fw-bold mb-0">${totalVentasRegistradas}</h2>
                <p class="card-text text-uppercase fw-semibold"><small><fmt:message key="dashboard.sales" /></small></p>
            </div>
        </div>
    </div>
</div>
 
<div class="card shadow-sm border-0 mb-4">
    <div class="card-header bg-white py-3">
        <h5 class="mb-0 fw-bold text-secondary"><fmt:message key="sales.recent" /></h5>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0 align-middle">
                <thead class="table-light">
                <tr>
                    <th class="ps-4"><fmt:message key="table.id" /></th>
                    <th><fmt:message key="sales.date" /></th>
                    <th><fmt:message key="sales.user" /></th>
                    <th><fmt:message key="sales.total" /></th>
                    <th class="pe-4"><fmt:message key="sales.status" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="v" items="${ventasRecientes}">
                    <tr>
                        <td class="ps-4">${v.idVenta}</td>
                        <td>${v.fecha}</td>
                        <td>${v.usuario.nombre}</td>
                        <td class="fw-bold text-success">$ ${v.total}</td>
                        <td class="pe-4">
                            <span class="badge ${v.estado == 'REGISTRADA' ? 'bg-success' : 'bg-danger'}">${v.estado}</span>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty ventasRecientes}">
                    <tr>
                        <td colspan="5" class="text-center py-4 text-muted"><fmt:message key="table.empty" /></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
 
<jsp:include page="templates/footer.jsp" />