<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html lang="${empty sessionScope.lang ? 'es' : sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="login.title" /> - Restaurante POS Web</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estilos.css">
</head>
<body class="bg-light d-flex align-items-center min-vh-100">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="card shadow border-0 rounded-3">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center py-3">
                    <h5 class="mb-0 fw-bold"><fmt:message key="login.title" /></h5>
                    <div class="dropdown">
                        <button class="btn btn-sm btn-outline-light dropdown-toggle fw-bold" type="button" data-bs-toggle="dropdown">
                            ${empty sessionScope.lang or sessionScope.lang == 'es' ? 'ES' : 'EN'}
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end shadow-sm">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/idioma?lang=es">Español</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/idioma?lang=en">English</a></li>
                        </ul>
                    </div>
                </div>
                <div class="card-body p-4">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger py-2 mb-3">${error}</div>
                    </c:if>
                    
                    <form method="post" action="${pageContext.request.contextPath}/login">
                        <div class="mb-3">
                            <label for="correo" class="form-label fw-bold"><fmt:message key="login.email" /></label>
                            <input type="email" id="correo" name="correo" class="form-control form-control-lg" required maxlength="120" autocomplete="username">
                        </div>
                        
                        <div class="mb-4">
                            <label for="password" class="form-label fw-bold"><fmt:message key="login.password" /></label>
                            <input type="password" id="password" name="password" class="form-control form-control-lg" required maxlength="120" autocomplete="current-password">
                        </div>
                        
                        <button type="submit" class="btn btn-primary btn-lg w-100 fw-bold"><fmt:message key="login.button" /></button>
                    </form>
                </div>
                <div class="card-footer text-center bg-white pb-3 pt-0 border-0">
                    <p class="text-muted small mb-0"><fmt:message key="login.demo" /></p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
