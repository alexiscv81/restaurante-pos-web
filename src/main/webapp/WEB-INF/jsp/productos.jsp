<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<jsp:include page="templates/header.jsp" />
<jsp:include page="templates/menu.jsp" />
 
<div class="row mb-4">
    <div class="col-12">
        <h2 class="fw-bold text-primary"><fmt:message key="products.title" /></h2>
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
        <fmt:message key="products.saved" />
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${param.actualizado == '1'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <fmt:message key="products.updated" />
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${param.eliminado == '1'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <fmt:message key="products.deleted" />
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="card shadow-sm border-0 mb-5">
    <div class="card-header bg-white py-3">
        <h5 class="mb-0 fw-bold"><fmt:message key="accion.guardar" /> <fmt:message key="menu.productos" /></h5>
    </div>
    <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/app/productos">
            <input type="hidden" name="idProducto" value="${productoEditar.idProducto}">
            
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="nombre" class="form-label fw-bold"><fmt:message key="product.name" /></label>
                    <input type="text" id="nombre" name="nombre" class="form-control" required maxlength="120" value="${productoEditar.nombre}">
                </div>
                
                <div class="col-md-6">
                    <label for="idCategoria" class="form-label fw-bold"><fmt:message key="product.category" /></label>
                    <select id="idCategoria" name="idCategoria" class="form-select" required>
                        <option value=""><fmt:message key="form.select" /></option>
                        <c:forEach var="c" items="${categorias}">
                            <option value="${c.idCategoria}" ${productoEditar.categoria.idCategoria == c.idCategoria ? 'selected' : ''}>${c.nombre}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="col-md-4">
                    <label for="precio" class="form-label fw-bold"><fmt:message key="product.price" /></label>
                    <div class="input-group">
                        <span class="input-group-text">$</span>
                        <input type="number" id="precio" step="0.01" min="0" name="precio" class="form-control" required value="${productoEditar.precio}">
                    </div>
                </div>
                
                <div class="col-md-4">
                    <label for="stock" class="form-label fw-bold"><fmt:message key="product.stock" /></label>
                    <input type="number" id="stock" min="0" name="stock" class="form-control" required value="${productoEditar.stock}">
                </div>
                
                <div class="col-md-12">
                    <label for="descripcion" class="form-label fw-bold"><fmt:message key="product.description" /></label>
                    <textarea id="descripcion" name="descripcion" class="form-control" rows="2" maxlength="255">${productoEditar.descripcion}</textarea>
                </div>
                
                <div class="col-12 mt-4 d-flex justify-content-end gap-2">
                    <c:if test="${not empty productoEditar}">
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/app/productos"><fmt:message key="accion.cancelar" /></a>
                    </c:if>
                    <button class="btn btn-primary px-4" type="submit"><fmt:message key="accion.guardar" /></button>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="card shadow-sm border-0 mb-4">
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0 align-middle">
                <thead class="table-light">
                <tr>
                    <th class="ps-4"><fmt:message key="table.id" /></th>
                    <th><fmt:message key="product.name" /></th>
                    <th><fmt:message key="product.description" /></th>
                    <th><fmt:message key="product.category" /></th>
                    <th><fmt:message key="product.price" /></th>
                    <th><fmt:message key="product.stock" /></th>
                    <th class="pe-4 text-end"><fmt:message key="table.actions" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${productos}">
                    <tr>
                        <td class="ps-4">${p.idProducto}</td>
                        <td class="fw-bold">${p.nombre}</td>
                        <td style="max-width: 250px; word-wrap: break-word;" class="text-muted small">${p.descripcion}</td>
                        <td><span class="badge bg-secondary">${p.categoria.nombre}</span></td>
                        <td class="text-success fw-bold">$ ${p.precio}</td>
                        <td>${p.stock}</td>
                        <td class="pe-4 text-end text-nowrap">
                            <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/app/productos?accion=editar&id=${p.idProducto}"><fmt:message key="accion.editar" /></a>
                            <a class="btn btn-sm btn-outline-danger ms-1" href="${pageContext.request.contextPath}/app/productos?accion=eliminar&id=${p.idProducto}"
                               onclick="return confirm('<fmt:message key="confirm.delete" />');"><fmt:message key="accion.eliminar" /></a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty productos}">
                    <tr>
                        <td colspan="7" class="text-center py-4 text-muted"><fmt:message key="table.empty" /></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
 
<jsp:include page="templates/footer.jsp" />