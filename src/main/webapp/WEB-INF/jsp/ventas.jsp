<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />
 
<div class="row mb-4">
    <div class="col-12">
        <h2 class="fw-bold text-primary"><fmt:message key="sales.title" /></h2>
    </div>
</div>

<c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        ${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${not empty param.registrada}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <fmt:message key="sales.saved" /> <span class="fw-bold">${param.registrada}</span>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="row">
    <!-- Panel Formulario Venta -->
    <div class="col-lg-5 mb-4">
        <div class="card shadow-sm border-0 h-100">
            <div class="card-header bg-white py-3">
                <h5 class="mb-0 fw-bold"><fmt:message key="sale.register" /></h5>
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/app/ventas">
                    <div class="mb-3">
                        <label for="idProducto" class="form-label fw-bold"><fmt:message key="sale.product" /></label>
                        <select id="idProducto" name="idProducto" class="form-select form-select-lg" required>
                            <option value=""><fmt:message key="form.select" /></option>
                            <c:forEach var="p" items="${productos}">
                                <option value="${p.idProducto}">${p.nombre} - $${p.precio} (Stock: ${p.stock})</option>
                            </c:forEach>
                        </select>
                    </div>
             
                    <div class="mb-4">
                        <label for="cantidad" class="form-label fw-bold"><fmt:message key="sale.quantity" /></label>
                        <input type="number" id="cantidad" name="cantidad" class="form-control form-control-lg" min="1" required value="1">
                    </div>
             
                    <div class="d-grid mt-4">
                        <button class="btn btn-success btn-lg fw-bold" type="submit">
                            <i class="bi bi-cart-check"></i> <fmt:message key="sale.register" />
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <!-- Histórico de ventas -->
    <div class="col-lg-7 mb-4">
        <div class="card shadow-sm border-0 h-100">
            <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
                <h5 class="mb-0 fw-bold"><fmt:message key="sales.recent" /></h5>
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
                                <td><small class="text-muted">${v.fecha}</small></td>
                                <td>${v.usuario.nombre}</td>
                                <td class="fw-bold text-success">$ ${v.total}</td>
                                <td class="pe-4"><span class="badge bg-success">${v.estado}</span></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty ventasRecientes}">
                            <tr>
                                <td colspan="5" class="text-center py-5 text-muted">
                                    <fmt:message key="table.empty" />
                                </td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
 
<jsp:include page="templates/footer.jsp" />