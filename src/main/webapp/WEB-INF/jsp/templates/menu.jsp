<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4 shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/app/dashboard"><fmt:message key="app.titulo" /></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/dashboard"><fmt:message key="menu.inicio" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/categorias"><fmt:message key="menu.categorias" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/productos"><fmt:message key="menu.productos" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/ventas"><fmt:message key="menu.ventas" /></a>
                </li>
            </ul>
            <ul class="navbar-nav align-items-center">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                        ${empty sessionScope.lang or sessionScope.lang == 'es' ? 'ES' : 'EN'}
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/idioma?lang=es">Español</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/idioma?lang=en">English</a></li>
                    </ul>
                </li>
                <li class="nav-item d-none d-lg-block">
                    <span class="nav-link text-light border-start ms-2 ps-3">
                        ${sessionScope.usuario.nombre} <small class="opacity-75">(${sessionScope.usuario.rol})</small>
                    </span>
                </li>
                <li class="nav-item">
                    <a class="btn btn-outline-light btn-sm fw-bold ms-2" href="${pageContext.request.contextPath}/logout"><fmt:message key="menu.logout" /></a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<main class="container flex-grow-1 mb-5">