<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />
 
<div class="row mb-4">
    <div class="col-12">
        <h2 class="fw-bold text-primary"><fmt:message key="categories.title" /></h2>
    </div>
</div>

<c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        ${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${param.ok == '1'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <fmt:message key="categories.saved" />
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${param.actualizada == '1'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <fmt:message key="categories.updated" />
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${param.eliminada == '1'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <fmt:message key="categories.deleted" />
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="row">
    <div class="col-lg-4 mb-4">
        <div class="card shadow-sm border-0">
            <div class="card-header bg-white py-3">
                <h5 class="mb-0 fw-bold"><fmt:message key="accion.guardar" /> <fmt:message key="menu.categorias" /></h5>
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/app/categorias">
                    <input type="hidden" name="idCategoria" value="${categoriaEditar.idCategoria}">
             
                    <div class="mb-3">
                        <label for="nombre" class="form-label fw-bold"><fmt:message key="category.name" /></label>
                        <input type="text" id="nombre" name="nombre" class="form-control" required maxlength="80" value="${categoriaEditar.nombre}">
                    </div>
             
                    <div class="mb-4">
                        <label for="descripcion" class="form-label fw-bold"><fmt:message key="category.description" /></label>
                        <textarea id="descripcion" name="descripcion" class="form-control" rows="3" maxlength="255">${categoriaEditar.descripcion}</textarea>
                    </div>
             
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <c:if test="${not empty categoriaEditar}">
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/app/categorias"><fmt:message key="accion.cancelar" /></a>
                        </c:if>
                        <button class="btn btn-primary" type="submit"><fmt:message key="accion.guardar" /></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <div class="col-lg-8">
        <div class="card shadow-sm border-0">
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover table-striped mb-0 align-middle">
                        <thead class="table-light">
                        <tr>
                            <th class="ps-4"><fmt:message key="table.id" /></th>
                            <th><fmt:message key="category.name" /></th>
                            <th><fmt:message key="category.description" /></th>
                            <th class="pe-4 text-end"><fmt:message key="table.actions" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="c" items="${categorias}">
                            <tr>
                                <td class="ps-4">${c.idCategoria}</td>
                                <td class="fw-bold">${c.nombre}</td>
                                <td>${c.descripcion}</td>
                                <td class="pe-4 text-end">
                                    <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/app/categorias?accion=editar&id=${c.idCategoria}"><fmt:message key="accion.editar" /></a>
                                    <a class="btn btn-sm btn-outline-danger ms-1" href="${pageContext.request.contextPath}/app/categorias?accion=eliminar&id=${c.idCategoria}"
                                       onclick="return confirm('<fmt:message key="confirm.delete" />');"><fmt:message key="accion.eliminar" /></a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty categorias}">
                            <tr>
                                <td colspan="4" class="text-center py-4 text-muted"><fmt:message key="table.empty" /></td>
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