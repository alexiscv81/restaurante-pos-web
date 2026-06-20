<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html lang="${empty sessionScope.lang ? 'es' : sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="login.title" /> - Restaurante POS Web</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estilos.css">
</head>
<body>
<div class="contenedor login-card">
    <div class="idiomas login-idiomas">
        <a href="${pageContext.request.contextPath}/idioma?lang=es">ES</a>
        <a href="${pageContext.request.contextPath}/idioma?lang=en">EN</a>
    </div>
 
    <h2><fmt:message key="login.title" /></h2>
 
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
 
    <form method="post" action="${pageContext.request.contextPath}/login" class="formulario">
        <label for="correo"><fmt:message key="login.email" /></label>
        <input type="email" id="correo" name="correo" required maxlength="120" autocomplete="username">
 
        <label for="password"><fmt:message key="login.password" /></label>
        <input type="password" id="password" name="password" required maxlength="120" autocomplete="current-password">
 
        <br><br>
        <button type="submit" class="btn"><fmt:message key="login.button" /></button>
    </form>
 
    <p class="ayuda"><fmt:message key="login.demo" /></p>
</div>
</body>
</html>
