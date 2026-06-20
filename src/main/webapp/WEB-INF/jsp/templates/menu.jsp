<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${empty sessionScope.lang ? 'es' : sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<nav class="menu">
    <a href="${pageContext.request.contextPath}/app/dashboard"><fmt:message key="menu.inicio" /></a>
    <a href="${pageContext.request.contextPath}/app/categorias"><fmt:message key="menu.categorias" /></a>
    <a href="${pageContext.request.contextPath}/app/productos"><fmt:message key="menu.productos" /></a>
    <a href="${pageContext.request.contextPath}/app/ventas"><fmt:message key="menu.ventas" /></a>
 
    <span class="menu-separador"></span>
    <a href="${pageContext.request.contextPath}/idioma?lang=es">ES</a>
    <a href="${pageContext.request.contextPath}/idioma?lang=en">EN</a>
 
    <a href="${pageContext.request.contextPath}/logout" class="logout"><fmt:message key="menu.logout" /></a>
</nav>