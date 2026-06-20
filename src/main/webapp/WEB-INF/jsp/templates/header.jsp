<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html lang="${empty sessionScope.lang ? 'es' : sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="app.titulo" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estilos.css">
</head>
<body>
<header class="topbar">
    <h1><fmt:message key="app.titulo" /></h1>
    <div class="usuario-topbar">
        ${sessionScope.usuario.nombre} (${sessionScope.usuario.rol})
    </div>
</header>